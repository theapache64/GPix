package com.theah64.gpix.server.primary.core;

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

    private static final String SEARCH_URL_FORMAT = "https://www.google.co.in/search?q=%s&tbm=isch";
    private static final String D1 = "<div class=\"rg_meta\">";
    private static final String D2 = "</div></div><!--n-->";

    private static GPix instance = new GPix();

    public static GPix getInstance() {
        return instance;
    }

/*    @NotNull
    public List<Image> search(String keyword) throws IOException, JSONException, GPixException {
        return search(SEARCH_URL_FORMAT, keyword, false);
    }*/

    @NotNull
    public List<Image> search(String searchUrlFormat, String keyword, final boolean isCustomServer) throws GPixException, IOException, JSONException {

        List<Image> imageList = null;

        final String url = String.format(searchUrlFormat, URLEncoder.encode(keyword, "UTF-8"));
        System.out.println("URL: " + url);
        final String r1 = NetworkHelper.downloadHtml(url, isCustomServer);

        System.out.println("Result : " + r1);

        if (r1.contains(D1)) {

            final String[] r2Arr = r1.split(D1);

            final JSONArray jaRs = new JSONArray();

            //Looping through r2Arr html
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
                    //Secured url -> normal
                    final String imageUrl = joImageNode.getString("ou").replaceAll("https://", "http://");

                    final int height = joImageNode.getInt("oh");
                    final int width = joImageNode.getInt("ow");

                    imageList.add(new Image(thumbImageUrl, imageUrl, height, width));
                }

            }
        }


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
