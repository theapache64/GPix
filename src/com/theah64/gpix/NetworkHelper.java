package com.theah64.gpix;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shifar on 14/10/16.
 */
public class NetworkHelper {

    private static final String FAKE_USER_AGENT = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";

    public static String downloadHtml(final String url) throws IOException {

        final URL theURL = new URL(url);
        final HttpURLConnection urlCon = (HttpURLConnection) theURL.openConnection();
        urlCon.addRequestProperty("User-Agent", FAKE_USER_AGENT);

        final BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
        final StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        br.close();

        final String data = sb.toString();
        return !data.isEmpty() ? data : null;
    }

    public static void download(File toFolder, String downloadUrl) throws IOException {
        final URL url = new URL(downloadUrl);
        final byte[] buffer = new byte[1024];
        final BufferedInputStream bis = new BufferedInputStream(url.openStream());
        final FileOutputStream fos = new FileOutputStream(toFolder + File.separator + getFileNameFromUrl(downloadUrl));
        int len;
        while ((len = bis.read(buffer, 0, 1024)) != -1) {
            fos.write(buffer, 0, len);
        }
        bis.close();
        fos.close();
    }

    private static String getFileNameFromUrl(String downloadUrl) {
        final String[] parts = downloadUrl.split("/");
        return parts[parts.length - 1];
    }
}
