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
    }

    private void startDownload(final String url) {

        Log.d(X, "Downloading : " + url);

        ImageLoader.getInstance().loadImage(url, null, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                callback.onCurrentProgress(imageUri, 0);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                next("Failed: " + imageUri);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                final String imagePath = BitmapSaver.save(folderToSave, loadedImage);
                Log.d(X, "Image downloaded to : " + imagePath);
                next(imageUri);
            }

            private void next(final String message) {

                //Calculating progress
                callback.onTotalProgress(message, (downloaded + 1) * 100 / total);

                if ((total - 1) > downloaded) {
                    startDownload(urls.get(downloaded++));
                } else {
                    callback.onFinish();
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

                next("Cancelled: " + imageUri);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {

                final int perc = (current * 100 / total);
                callback.onCurrentProgress(imageUri, perc);
                if (total == 1) {
                    callback.onTotalProgress("Downloading : " + imageUri, perc);
                }
            }
        });
    }

    public interface Callback {
        void onStart();


        void onCurrentProgress(final String fileName, int perc);

        void onTotalProgress(final String message, int perc);

        void onFinish();
    }
}
