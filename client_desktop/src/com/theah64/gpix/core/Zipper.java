package com.theah64.gpix.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by theapache64 on 13/10/16.
 */
public class Zipper {

    private static final String X = Zipper.class.getSimpleName();
    private final File inputPath;
    private final File outputPath;
    private final byte[] buffer = new byte[1024];
    private ZipperProgressCallback callback;
    private int totalFilesCount = -1;
    private int filesZippedCount = -1;

    public Zipper(File inputPath, File outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public Zipper(String inputPath, String outputPath) {
        this(new File(inputPath), new File(outputPath));
    }

    public void setCallback(ZipperProgressCallback callback) {
        this.callback = callback;
    }

    public void startZipping() throws IOException {

        if (this.callback != null) {
            this.totalFilesCount = 0;
            calculateFileCount(inputPath);
            this.filesZippedCount = 0;
            this.callback.onStart();
        }


        final FileOutputStream fos = new FileOutputStream(outputPath);
        final ZipOutputStream zos = new ZipOutputStream(fos);

        zipIt(zos, inputPath);

        zos.closeEntry();
        zos.close();

        if (this.callback != null) {
            this.callback.onFinish();
        }

    }

    private void calculateFileCount(File file) {
        if (file.isDirectory()) {
            for (final File f : file.listFiles()) {
                calculateFileCount(f);
            }
        } else {
            this.totalFilesCount++;
        }
    }

    private void zipIt(ZipOutputStream zos, final File file) throws IOException {

        if (file.isDirectory()) {

            for (final File f : file.listFiles()) {
                //recursion
                zipIt(zos, f);
            }

        } else {

            //Getting real path by keeping the directory structure
            final String filePath = inputPath.getName() + file.getAbsolutePath().split(inputPath.getName())[1];
            final ZipEntry zipEntry = new ZipEntry(filePath);

            zos.putNextEntry(zipEntry);
            final FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            int len;
            while ((len = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, len);
            }
            fis.close();

            if (this.callback != null) {
                this.filesZippedCount++;
                final int perc = (filesZippedCount * 100) / totalFilesCount;
                this.callback.onProgress(perc);
            }

        }

    }

    public void deleteInput() {
        if (inputPath.isDirectory()) {
            delete(inputPath);
        } else {
            //input is a file
            inputPath.delete();
        }
    }

    private void delete(File file) {

        if (file.isDirectory()) {
            for (final File f : file.listFiles()) {
                delete(f);
            }
        }

        file.delete();
    }

    public interface ZipperProgressCallback {
        void onStart();

        void onProgress(int perc);

        void onFinish();
    }


}
