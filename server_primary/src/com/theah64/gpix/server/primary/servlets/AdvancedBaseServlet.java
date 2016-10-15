package com.theah64.gpix.server.primary.servlets;

import com.theah64.gpix.server.primary.utils.APIResponse;
import com.theah64.gpix.server.primary.utils.HeaderSecurity;
import com.theah64.gpix.server.primary.utils.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by shifar on 16/9/16.
 */
public abstract class AdvancedBaseServlet extends HttpServlet {

    private Request request;
    private HeaderSecurity hs;
    private PrintWriter out;
    private HttpServletRequest httpServletRequest;

    public PrintWriter getWriter() {
        return out;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(getContentType());
        this.httpServletRequest = req;
        out = resp.getWriter();

        try {
            if (isSecureServlet()) {
                hs = new HeaderSecurity(req.getHeader(HeaderSecurity.KEY_AUTHORIZATION));
            }

            if (getRequiredParameters() != null) {
                request = new Request(req, getRequiredParameters());
            }

            doAdvancedPost();

        } catch (Exception e) {
            e.printStackTrace();
            out.write(new APIResponse(e.getMessage()).toString());
        }
    }


    protected String getContentType() {
        return CONTENT_TYPE_JSON;
    }

    protected abstract boolean isSecureServlet();

    protected abstract String[] getRequiredParameters();

    protected abstract void doAdvancedPost() throws Exception;


    public HeaderSecurity getHeaderSecurity() {
        if (!isSecureServlet()) {
            throw new IllegalArgumentException("It's not a secure servlet");
        }
        return hs;
    }

    //Basic request paramaters
    protected static final String KEY_ERROR = "error";
    protected static final String KEY_MESSAGE = "message";
    protected static final String KEY_DATA_TYPE = "data_type";
    protected static final String KEY_DATA = "data"; //file Part


    protected static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ERROR_GET_NOT_SUPPORTED = "GET method not supported";
    private static final String ERROR_POST_NOT_SUPPORTED = "POST method not supported";
    public static final String VERSION_CODE = "/v1";


    protected static void setGETMethodNotSupported(HttpServletResponse response) throws IOException {
        notSupported(ERROR_GET_NOT_SUPPORTED, response);
    }

    protected static void POSTMethodNotSupported(HttpServletResponse response) throws IOException {
        notSupported(ERROR_POST_NOT_SUPPORTED, response);
    }

    private static void notSupported(String methodErrorMessage, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        final PrintWriter out = response.getWriter();

        //GET Method not supported
        out.write(new APIResponse(methodErrorMessage).getResponse());
    }

    public String getStringParameter(String key) {
        return request.getStringParameter(key);
    }


    public boolean getBooleanParameter(String key) {
        return request.getBooleanParameter(key);
    }

    public boolean has(String key) {
        return request.has(key);
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public long getLongParameter(String key) {
        return request.getLongParameter(key);
    }
}
