package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.database.Connection;
import com.theah64.gpix.server.primary.models.Request;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public String addv3(Request request) {

        String newRequestId = null;

        final String query = "INSERT INTO requests (user_id,server_id, keyword,_limit) VALUES (?,?,?,?);";

        final java.sql.Connection con = Connection.getConnection();

        try {
            final PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, request.getUserId());
            ps.setString(2, request.getServerId());
            ps.setString(3, request.getKeyword());
            ps.setInt(4, request.getLimit());

            if (ps.executeUpdate() == 1) {
                final ResultSet rs = ps.getGeneratedKeys();
                if (rs.first()) {
                    newRequestId = rs.getString(1);
                    System.out.println("New request id is " + newRequestId);
                }
                rs.close();
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
        return newRequestId;
    }
}
