package com.theah64.gpix.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.theah64.gpix.R;

import java.io.File;

/**
 * Created by theapache64 on 30/10/16.
 */

public class App extends Application {

    private static final File APP_FOLDER = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "gpix");

    public static final boolean IS_DEBUG = true;

    private static void initImageLoader(final Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();

        final DisplayImageOptions defaultImageOption = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        config.defaultDisplayImageOptions(defaultImageOption);

        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        config.memoryCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
    }

    public static String getAppFolder() {
        if (!APP_FOLDER.exists()) {
            //noinspection ResultOfMethodCallIgnored
            APP_FOLDER.mkdirs();
        }
        return APP_FOLDER.getAbsolutePath();
    }
}
