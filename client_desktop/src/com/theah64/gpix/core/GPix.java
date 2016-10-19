package com.theah64.gpix.core;

import com.sun.istack.internal.NotNull;
import org.json.JSONArray;
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

    private static final String API_URL_FORMAT = "http://192.168.43.234:8080/v1/gpix?keyword=%s";
    private static final String AUTHORIZATION = "WYAfuHwjCu";

    private static GPix instance = new GPix();

    public static GPix getInstance() {
        return instance;
    }


    @NotNull
    public List<Image> search(String keyword) throws GPixException, IOException, JSONException {

        final String url = getEncodedUrl(API_URL_FORMAT, keyword);

        System.out.println("URL: " + url);
        final String jsonData = NetworkHelper.downloadHtml(url, AUTHORIZATION);

        if (jsonData != null) {

            final JSONObject joResp = new JSONObject(jsonData);

            final boolean hasError = joResp.getBoolean("error");
            final String message = joResp.getString("message");

            if (!hasError) {
                final JSONArray jaImages = joResp.getJSONObject("data").getJSONArray("images");
                return parse(jaImages);
            } else {
                throw new GPixException(message);
            }


        } else {
            throw new GPixException("Empty server response");
        }
    }

    public static String getEncodedUrl(String url, String data) throws UnsupportedEncodingException {
        return String.format(url, URLEncoder.encode(data, "UTF-8"));
    }

    public static List<Image> parse(JSONArray jaImages) throws JSONException {
        List<Image> imageList = new ArrayList<Image>(jaImages.length());
        for (int i = 0; i < jaImages.length(); i++) {
            final JSONObject joImage = jaImages.getJSONObject(i);

            final String imageUrl = joImage.getString("image_url");
            final String thumbUrl = joImage.getString("thumb_url");
            final int width = joImage.getInt("width");
            final int height = joImage.getInt("height");

            imageList.add(new Image(thumbUrl, imageUrl, height, width));

        }
        return imageList;
    }

    public static class GPixException extends Exception {
        public GPixException(String message) {
            super(message);
        }
    }


}
