package com.theah64.gpix.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.theah64.gpix.R;
import com.theah64.gpix.ui.MainActivity;
import com.theah64.gpix.util.App;
import com.theah64.gpix.util.ImageDownloadManager;

import java.io.File;
import java.util.List;

public class ImageDownloaderService extends Service {

    private static final String X = ImageDownloaderService.class.getSimpleName();

    public ImageDownloaderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String folder = intent.getStringExtra(MainActivity.KEY_FOLDER);
        final List<String> urls = intent.getStringArrayListExtra(MainActivity.KEY_IMAGE_URLS);

        if (urls != null && !urls.isEmpty()) {

            final File folderToSave = new File(App.getAppFolder() + File.separator + folder);
            if (!folderToSave.exists() && !folderToSave.mkdirs()) {
                throw new IllegalArgumentException("Failed to create folder : " + folderToSave);
            }

            final int notificationId = 5656;

            //Building notification
            final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle(folder)
                    .setAutoCancel(false)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSmallIcon(R.drawable.ic_search_white_36dp);

            new ImageDownloadManager(urls, new ImageDownloadManager.Callback() {
                @Override
                public void onStart() {
                    Log.d(X, "Downloading started...");
                    builder.setProgress(100, 0, false);
                    nm.notify(notificationId, builder.build());
                }

                @Override
                public void onCurrentProgress(String fileName, int perc) {
                    Log.d(X, "Current progress : " + fileName + " , perc : " + perc);
                    builder.setContentText(perc + "%" + " -> " + fileName);
                    nm.notify(notificationId, builder.build());
                }

                @Override
                public void onTotalProgress(String message, int perc) {
                    Log.d(X, "Total progress : " + perc);
                    builder.setProgress(100, perc, false)
                            .setContentText(message);
                    nm.notify(notificationId, builder.build());
                }

                @Override
                public void onFinish() {

                    final String absFolderPath = folderToSave.getAbsolutePath();

                    final Intent dirIntent = new Intent(Intent.ACTION_VIEW);
                    dirIntent.setDataAndType(Uri.parse(absFolderPath), "resource/folder");

                    builder.setContentTitle(urls.size() + " image(s) downloaded.")
                            .setContentText("To " + absFolderPath)
                            .setContentIntent(PendingIntent.getActivity(ImageDownloaderService.this
                                    , 0, dirIntent, 0))
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    nm.notify(notificationId, builder.build());
                    //refreshing
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + absFolderPath)));
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
