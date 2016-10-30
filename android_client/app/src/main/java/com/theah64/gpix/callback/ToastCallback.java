package com.theah64.gpix.callback;

import android.support.annotation.StringRes;

/**
 * Created by shifar on 23/7/16.
 */
public interface ToastCallback {
    void toast(@StringRes final int message);

    void toast(final String message);
}
