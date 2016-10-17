package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.models.User;

/**
 * Created by theapache64 on 15/10/16.
 */
public class Users extends BaseTable<User> {

    private static final Users instance = new Users();
    public static final String COLUMN_API_KEY = "api_key";
    public static final String COLUMN_EMAIL = "email";

    private Users() {
        super("users");
    }

    public static Users getInstance() {
        return instance;
    }

}
