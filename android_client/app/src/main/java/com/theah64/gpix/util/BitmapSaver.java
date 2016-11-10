package com.theah64.gpix.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by theapache64 on 8/11/16.
 */

public class BitmapSaver {

    public static String save(String imageUri, File folder, Bitmap loadedBitmap) {
        FileOutputStream fos = null;

        final DomainParser domainParser = new DomainParser(imageUri);
        final String imagePath = folder + File.separator + RandomString.getRandomFilename(10, domainParser.getExtension());

        try {
            fos = new FileOutputStream(imagePath);
            loadedBitmap.compress(domainParser.getCompressionFormat(), 100, fos);
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

    private static class DomainParser {

        private static final String DEFAULT_IMAGE_EXTENSION = ".png";
        private static final String IMAGE_EXT_JPG = ".jpg";
        private static final String IMAGE_EXT_JPEG = ".jpeg";
        private final String extension;


        DomainParser(String url) {
            if (url.contains(".")) {
                final String[] dotSplit = url.split("\\.");
                this.extension = "." + dotSplit[dotSplit.length - 1];
            } else {
                this.extension = DEFAULT_IMAGE_EXTENSION;
            }

        }

        String getExtension() {
            return this.extension;
        }

        Bitmap.CompressFormat getCompressionFormat() {
            switch (this.extension) {
                case IMAGE_EXT_JPG:
                case IMAGE_EXT_JPEG:
                    return Bitmap.CompressFormat.JPEG;
                default:
                    return Bitmap.CompressFormat.PNG;
            }
        }
    }

}
