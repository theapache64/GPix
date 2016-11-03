package com.theah64.gpix.server.primary.utils;


import com.theah64.gpix.server.primary.database.tables.Users;

/**
 * Created by shifar on 31/12/15.
 */
public final class HeaderSecurity {

    public static final String KEY_AUTHORIZATION = "Authorization";
    private static final String REASON_API_KEY_MISSING = "API key is missing";
    private static final String REASON_INVALID_API_KEY = "Invalid API key";
    private final String authorization;
    private String userId;

    public HeaderSecurity(final String authorization) throws AuthorizationException {
        //Collecting header from passed request
        this.authorization = authorization;
        isAuthorized();
    }

    /**
     * Used to identify if passed API-KEY has a valid victim.
     */
    private void isAuthorized() throws AuthorizationException {

        if (this.authorization == null) {
            //No api key passed along with request
            throw new AuthorizationException("Unauthorized access");
        }

        final Users users = Users.getInstance();
        this.userId = users.get(Users.COLUMN_API_KEY, this.authorization, Users.COLUMN_ID);
        if (this.userId == null) {
            throw new AuthorizationException("Invalid API KEY: " + this.authorization);
        }

    }

    public String getUserId() {
        return this.userId;
    }

    public String getFailureReason() {
        return this.authorization == null ? REASON_API_KEY_MISSING : REASON_INVALID_API_KEY;
    }

    public class AuthorizationException extends Exception {
        public AuthorizationException(String message) {
            super(message);
        }
    }
}
