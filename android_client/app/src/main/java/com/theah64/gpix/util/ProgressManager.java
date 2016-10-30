package com.theah64.gpix.util;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theah64.gpix.R;


/**
 * Created by shifar on 23/7/16.
 */
public class ProgressManager {

    private final View progressLayout, mainView, ivErrorIcon, pbLoading;
    private final TextView tvMessage;
    private boolean isLoading, isShowingError;

    public ProgressManager(Activity activity, final int mainViewId) {
        this(activity, (ViewGroup) activity.getWindow().getDecorView().getRootView(), mainViewId);
    }

    public ProgressManager(final Activity activity, final ViewGroup rootLayout, @IdRes final int mainViewId) {

        mainView = rootLayout.findViewById(mainViewId);

        if (mainView == null) {
            throw new IllegalArgumentException("main_view couldn't found in the given activity");
        }

        progressLayout = LayoutInflater.from(activity).inflate(R.layout.progress_layout, rootLayout, false);
        rootLayout.addView(progressLayout);

        ivErrorIcon = progressLayout.findViewById(R.id.ivErrorIcon);
        pbLoading = progressLayout.findViewById(R.id.pbLoading);
        tvMessage = (TextView) progressLayout.findViewById(R.id.tvMessage);
        isLoading = false;
        isShowingError = false;
    }

    public void showError(String message) {
        isLoading = false;
        isShowingError = true;
        mainView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        ivErrorIcon.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(message);
    }

    public void showError(int message) {
        isLoading = false;
        isShowingError = true;
        mainView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        ivErrorIcon.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(message);
    }

    public void showLoading(final String message) {
        isLoading = true;
        isShowingError = false;
        mainView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        ivErrorIcon.setVisibility(View.GONE);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(message);
    }

    public void showLoading(final int message) {
        isLoading = true;
        isShowingError = false;
        mainView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        ivErrorIcon.setVisibility(View.GONE);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(message);
    }

    public void showMainView() {
        isLoading = false;
        isShowingError = false;
        mainView.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.GONE);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isShowingError() {
        return isShowingError;
    }
}
