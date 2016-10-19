package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.database.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by theapache64 on 19/10/16.
 */
@WebServlet(urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (int i = 0; i < 100000; i++) {

            System.out.println("--------------------------");

            final java.sql.Connection con = Connection.getConnection();
            System.out.println("Opened connection " + i);
            try {
                con.close();
                System.out.println("Closed connection " + i);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("--------------------------");

        }

    }
}
