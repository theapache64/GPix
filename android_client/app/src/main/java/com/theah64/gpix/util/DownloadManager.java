package com.theah64.gpix.util;

import java.util.List;

/**
 * Created by theapache64 on 8/11/16.
 */

public class DownloadManager {

    private final List<String> urls;
    private final Callback callback;

    public DownloadManager(List<String> urls, Callback callback) {
        this.urls = urls;
        this.callback = callback;
    }

    public void start() {
        callback.onStart();

    }

    public interface Callback {
        void onStart();

        void onProgress(int perc);

        void onFinish();
    }
}
