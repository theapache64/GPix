package com.theah64.gpix.server.primary.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shifar on 14/10/16.
 */
public class GPix {

    public static final String D1 = "class=\"rg_meta notranslate\">";
    public static final String D2 = "</div></div><!--n-->";

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


    public static class GPixException extends Exception {
        public GPixException(String message) {
            super(message);
        }
    }

}
