package com.theah64.gpix.util;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.List;

/**
 * Created by theapache64 on 8/11/16.
 */

public class DownloadManager {

    private final List<String> urls;
    private final Callback callback;
    private final File folderToSave;
    private int downloaded;

    public DownloadManager(List<String> urls, Callback callback, File folderToSave) {
        this.urls = urls;
        this.callback = callback;
        this.folderToSave = folderToSave;
    }

    public void start() {
        callback.onStart();
        downloaded = 0;
        startDownload(urls.get(downloaded));
    }

    private void startDownload(final String url) {
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                BitmapSaver.save(folderToSave, loadedImage);
                if (urls.size() < downloaded) {
                    startDownload(urls.get(downloaded++));
                }
                //TODO :Bwaaak
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    public interface Callback {
        void onStart();

        void onCurrentProgress(int perc);

        void onTotalProgress(int perc);

        void onFinish();
    }
}
