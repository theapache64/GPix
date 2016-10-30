package com.theah64.gpix.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by shifar on 23/7/16.
 */
public class OkHttpHelper {

    public static final String METHOD_GET = "GET";

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    private static final String X = OkHttpHelper.class.getSimpleName();
    private static OkHttpHelper instance = new OkHttpHelper();

    private OkHttpHelper() {
    }

    public static OkHttpHelper getInstance() {
        return instance;
    }

    public static String logAndGetStringBody(final Response response) throws IOException {
        final String stringResp = response.body().string();
        Log.d(X, "JSONResponse : " + stringResp);
        return stringResp;
    }

    /**
     * Used to cancel passed calls.
     *
     * @param apiCalls
     */
    public static void cancelCalls(final Call... apiCalls) {
        for (final Call apiCall : apiCalls) {
            cancelCall(apiCall);
        }
    }

    public static void cancelCall(final Call apiCall) {
        if (apiCall != null) {
            Log.d(X, "Cancelling call");
            Log.d(X, "isCallExecuted : " + apiCall.isExecuted());
            apiCall.cancel();
        } else {
            Log.d(X, "API Call is null");
        }
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

}
