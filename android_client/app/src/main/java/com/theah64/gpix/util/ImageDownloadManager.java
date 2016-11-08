package com.theah64.gpix.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.File;
import java.util.List;

/**
 * Created by theapache64 on 8/11/16.
 */

public class ImageDownloadManager {

    private static final String X = ImageDownloadManager.class.getSimpleName();
    private final List<String> urls;
    private final Callback callback;
    private final File folderToSave;
    private int downloaded;
    private final int total;

    public ImageDownloadManager(List<String> urls, Callback callback, File folderToSave) {
        this.urls = urls;
        this.total = urls.size();
        this.callback = callback;
        this.folderToSave = folderToSave;
    }

    public void start() {
        downloaded = 0;
        
        callback.onStart();
        startDownload(urls.get(downloaded));
        callback.onFinish();
    }

    private void startDownload(final String url) {

        Log.d(X, "Downloading : " + url);

        ImageLoader.getInstance().loadImage(url, null, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                next();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                final String imagePath = BitmapSaver.save(folderToSave, loadedImage);
                Log.d(X, "Image downloaded to : " + imagePath);
                next();
            }

            private void next() {

                //Calculating progress
                callback.onTotalProgress((downloaded + 1) * 100 / total);

                if ((total - 1) > downloaded) {
                    startDownload(urls.get(downloaded++));
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                next();
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                callback.onCurrentProgress(imageUri, (current * 100 / total));
            }
        });
    }

    public interface Callback {
        void onStart();

        void onCurrentProgress(final String fileName, int perc);

        void onTotalProgress(int perc);

        void onFinish();
    }
}
