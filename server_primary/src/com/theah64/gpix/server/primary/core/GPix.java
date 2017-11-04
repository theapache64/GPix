package com.theah64.gpix.server.primary.core;

import com.sun.istack.internal.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shifar on 14/10/16.
 */
public class GPix {

    public static final String SEARCH_URL_FORMAT = "https://www.google.com/search?q=%s&tbm=isch";
    public static final String D1 = "class=\"rg_meta notranslate\">";
    public static final String D2 = "</div></div><!--n-->";
    public static final String D3 = "\">";

    private static GPix instance = new GPix();

    public static GPix getInstance() {
        return instance;
    }

/*    @NotNull
    public List<Image> search(String keyword) throws IOException, JSONException, GPixException {
        return search(SEARCH_URL_FORMAT, keyword, false);
    }*/

    public static String getEncodedUrl(String url, String data) throws UnsupportedEncodingException {
        return String.format(url, URLEncoder.encode(data, "UTF-8"));
    }

    public static List<Image> parse(String firstData) throws JSONException {

        List<Image> imageList = null;

        if (firstData.contains(D1)) {

            imageList = new ArrayList<>(100);

            final String[] f1 = firstData.split(D1);

            for (int i = 1; i < f1.length; i++) {

                final String f2 = f1[i].split(D2)[0];

                final JSONObject joImageNode = new JSONObject(f2);

                final String thumbImageUrl = joImageNode.getString("tu");
                //Secured url -> normal
                final String imageUrl = joImageNode.getString("ou").replaceAll("https://", "http://");

                final int height = joImageNode.getInt("oh");
                final int width = joImageNode.getInt("ow");

                imageList.add(new Image(thumbImageUrl, imageUrl, height, width));

            }

        }

        return imageList;
    }

    @NotNull
    public List<Image> search(String searchUrlFormat, String keyword, String authorization) throws GPixException, IOException, JSONException {

        final String url = getEncodedUrl(searchUrlFormat, keyword);

        System.out.println("URL: " + url);
        final String r1 = NetworkHelper.downloadHtml(url, authorization);

        System.out.println(r1.length());

        final List<Image> imageList = parse(r1);

        if (imageList == null || imageList.isEmpty()) {
            throw new GPixException("No image found for " + keyword);
        }

        return imageList;
    }

    public static class GPixException extends Exception {
        public GPixException(String message) {
            super(message);
        }
    }


}
