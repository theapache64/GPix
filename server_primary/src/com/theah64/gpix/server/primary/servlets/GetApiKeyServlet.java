package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.database.tables.Users;
import com.theah64.gpix.server.primary.models.User;
import com.theah64.gpix.server.primary.utils.APIResponse;
import com.theah64.gpix.server.primary.utils.DarKnight;
import com.theah64.gpix.server.primary.utils.MailHelper;
import com.theah64.gpix.server.primary.utils.RandomString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by theapache64 on 15/10/16.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/get_api_key"})
public class GetApiKeyServlet extends AdvancedBaseServlet {

    private static final String INPUT_EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(INPUT_EMAIL_REGEX);

    private static final int API_KEY_LENGTH = 10;

    @Override
    protected boolean isSecureServlet() {
        return false;
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{Users.COLUMN_EMAIL};
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doAdvancedPost() throws Exception {

        final String email = getStringParameter(Users.COLUMN_EMAIL);

        if (emailPattern.matcher(email).matches()) {

            final Users users = Users.getInstance();
            final User user = users.get(Users.COLUMN_EMAIL, email);


            final String apiKey = RandomString.getNewApiKey(API_KEY_LENGTH);

            final boolean isAdded = users.add(new User(null, email, apiKey));

            if (isAdded) {

                if (MailHelper.sendApiKey(email, apiKey)) {
                    getWriter().write(new APIResponse("API key sent to " + email).getResponse());
                } else {
                    getWriter().write(new APIResponse("Failed to generate new api key for " + email).getResponse());
                }

            } else {
                throw new Exception("Failed to add new user");
            }


        } else {
            throw new Exception("Invalid email");
        }
    }
}
