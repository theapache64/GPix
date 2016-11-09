package com.theah64.gpix.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.theah64.gpix.R;
import com.theah64.gpix.ui.MainActivity;
import com.theah64.gpix.util.App;
import com.theah64.gpix.util.ImageDownloadManager;

import java.io.File;
import java.util.List;

public class ImageDownloaderService extends Service {

    public ImageDownloaderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String keyword = intent.getStringExtra(MainActivity.KEY_KEYWORD);
        final List<String> urls = intent.getStringArrayListExtra(MainActivity.KEY_KEYWORD);

        if (urls != null && !urls.isEmpty()) {

            final File folderToSave = new File(App.getAppFolder() + File.separator + keyword);


            final int notificationId = 5656;

            //Building notification
            final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle("The Title")
                    .setSmallIcon(R.drawable.ic_search_white_36dp);

            new ImageDownloadManager(urls, new ImageDownloadManager.Callback() {
                @Override
                public void onStart() {
                    builder.setProgress(100, 0, false);
                    nm.notify(notificationId, builder.build());
                }

                @Override
                public void onCurrentProgress(String fileName, int perc) {

                }

                @Override
                public void onTotalProgress(int perc) {
                    builder.setProgress(100, perc, false);
                    nm.notify(notificationId, builder.build());
                }

                @Override
                public void onFinish() {
                    builder.setContentText("Download complete")
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    nm.notify(notificationId, builder.build());
                    stopSelf();
                }
            }, folderToSave).start();

        } else {
            throw new IllegalArgumentException("URL list is empty!");
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
