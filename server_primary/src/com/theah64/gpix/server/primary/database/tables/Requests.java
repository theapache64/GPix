package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.models.Request;

/**
 * Created by theapache64 on 16/10/16.
 */
public class Requests extends BaseTable<Request> {

    private static final Requests instance = new Requests();
    public static final String COLUMN_KEYWORD = "keyword";

    private Requests() {
        super("requests");
    }

    public static Requests getInstance() {
        return instance;
    }


}
