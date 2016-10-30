package com.theah64.gpix.util;

import android.support.annotation.StringRes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shifar on 23/7/16.
 */
public class APIResponse {

    private static final String KEY_ERROR = "error";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATA = "data";
    private static final String KEY_ERROR_CODE = "error_code";
    /*private static final String KEY_QUOTE = "quote";
    private static final String KEY_AUTHOR = "author";*/
    private final String message;
    private final JSONObject joMain;

    public APIResponse(final String stringResp) throws JSONException, APIException {

        joMain = new JSONObject(stringResp);
        this.message = joMain.getString(KEY_MESSAGE);

        if (joMain.getBoolean(KEY_ERROR)) {
            final int errorCode = joMain.getInt(KEY_ERROR_CODE);
            throw new APIException(errorCode, message);
        }

    }

    public JSONArray getJSONArrayData() throws JSONException {
        return joMain.getJSONArray(KEY_DATA);
    }

    public JSONObject getJSONObjectData() throws JSONException {
        return joMain.getJSONObject(KEY_DATA);
    }

    /*public SmartJSONObject getSmartSONObjectData() throws JSONException {
        return new SmartJSONObject(joMain.getJSONObject(KEY_DATA).toString());
    }*/


    public String getMessage() {
        return this.message;
    }

    /*public Quote getQuote() {

        try {

            final JSONObject joQuote = joMain.getJSONObject(KEY_QUOTE);

            final String quote = joQuote.getString(KEY_QUOTE);
            final String author = joQuote.getString(KEY_AUTHOR);

            return new Quote(quote, author);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    public static class APIException extends Exception {


        private final int pleasantMsg;

        public APIException(final int errorCode, String msg) {
            super(msg);
            pleasantMsg = getPleasantMessage(errorCode);
        }

        private static
        @StringRes
        int getPleasantMessage(int errorCode) {

            switch (errorCode) {

                default:
                    return -1;
            }

        }

        @StringRes
        public int getPleasantMsg() {
            return pleasantMsg;
        }
    }

}
