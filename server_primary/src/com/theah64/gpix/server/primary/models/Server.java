package com.theah64.gpix.server.primary.models;

/**
 * Created by theapache64 on 19/10/16.
 */
public class Server {
    private final String id, name, dataUrlFormat, authorizationKey;

    public Server(String id, String name, String dataUrlFormat, String authorizationKey) {
        this.id = id;
        this.name = name;
        this.dataUrlFormat = dataUrlFormat;
        this.authorizationKey = authorizationKey;
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

    @Override
    public String toString() {
        return "Server{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dataUrlFormat='" + dataUrlFormat + '\'' +
                ", authorizationKey='" + authorizationKey + '\'' +
                '}';
    }
}
