package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.core.GPix;
import com.theah64.gpix.server.primary.core.NetworkHelper;
import com.theah64.gpix.server.primary.database.tables.Servers;
import com.theah64.gpix.server.primary.models.Server;
import com.theah64.gpix.server.primary.utils.Request;
import org.json.JSONException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by theapache64 on 24/10/16.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/serverStatus"})
public class ServerStatusServlet extends AdvancedBaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected boolean isSecureServlet() {
        return false;
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[0];
    }

    @Override
    protected void doAdvancedPost() throws Request.RequestException, IOException, JSONException, GPix.GPixException {

        final List<Server> servers = Servers.getInstance().getAll(Servers.COLUMN_IS_ACTIVE, Servers.TRUE);

        final StringBuilder sb = new StringBuilder();

        for (final Server server : servers) {
            try {
                final int responseCode = NetworkHelper.getResponseRequestCode(String.format(server.getDataUrlFormat(), "test"));
                sb.append(server).append(" - ").append(responseCode).append(" ").append(responseCode == 200 ? " [OK]" : " [NOT-OK]\n");
            } catch (IOException e) {
                e.printStackTrace();
                sb.append(server).append(" - ").append(e.getMessage()).append(" [NOT-OK]\n");
            }
        }

        getWriter().write(sb.toString());
    }
}
