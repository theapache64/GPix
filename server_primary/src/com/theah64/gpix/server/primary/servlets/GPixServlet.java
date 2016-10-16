package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.database.tables.Requests;

/**
 * Created by theapache64 on 15/10/16.
 */
public class GPixServlet extends AdvancedBaseServlet {

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
        
    }
}
