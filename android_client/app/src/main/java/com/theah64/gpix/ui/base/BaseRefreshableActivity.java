package com.theah64.gpix.ui.base;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.theah64.gpix.R;
import com.theah64.gpix.util.APIResponse;
import com.theah64.gpix.util.DialogUtils;
import com.theah64.gpix.util.OkHttpHelper;
import com.theah64.gpix.util.ProgressManager;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shifar on 15/9/16.
 */
public abstract class BaseRefreshableActivity extends BaseAppCompatActivity {

    private static final String X = BaseRefreshableActivity.class.getSimpleName();

    protected ProgressManager progressMan;
    private DialogUtils dialogUtils;
    private FloatingActionButton fabRefresh;
    private Call theCall;
    private int mainViewId;

    protected void onBeforeFirstContentLoad(int mainViewId, final String actionBarTitle, String actionBarSubTitle, final boolean hasBackNavigation) {

        if (hasBackNavigation) {
            enableBackNavigation();
        }

        this.mainViewId = mainViewId;

        dialogUtils = new DialogUtils(this);
        progressMan = new ProgressManager(this, mainViewId);
        this.fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);

        this.fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabRefreshClick();
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        if (actionBarTitle != null) {
            actionBar.setTitle(actionBarTitle);
        }

        if (actionBarSubTitle != null) {
            actionBar.setSubtitle(actionBarSubTitle);
        }

        loadContent();
    }


    protected void onFabRefreshClick() {
        loadContent();
    }


    protected void onBeforeFirstContentLoad(final int mainViewId, String actionBarSubTitle) {
        onBeforeFirstContentLoad(mainViewId, null, actionBarSubTitle, true);
    }

    protected void onBeforeFirstContentLoad(final int mainViewId) {
        onBeforeFirstContentLoad(mainViewId, null, null, false);
    }

    private void loadContent() {

        this.fabRefresh.hide();
        progressMan.showLoading(getLoadingMessage());

        theCall = OkHttpHelper.getInstance().getClient().newCall(getRequest());
        theCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();
                if (!call.isCanceled()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onError(getString(R.string.network_error));
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String stringResp = OkHttpHelper.logAndGetStringBody(response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final APIResponse apiResponse = new APIResponse(stringResp);
                            handleAPIResponse(apiResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onServerError();
                        } catch (APIResponse.APIException e) {
                            e.printStackTrace();
                            onError(e.getMessage());
                        }
                    }
                });
            }
        });
    }

    protected void onSuccessCall() {
        progressMan.showMainView();
        getFabRefresh().hide();
    }


    protected void onError(String message) {

        fabRefresh.show();
        progressMan.showError(message);

        //Showing
        getFabRefresh().show();
    }


    @Override
    protected void onDestroy() {
        OkHttpHelper.cancelCall(theCall);
        super.onDestroy();
    }

    public FloatingActionButton getFabRefresh() {
        return fabRefresh;
    }

    protected void onServerError() {
        onError(getString(R.string.server_error));
    }

    protected abstract Request getRequest();

    protected abstract String getLoadingMessage();

    protected abstract void handleAPIResponse(APIResponse apiResponse) throws JSONException;


}
