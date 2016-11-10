package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.core.GPix;
import com.theah64.gpix.server.primary.core.NetworkHelper;
import com.theah64.gpix.server.primary.database.Connection;
import org.json.JSONException;

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
        try {
            System.out.println(new GPix().search(GPix.SEARCH_URL_FORMAT, "car", null).size());
        } catch (GPix.GPixException | JSONException e) {
            e.printStackTrace();
        }
    }
}
