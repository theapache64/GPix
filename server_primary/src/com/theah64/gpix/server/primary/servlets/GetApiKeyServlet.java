package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.database.tables.Users;
import com.theah64.gpix.server.primary.models.User;

import javax.servlet.annotation.WebServlet;

/**
 * Created by theapache64 on 15/10/16.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/get_api_key"})
public class GetApiKeyServlet extends AdvancedBaseServlet {

    private static final String KEY_PASSWORD = "password";

    @Override
    protected boolean isSecureServlet() {
        return false;
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{Users.COLUMN_EMAIL, KEY_PASSWORD};
    }

    @Override
    protected void doAdvancedPost() throws Exception {

        final String email = getStringParameter(Users.COLUMN_EMAIL);
        final String password = getStringParameter(KEY_PASSWORD);

        //TODO: Validate email

        final Users users = Users.getInstance();
        final User user = users.get(Users.COLUMN_EMAIL, )
        //TODO: From
    }
}
