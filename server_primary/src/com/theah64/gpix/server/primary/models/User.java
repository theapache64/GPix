package com.theah64.gpix.server.primary.models;

/**
 * Created by theapache64 on 15/10/16.
 */
public class User {

    private final String id, email, apiKey;

    public User(String id, String email, String apiKey) {
        this.id = id;
        this.email = email;
        this.apiKey = apiKey;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getApiKey() {
        return apiKey;
    }
}