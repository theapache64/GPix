package com.theah64.gpix.server.primary.models;

/**
 * Created by theapache64 on 15/10/16.
 */
public class Request {
    private final String userId, keyword;
    private final int limit;

    public Request(String userId, String keyword, int limit) {
        this.userId = userId;
        this.keyword = keyword;
        this.limit = limit;
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
