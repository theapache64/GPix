package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.database.Connection;
import com.theah64.gpix.server.primary.models.Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 19/10/16.
 */
public class Servers extends BaseTable<Server> {

    private static final Servers instance = new Servers();
    private static final String COLUMN_AUTHORIZATION_KEY = "authorization_key";
    private static final String COLUMN_DATA_URL_FORMAT = "data_url_format";
    private static final String COLUMN_REQUEST_HANDLED = "request_handled";

    private Servers() {
        super("servers");
    }

    public static Servers getInstance() {
        return instance;
    }


    @Override
    public List<Server> getAll(String whereColumn, String whereColumnValue) {
        List<Server> servers = null;
        final String query = String.format("SELECT s.id, s._name, s.authorization_key, s.data_url_format, COUNT(r.id) AS request_handled FROM servers s LEFT JOIN requests r ON r.server_id = s.id WHERE s.%s = ? GROUP BY s.id ORDER BY request_handled DESC;", whereColumn);
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, whereColumnValue);

            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                servers = new ArrayList<>();

                //looping through each server node
                do {
                    final String id = rs.getString(COLUMN_ID);
                    final String name = rs.getString(COLUMN_NAME);
                    final String dataUrlFormat = rs.getString(COLUMN_DATA_URL_FORMAT);
                    final String authorization = rs.getString(COLUMN_AUTHORIZATION_KEY);
                    final int requestHandled = rs.getInt(COLUMN_REQUEST_HANDLED);

                    servers.add(new Server(id, name, dataUrlFormat, authorization, requestHandled));

                } while (rs.next());
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return servers;
    }
}
