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
    private String victimId;

    public HeaderSecurity(final String authorization) throws Exception {
        //Collecting header from passed request
        this.authorization = authorization;
        isAuthorized();
    }

    /**
     * Used to identify if passed API-KEY has a valid victim.
     */
    private void isAuthorized() throws Exception {

        if (this.authorization == null) {
            //No api key passed along with request
            throw new Exception("Unauthorized access");
        }

        final Users users = Users.getInstance();
        this.victimId = users.get(Users.COLUMN_API_KEY, this.authorization, Users.COLUMN_ID);
        if (this.victimId == null) {
            throw new Exception("No victim found with the api_key " + this.authorization);
        }

    }

    public String getVictimId() {
        return this.victimId;
    }

    public String getFailureReason() {
        return this.authorization == null ? REASON_API_KEY_MISSING : REASON_INVALID_API_KEY;
    }
}
