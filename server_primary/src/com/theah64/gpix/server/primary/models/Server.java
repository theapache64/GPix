package com.theah64.gpix.server.primary.models;

/**
 * Created by theapache64 on 19/10/16.
 */
public class Server {
    private final String id, name, dataUrlFormat, authorizationKey;
    private final int requestHandled;

    public Server(String id, String name, String dataUrlFormat, String authorizationKey, int requestHandled) {
        this.id = id;
        this.name = name;
        this.dataUrlFormat = dataUrlFormat;
        this.authorizationKey = authorizationKey;
        this.requestHandled = requestHandled;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDataUrlFormat() {
        return dataUrlFormat;
    }

    public String getAuthorizationKey() {
        return authorizationKey;
    }

    public int getRequestHandled() {
        return requestHandled;
    }

    @Override
    public String toString() {
        return "\n" +
                "-----------------------\n" +
                "Server{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dataUrlFormat='" + dataUrlFormat + '\'' +
                ", authorizationKey='" + authorizationKey + '\'' +
                ", requestHandled=" + requestHandled +
                '}';
    }
}
