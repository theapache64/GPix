package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.core.GPix;
import com.theah64.gpix.server.primary.core.Image;
import com.theah64.gpix.server.primary.core.NetworkHelper;
import com.theah64.gpix.server.primary.database.tables.Images;
import com.theah64.gpix.server.primary.database.tables.Requests;
import com.theah64.gpix.server.primary.database.tables.Servers;
import com.theah64.gpix.server.primary.models.Request;
import com.theah64.gpix.server.primary.models.Server;
import com.theah64.gpix.server.primary.utils.APIResponse;
import com.theah64.gpix.server.primary.utils.MailHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    protected void doAdvancedPost() throws IOException, JSONException, GPix.GPixException {

        final String keyword = getStringParameter(Requests.COLUMN_KEYWORD);
        final int limit = getIntParameter(Requests.COLUMN_LIMIT, DEFAULT_RESULT_LIMIT);
        final String userId = getHeaderSecurity().getUserId();


        final Images imagesTable = Images.getInstance();

        List<Image> images = imagesTable.getAll(keyword, limit, Images.MAX_RESULT_VALIDITY_IN_DAYS);

        if (images == null || images.size() < limit && limit <= 100) {

            //Get server in usage order.
            final Servers serversTable = Servers.getInstance();
            final List<Server> servers = serversTable.getAll(Servers.COLUMN_IS_ACTIVE, Servers.TRUE);

            for (final Server server : servers) {

                final String url = GPix.getEncodedUrl(server.getDataUrlFormat(), keyword);
                System.out.println("URL: " + url);
                final String googleData = NetworkHelper.downloadHtml(url, server.getAuthorizationKey());

                final String requestId = Requests.getInstance().addv3(new Request(userId, server.getId(), keyword, limit));

                if (googleData != null && googleData.contains(GPix.D1) && googleData.contains(GPix.D2)) {

                    //Images not available or available images are expired. so collect fresh data
                    final List<Image> googleImages = GPix.parse(googleData);
                    images = googleImages;

                    //Adding images in a different thread.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            imagesTable.addAll(requestId, googleImages);
                        }
                    }).start();


                    //Jumping out from the loop, no need to check next server cuz the data has been collected.
                    break;

                } else {


                    //Weird data, mail it to the admin
                    MailHelper.sendMail("theapache64@gmail.com", "GPix - Weird data", "Hey, \n\n GoogleDat: " + googleData + "\n\nRequest: " + requestId + "\n\n" + "Server : " + server);

                    //Assuming the error is with the server, so is_active to false;
                    serversTable.update(Servers.COLUMN_ID, server.getId(), Servers.COLUMN_IS_ACTIVE, Servers.FALSE);
                }

            }
        } else {

            //Adding temp request.
            Requests.getInstance().addv3(new Request(userId, null, keyword, limit));
        }

        if (images != null) {

            final JSONArray jaImages = new JSONArray();


            final int smallestIndex = images.size() >= limit ? limit : images.size();

            //Looping through each images
            for (int i = 0; i < smallestIndex; i++) {

                final Image image = images.get(i);
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

        } else {
            throw new GPix.GPixException("All available servers are busy.");
        }

    }


}
