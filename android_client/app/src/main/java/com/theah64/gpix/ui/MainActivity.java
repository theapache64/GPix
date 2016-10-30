package com.theah64.gpix.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.theah64.gpix.R;
import com.theah64.gpix.models.Image;
import com.theah64.gpix.ui.base.BaseRefreshableActivity;
import com.theah64.gpix.util.APIRequestBuilder;
import com.theah64.gpix.util.APIResponse;

import org.json.JSONException;

import okhttp3.Request;

public class MainActivity extends BaseRefreshableActivity implements SearchView.OnQueryTextListener {

    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    protected Request getRequest() {
        return new APIRequestBuilder().addParam(APIRequestBuilder.KEY_KEYWORD, this.keyword).build();
    }

    @Override
    protected String getLoadingMessage() {
        return getString(R.string.Searching);
    }

    @Override
    protected void handleAPIResponse(APIResponse apiResponse) throws JSONException {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem miSearchDestinations = menu.findItem(R.id.miSearch);
        final SearchView svBusDestination = (SearchView) MenuItemCompat.getActionView(miSearchDestinations);
        svBusDestination.setQueryHint(getString(R.string.Search));
        svBusDestination.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.keyword = query;
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
