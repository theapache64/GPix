package com.theah64.gpix.server.primary.models;

/**
 * Created by theapache64 on 15/10/16.
 */
public class Request {
    private final String userId,serverId, keyword;
    private final int limit;

    public Request(String userId, String serverId, String keyword, int limit) {
        this.userId = userId;
        this.serverId = serverId;
        this.keyword = keyword;
        this.limit = limit;
    }

    public String getServerId() {
        return serverId;
    }

    public String getUserId() {
        return userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getLimit() {
        return limit;
    }
}
