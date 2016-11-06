package com.theah64.gpix.util;

import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by shifar on 29/7/16.
 * Utility class to create API request object.
 */
public class APIRequestBuilder {

    public static final String API_URL = App.IS_DEBUG ? "http://192.168.43.234:8080/v1/gpix" : "http://35.161.57.139:8080/gpix/v1/gpix";
    public static final String KEY_KEYWORD = "keyword";
    private static final String X = APIRequestBuilder.class.getSimpleName();
    private static final String AUTHORIZATION_KEY = "A0vja6BHNZ";
    private final Request.Builder requestBuilder = new Request.Builder();
    private final StringBuilder logBuilder = new StringBuilder();
    private FormBody.Builder params = new FormBody.Builder();

    public APIRequestBuilder() {
        requestBuilder.addHeader("Authorization", AUTHORIZATION_KEY);
    }

    private void appendLog(String key, String value) {
        logBuilder.append(String.format("%s='%s'\n", key, value));
    }

    private APIRequestBuilder addParam(final boolean isAllowNull, final String key, final String value) {

        if (isAllowNull) {
            this.params.add(key, value);
            appendLog(key, value);
        } else {
            //value must be not null.
            if (value != null) {
                this.params.add(key, value);
                appendLog(key, value);
            }
        }

        return this;
    }

    public APIRequestBuilder addParam(final String key, final String value) {
        return addParam(true, key, value);
    }


    /**
     * Used to build the OkHttpRequest.
     */
    public Request build() {

        requestBuilder
                .post(params.build())
                .url(API_URL);

        Log.d(X, "Request : " + logBuilder.toString());

        return requestBuilder.build();
    }

    public APIRequestBuilder addParamIfNotNull(String key, String value) {
        return addParam(false, key, value);
    }
}
