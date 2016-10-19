package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.models.Request;

/**
 * Created by theapache64 on 16/10/16.
 */
public class Requests extends BaseTable<Request> {

    private static final Requests instance = new Requests();
    public static final String COLUMN_KEYWORD = "keyword";
    public static final String COLUMN_LIMIT = "limit";

    private Requests() {
        super("requests");
    }

    public static Requests getInstance() {
        return instance;
    }

    @Override
    public String addv3(Request newInstance) {
        String newRequestId = null;
        final String query = "INSERT INTO requests (user_id, keyword,limit) VALUES (?,?,?);";
        return newRequestId;
    }
}
