package com.theah64.gpix.server.primary.core;

import com.sun.istack.internal.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shifar on 15/10/16.
 */
class NetworkHelper {

    private static final String FAKE_USER_AGENT = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";

    public static String downloadHtml(final String url) throws IOException {

        final URL theURL = new URL(url);
        final HttpURLConnection urlCon = (HttpURLConnection) theURL.openConnection();
        urlCon.addRequestProperty("User-Agent", FAKE_USER_AGENT);

        final BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getResponseCode() == 200 ? urlCon.getInputStream() : urlCon.getErrorStream()));
        final StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        br.close();

        final String data = sb.toString();
        return !data.isEmpty() ? data : null;
    }

    public static boolean download(File toFolder, String downloadUrl, @Nullable final String fileNameToSave) {

        try {

            final URL url = new URL(downloadUrl);
            final HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.addRequestProperty("User-Agent", FAKE_USER_AGENT);

            final byte[] buffer = new byte[1024];
            final BufferedInputStream bis = new BufferedInputStream(urlCon.getInputStream());
            final FileOutputStream fos = new FileOutputStream(toFolder + File.separator + (fileNameToSave == null ? getFileNameFromUrl(downloadUrl) : fileNameToSave));

            int len;
            while ((len = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, len);
            }

            bis.close();
            fos.close();

            return true;

        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    private static String getFileNameFromUrl(String downloadUrl) {
        final String[] parts = downloadUrl.split("/");
        return parts[parts.length - 1];
    }
}