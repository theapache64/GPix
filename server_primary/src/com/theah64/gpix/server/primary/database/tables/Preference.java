package com.theah64.gpix.server.primary.database.tables;


import com.theah64.gpix.server.primary.database.Connection;
import com.theah64.gpix.server.primary.utils.CommonUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by theapache64 on 27/8/16.
 */
public class Preference extends BaseTable<String> {

    public static final String COLUMN_KEY = "_key";
    public static final java.lang.String KEY_PHOTO_URL_FORMAT = "photo_url_format";
    public static final String KEY_ADMIN_USERNAME = "admin_username";
    public static final String KEY_ADMIN_PASSWORD = "admin_password";
    public static final String KEY_IS_DIRECT_CONTACT = "is_direct_contact";
    private static final String COLUMN_VALUE = "_value";
    private static Preference instance = new Preference();

    private Preference() {
        super("preferences");
    }

    public static Preference getInstance() {
        return instance;
    }


    @Override
    public String get(String column, String key) {
        String value = null;
        final String query = String.format("SELECT _value FROM preference WHERE %s = ? LIMIT 1", column);
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, key);

            final ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                value = rs.getString(COLUMN_VALUE);
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return value;
    }


    public String getString(final String key) {
        return get(COLUMN_KEY, key);
    }

    public boolean getBoolean(final String key) {
        return CommonUtils.parseBoolean(get(COLUMN_KEY, key));
    }
}
