package com.theah64.gpix.server.primary.utils;

import com.theah64.gpix.server.primary.database.tables.BaseTable;

import java.util.regex.Pattern;

/**
 * Created by theapache64 on 26/11/16.
 */
public class CommonUtils {

    public static final String INPUT_EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final Pattern emailPattern = Pattern.compile(INPUT_EMAIL_REGEX);
    public static final int API_KEY_LENGTH = 10;

    public static boolean parseBoolean(String s) {
        return s != null && s.equals(BaseTable.TRUE);
    }
}
