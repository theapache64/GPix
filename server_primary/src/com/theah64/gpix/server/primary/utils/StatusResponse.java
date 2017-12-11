package com.theah64.gpix.server.primary.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by theapache64 on 13/11/17.
 */
public class StatusResponse {
    public static void redirect(HttpServletResponse response, String title, String message) throws IOException {
        response.sendRedirect("status.jsp?title=" + title + "&message=" + message);
    }
}
