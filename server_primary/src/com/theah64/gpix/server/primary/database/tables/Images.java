package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.core.Image;
import com.theah64.gpix.server.primary.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 17/10/16,2:38 PM.
 */
public class Images extends BaseTable<Image> {


    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_THUMB_URL = "thumb_url";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WIDTH = "width";
    public static final String TABLE_NAME_IMAGES = "images";
    public static final int MAX_RESULT_VALIDITY_IN_DAYS = 15;
    private static final Images instance = new Images();

    private Images() {
        super(TABLE_NAME_IMAGES);
    }

    public static Images getInstance() {
        return instance;
    }

    /*public List<Image> getAll(String keyword, final int limit, final int validity) {
        List<Image> images = null;

        final String query = String.format("SELECT i.image_url, i.thumb_url, i.height, i.width FROM images i INNER JOIN request_image_rel rr ON rr.image_id = i.id INNER JOIN requests r ON r.id = rr.request_id WHERE r.keyword = ? AND IFNULL(DATEDIFF(NOW(),r.created_at),0) <= %d LIMIT %d;", validity, limit);

        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, keyword);

            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                images = new ArrayList<>();

                do {
                    final String imageUrl = rs.getString(COLUMN_IMAGE_URL);
                    final String thumbUrl = rs.getString(COLUMN_THUMB_URL);
                    final int height = rs.getInt(COLUMN_HEIGHT);
                    final int width = rs.getInt(COLUMN_WIDTH);

                    images.add(new Image(thumbUrl, imageUrl, height, width));
                } while (rs.next());

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
        return images;
    }*/

    /*public void addAll(final String requestId, List<Image> images) {

        boolean isEverythingOk = false;

        final String existenceQuery = "SELECT id FROM images WHERE image_url = ? AND thumb_url = ? AND width = ? AND height = ? LIMIT 1";
        final String insertImageQuery = "INSERT INTO images (image_url, thumb_url, width, height ) VALUES (?, ?, ?, ?);";
        final String insertRelQuery = "INSERT INTO request_image_rel (request_id, image_id) VALUES (?,?);";


        final java.sql.Connection con = Connection.getConnection();
        try {

            final PreparedStatement exPs = con.prepareStatement(existenceQuery);
            final PreparedStatement iiPs = con.prepareStatement(insertImageQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            final PreparedStatement irPs = con.prepareStatement(insertRelQuery);

            for (Image image : images) {

                //Checking existence
                exPs.setString(1, image.getImageUrl());
                exPs.setString(2, image.getThumbImageUrl());
                exPs.setInt(3, image.getWidth());
                exPs.setInt(4, image.getHeight());

                final ResultSet rsEx = exPs.executeQuery();
                String imageId = null;

                if (rsEx.first()) {
                    //Image available in db, no need to add
                    imageId = rsEx.getString(Images.COLUMN_ID);
                } else {
                    //Image not available so insert
                    iiPs.setString(1, image.getImageUrl());
                    iiPs.setString(2, image.getThumbImageUrl());
                    iiPs.setInt(3, image.getWidth());
                    iiPs.setInt(4, image.getHeight());

                    if (iiPs.executeUpdate() == 1) {
                        final ResultSet rsIi = iiPs.getGeneratedKeys();
                        if (rsIi.first()) {
                            imageId = rsIi.getString(1);
                        }
                        rsIi.close();
                    }

                }

                //here image id must be not null.
                if (imageId == null) {
                    throw new IllegalArgumentException("Failed to get image id");
                }

                //here both image_id and request id is not null. so adding relation.
                irPs.setString(1, requestId);
                irPs.setString(2, imageId);

                if (irPs.executeUpdate() != 1) {
                    throw new IllegalArgumentException("Failed to add request-image relation");
                }

                rsEx.close();

            }

            exPs.close();
            iiPs.close();
            irPs.close();

            isEverythingOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
            isEverythingOk = false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                isEverythingOk = false;
            }
        }

        if (!isEverythingOk) {
            throw new IllegalArgumentException("Something went wrong while adding images to the database.");
        }

    }*/
}

