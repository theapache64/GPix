package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.core.GPix;
import com.theah64.gpix.server.primary.core.Image;
import com.theah64.gpix.server.primary.database.tables.Requests;
import com.theah64.gpix.server.primary.database.tables.Users;
import com.theah64.gpix.server.primary.utils.APIResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * Created by theapache64 on 15/10/16.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/gpix"})
public class GPixServlet extends AdvancedBaseServlet {

    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_THUMB_URL = "thumb_url";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WIDTH = "width";
    private static final String KEY_IMAGES = "images";

    @Override
    protected boolean isSecureServlet() {
        return true;
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{Requests.COLUMN_KEYWORD};
    }

    @Override
    protected void doAdvancedPost() throws Exception {
        final String keyword = getStringParameter(Requests.COLUMN_KEYWORD);
        final List<Image> images = GPix.getInstance().search(keyword);

        final JSONArray jaImages = new JSONArray();

        //Looping through each images
        for (final Image image : images) {

            final JSONObject joImage = new JSONObject();

            joImage.put(KEY_IMAGE_URL, image.getImageUrl());
            joImage.put(KEY_THUMB_URL, image.getThumbImageUrl());
            joImage.put(KEY_WIDTH, image.getWidth());
            joImage.put(KEY_HEIGHT, image.getHeight());

            jaImages.put(joImage);
        }

        getWriter().write(new APIResponse(jaImages.length() + " images(s) available", KEY_IMAGES, jaImages.toString()).getResponse());

    }
}
