package com.theah64.gpix.server.primary.models;

/**
 * Created by theapache64 on 15/10/16.
 */
public class User {

    private final String id, email, apiKey, lastHit;
    private final long totalHits;
    private final boolean isActive;

    public User(String id, String email, String apiKey, String lastHit, long totalHits, boolean isActive) {
        this.id = id;
        this.email = email;
        this.apiKey = apiKey;
        this.lastHit = lastHit;
        this.totalHits = totalHits;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
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

    public String getLastHit() {
        return lastHit;
    }

    public long getTotalHits() {
        return totalHits;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", lastHit='" + lastHit + '\'' +
                ", totalHits=" + totalHits +
                '}';
    }
}
