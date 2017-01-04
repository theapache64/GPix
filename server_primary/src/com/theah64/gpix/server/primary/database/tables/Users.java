package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.database.Connection;
import com.theah64.gpix.server.primary.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 15/10/16.
 */
public class Users extends BaseTable<User> {

    public static final String COLUMN_API_KEY = "api_key";
    public static final String COLUMN_EMAIL = "email";
    private static final Users instance = new Users();
    private static final String COLUMN_AS_TOTAL_HITS = "total_hits";
    private static final String COLUMN_AS_LAST_HIT = "last_hit";

    private Users() {
        super("users");
    }

    public static Users getInstance() {
        return instance;
    }

    public List<User> getAll() {
        List<User> users = null;
        final String query = "SELECT u.id, u.email, COUNT(DISTINCT r.id) AS total_hits, (SELECT CONCAT_WS('-',keyword,_limit,created_at) FROM requests WHERE user_id = u.id ORDER BY ID desc LIMIT 1 ) last_hit, u.is_active FROM users u LEFT JOIN requests r ON r.user_id = u.id GROUP BY u.id ORDER BY total_hits DESC;";
        final java.sql.Connection con = Connection.getConnection();
        try {
            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery(query);

            if (rs.first()) {
                users = new ArrayList<>();
                do {
                    final String id = rs.getString(COLUMN_ID);
                    final String email = rs.getString(COLUMN_EMAIL);
                    final long totalHits = rs.getLong(COLUMN_AS_TOTAL_HITS);
                    final String lastHit = rs.getString(COLUMN_AS_LAST_HIT);
                    final boolean isActive = rs.getBoolean(COLUMN_IS_ACTIVE);

                    users.add(new User(id, email, null, lastHit, totalHits, isActive));
                } while (rs.next());
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    @Override
    public boolean add(User newUser) {

        boolean isUserAdded = false;
        final String query = "INSERT INTO users (email,api_key) VALUES (?,?);";
        final java.sql.Connection con = Connection.getConnection();
        try {

            final PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, newUser.getEmail());
            ps.setString(2, newUser.getApiKey());

            isUserAdded = ps.executeUpdate() == 1;

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
        return isUserAdded;
    }

    @Override
    public User get(String column, String value) {

        User user = null;

        final String query = String.format("SELECT id, email,api_key,is_active FROM users WHERE %s = ? LIMIT 1 ", column);

        final java.sql.Connection con = Connection.getConnection();
        try {

            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, value);
            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                final String id = rs.getString(COLUMN_ID);
                final String email = rs.getString(COLUMN_EMAIL);
                final String apiKey = rs.getString(COLUMN_API_KEY);
                final boolean isActive = rs.getBoolean(COLUMN_IS_ACTIVE);

                user = new User(id, email, apiKey, null, 0, isActive);
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

        return user;
    }
}
