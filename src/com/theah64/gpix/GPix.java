package com.theah64.gpix;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shifar on 14/10/16.
 */
public class GPix {

    private static final String SEARCH_URL_FORMAT = "https://www.google.co.in/search?q=%s&tbm=isch";
    private static final String HIT_URL_FORMAT = "https://www.google.co.in/ajax/pi/imgdisc?imgdii=%s";

    private static GPix instance = new GPix();

    public static GPix getInstance() {
        return instance;
    }

    public List<Image> search(String keyword) throws NoImageFoundException, Exception {

        List<GPix.Image> imageList = null;

        final String url = String.format(SEARCH_URL_FORMAT, URLEncoder.encode(keyword, "UTF-8"));

        final String r1 = NetworkHelper.downloadHtml(url);

        final String[] r2Arr = r1.split("var data=\\[");

        if (r2Arr.length >= 2) {

            //Here r3 = valid json image ids.
            final String r3 = r2Arr[1].split(",\\d+]")[0];

            if (!r3.trim().isEmpty()) {

                final JSONArray jaGIds = new JSONArray(r3);

                final List<String> gIdList = new ArrayList<String>();

                for (int i = 0; i < jaGIds.length(); i++) {

                    final JSONArray jaGIdNode = jaGIds.getJSONArray(i);

                    final String gId = jaGIdNode.getString(0);
                    gIdList.add(gId);
                }

                if (!gIdList.isEmpty()) {

                    //For now, we are only downloading the primary result data.
                    final String primarySearchId = gIdList.get(0);
                    final String hitUrl = String.format(HIT_URL_FORMAT, URLEncoder.encode(primarySearchId, "UTF-8"));

                    final String h1 = NetworkHelper.downloadHtml(hitUrl);
                    final String h2 = h1.split(",\\d+]")[0];

                    if (!h2.trim().isEmpty()) {
                        final String h2p5 = h2.replaceAll("(/\\*|\\*/)", "");
                        final JSONArray jaH3 = new JSONObject(h2p5).getJSONArray("rel");

                        imageList = new ArrayList<Image>(jaH3.length());

                        for (int i = 0; i < jaH3.length(); i++) {

                            final JSONObject joImageNode = jaH3.getJSONObject(i);

                            final String thumbImageUrl = joImageNode.getString("tu");
                            final String imageUrl = joImageNode.getString("ou");

                            final int height = Integer.parseInt(joImageNode.getString("oh"));
                            final int width = Integer.parseInt(joImageNode.getString("ow"));


                            imageList.add(new Image(thumbImageUrl, imageUrl, height, width));
                        }

                    }

                }

            }


        }

        if (imageList == null || imageList.isEmpty()) {
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
