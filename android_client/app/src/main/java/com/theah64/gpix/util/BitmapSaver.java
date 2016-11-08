package com.theah64.gpix.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by theapache64 on 8/11/16.
 */

public class BitmapSaver {

    public static String save(File folder, Bitmap loadedBitmap) {
        FileOutputStream fos = null;
        final String imagePath = folder + File.separator + RandomString.getRandomFilename(10, ".jpg");
        try {
            fos = new FileOutputStream(imagePath);
            loadedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return imagePath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
