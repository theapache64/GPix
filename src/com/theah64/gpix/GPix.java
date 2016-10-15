package com.theah64.gpix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shifar on 14/10/16.
 */
public class GPix {

    private static final String SEARCH_URL_FORMAT = "https://www.google.co.in/search?q=%s&tbm=isch";
    private static final String D1 = "<div class=\"rg_meta\">";
    private static final String D2 = "</div></div><!--n-->";

    private static GPix instance = new GPix();

    public static GPix getInstance() {
        return instance;
    }

    public List<Image> search(String keyword) throws NoImageFoundException, IOException, JSONException {

        List<GPix.Image> imageList = null;

        final String url = String.format(SEARCH_URL_FORMAT, URLEncoder.encode(keyword, "UTF-8"));
        System.out.println("URL : " + url);
        final String r1 = NetworkHelper.downloadHtml(url);
        if (r1.contains(D1)) {

            final String[] r2Arr = r1.split(D1);

            final JSONArray jaRs = new JSONArray();

            //Looping through r2Arr
            for (final String r2ArrNode : r2Arr) {
                if (r2ArrNode.contains(D2)) {
                    final String r3 = r2ArrNode.split(D2)[0];
                    jaRs.put(new JSONObject(r3));
                }
            }

            if (jaRs.length() > 0) {

                //Converting to GPixImage
                imageList = new ArrayList<Image>(jaRs.length());

                for (int i = 0; i < jaRs.length(); i++) {

                    final JSONObject joImageNode = jaRs.getJSONObject(i);

                    final String thumbImageUrl = joImageNode.getString("tu");
                    final String imageUrl = joImageNode.getString("ou");

                    final int height = joImageNode.getInt("oh");
                    final int width = joImageNode.getInt("ow");

                    imageList.add(new Image(thumbImageUrl, imageUrl, height, width));
                }


            }
        }


        if (imageList == null || imageList.isEmpty())

        {
            throw new NoImageFoundException("No image found for " + keyword);
        }


        return imageList;
    }


    class NoImageFoundException extends Exception {
        public NoImageFoundException(String message) {
            super(message);
        }
    }

    static class Image {
        private final String thumbImageUrl, imageUrl;
        private final int height, width;

        public Image(String thumbImageUrl, String imageUrl, int height, int width) {
            this.thumbImageUrl = thumbImageUrl;
            this.imageUrl = imageUrl;
            this.height = height;
            this.width = width;
        }

        public String getThumbImageUrl() {
            return thumbImageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        @Override
        public String toString() {
            return "Image{" +
                    "thumbImageUrl='" + thumbImageUrl + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", height=" + height +
                    ", width=" + width +
                    '}';
        }
    }
}
