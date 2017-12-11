package com.theah64.gpix.server.primary.database.tables;


import com.theah64.gpix.server.primary.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by theapache64 on 11/22/2015.
 */
public class BaseTable<T> {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_IS_ACTIVE = "is_active";
    public static final String TRUE = "1";
    public static final String FALSE = "0";
    protected static final String COLUMN_AS_UNIX_EPOCH = "unix_epoch";
    private static final String ERROR_MESSAGE_UNDEFINED_METHOD = "Undefined method.";
    private static final String COLUMN_AS_TOTAL_ROWS = "total_rows";
    private final String tableName;

    BaseTable(String tableName) {
        this.tableName = tableName;
    }

    public T get(final String column, final String value) {
        throw new IllegalArgumentException(ERROR_MESSAGE_UNDEFINED_METHOD);
    }

    public String get(String byColumn, String byValue, String columnToReturn) {

        final String query = String.format("SELECT %s FROM %s WHERE %s = ? AND is_active=1 ORDER BY id DESC LIMIT 1", columnToReturn, tableName, byColumn);

        String resultValue = null;
        final java.sql.Connection con = Connection.getConnection();

        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, byValue);
            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                resultValue = rs.getString(columnToReturn);
            }
            rs.close();
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

        return resultValue;
    }


}

