package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.core.GPix;
import com.theah64.gpix.server.primary.core.Image;
import com.theah64.gpix.server.primary.database.tables.Images;
import com.theah64.gpix.server.primary.database.tables.Requests;
import com.theah64.gpix.server.primary.models.Request;
import com.theah64.gpix.server.primary.utils.APIResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by theapache64 on 15/10/16.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/gpix"})
public class GPixServlet extends AdvancedBaseServlet {


    private static final int DEFAULT_RESULT_LIMIT = 100;

    @Override
    protected boolean isSecureServlet() {
        return true;
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{Requests.COLUMN_KEYWORD};
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doAdvancedPost() throws Exception {

        final String keyword = getStringParameter(Requests.COLUMN_KEYWORD);
        final int limit = getIntParameter(Requests.COLUMN_LIMIT, DEFAULT_RESULT_LIMIT);
        final String userId = getHeaderSecurity().getUserId();

        final String requestId = Requests.getInstance().addv3(new Request(userId, keyword, limit));

        final Images imagesTable = Images.getInstance();

        List<Image> images = imagesTable.getAll(keyword, limit, Images.MAX_RESULT_VALIDITY_IN_DAYS);

        if (images == null) {

            //Getting least used server
            final Server`

            //Images not available or available images are expired. so collect fresh data
            images = GPix.getInstance().search(keyword);

            //Adding images to the db
            imagesTable.addAll(requestId, images);
        }

        final JSONArray jaImages = new JSONArray();

        //Looping through each images
        for (final Image image : images) {

            final JSONObject joImage = new JSONObject();

            joImage.put(Images.COLUMN_IMAGE_URL, image.getImageUrl());
            joImage.put(Images.COLUMN_THUMB_URL, image.getThumbImageUrl());
            joImage.put(Images.COLUMN_WIDTH, image.getWidth());
            joImage.put(Images.COLUMN_HEIGHT, image.getHeight());

            jaImages.put(joImage);
        }

        final JSONObject joData = new JSONObject();
        joData.put(Images.TABLE_NAME_IMAGES, jaImages);

        getWriter().write(new APIResponse(jaImages.length() + " images(s) available", joData).getResponse());
    }
}
