package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.core.GPix;
import com.theah64.gpix.server.primary.core.Image;
import com.theah64.gpix.server.primary.core.NetworkHelper;
import com.theah64.gpix.server.primary.utils.APIResponse;
import com.theah64.gpix.server.primary.utils.MailHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by theapache64 on 15/10/16.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/search"})
public class SearchServlet extends AdvancedBaseServlet {

    private static final String GOOGLE_SEARCH_URL_FORMAT = "https://www.google.com/search?q=%s&tbm=isch";

    private static final int DEFAULT_RESULT_LIMIT = 100;
    private static final String KEY_KEYWORD = "keyword";
    private static final String KEY_LIMIT = "limit";

    @Override
    protected boolean isSecureServlet() {
        return true;
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{KEY_KEYWORD};
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doAdvancedPost() throws IOException, JSONException, GPix.GPixException {

        System.out.println("---------------------");
        final String keyword = getStringParameter(KEY_KEYWORD);
        final int limit = getIntParameter(KEY_LIMIT, DEFAULT_RESULT_LIMIT);

        final String googleUrl = String.format(GOOGLE_SEARCH_URL_FORMAT, URLEncoder.encode(keyword, "UTF-8"));
        final String googleData = NetworkHelper.downloadHtml(googleUrl);

        if (googleData != null && googleData.contains(GPix.D1) && googleData.contains(GPix.D2)) {

            final List<Image> googleImages = GPix.parse(googleData);

            if (googleImages != null) {

                final JSONArray jaImages = new JSONArray();
                final int smallestIndex = googleImages.size() >= limit ? limit : googleImages.size();

                //Looping through each images
                for (int i = 0; i < smallestIndex; i++) {

                    final Image image = googleImages.get(i);
                    final JSONObject joImage = new JSONObject();

                    joImage.put("image_url", image.getImageUrl());
                    joImage.put("thumb_url", image.getThumbImageUrl());
                    joImage.put("width", image.getWidth());
                    joImage.put("height", image.getHeight());

                    jaImages.put(joImage);
                }

                final JSONObject joData = new JSONObject();
                joData.put("images", jaImages);

                getWriter().write(new APIResponse(jaImages.length() + " images(s) found", joData).getResponse());
            } else {
                throw new GPix.GPixException("No match found");
            }

        } else {

            MailHelper.sendMail("theapache64@gmail.com", "GPix - MayDay|MayDay|MayDay - ALGORITHM EXPIRED!!", "Hey\nWe need to update the algorithm\n\n" + googleData + "\n\n\n\n-GPIX");
            throw new GPix.GPixException("Something went wrong");
        }


    }

}
