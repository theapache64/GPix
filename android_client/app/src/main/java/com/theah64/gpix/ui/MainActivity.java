package com.theah64.gpix.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.theah64.gpix.R;
import com.theah64.gpix.adapters.BaseRecyclerViewAdapter;
import com.theah64.gpix.adapters.ImagesAdapter;
import com.theah64.gpix.models.Image;
import com.theah64.gpix.services.ImageDownloaderService;
import com.theah64.gpix.ui.base.BaseRecyclerViewActivity;
import com.theah64.gpix.util.APIRequestBuilder;
import com.theah64.gpix.util.APIResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends BaseRecyclerViewActivity<Image> implements SearchView.OnQueryTextListener, BaseRecyclerViewAdapter.Callback, ImagesAdapter.ImageCallback {

    private static final String X = MainActivity.class.getSimpleName();
    public static final String KEY_KEYWORD = "keyword";
    public static final String KEY_IMAGE_URLS = "image_urls";
    private static final int MENU_ITEM_DOWNLOAD_SELECTED = 1;
    private static final int MENU_ITEM_DOWNLOAD_THUMBNAIL_SELECTED = 2;
    public static final String KEY_FOLDER = "folder";
    private String keyword = "Car";
    private RecyclerView rvImages;
    private Menu menu;
    private List<Image> downloadList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvImages = (RecyclerView) findViewById(R.id.rvImages);
        rvImages.setLayoutManager(new GridLayoutManager(this, 2));

        ((CheckBox) findViewById(R.id.cvSelectAll)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                for (int i = 0; i < getDataList().size(); i++) {

                    if (isChecked) {
                        onImageSelected(i);
                    } else {
                        onImageUnSelected(i);
                    }
                }

                getAdapter().notifyDataSetChanged();
            }
        });

        super.onBeforeFirstContentLoad(R.id.iMain, null);
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
    protected String getErrorOnEmptyData() {
        return getString(R.string.No_image_found);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return rvImages;
    }

    @Override
    protected BaseRecyclerViewAdapter<? extends RecyclerView.ViewHolder, Image> getNewAdapter(List<Image> dataList) {
        return new ImagesAdapter(dataList, R.layout.image_row, this);
    }

    @Override
    protected List<Image> parseData(APIResponse apiResponse) throws JSONException {
        return parse(apiResponse.getJSONObjectData().getJSONArray("images"));
    }

    private static List<Image> parse(JSONArray jaImages) throws JSONException {
        List<Image> imageList = new ArrayList<>(jaImages.length());

        for (int i = 0; i < jaImages.length(); i++) {
            final JSONObject joImage = jaImages.getJSONObject(i);

            final String imageUrl = joImage.getString(Image.KEY_IMAGE_URL);
            final String thumbUrl = joImage.getString("thumb_url");
            final int width = joImage.getInt("width");
            final int height = joImage.getInt("height");

            imageList.add(new Image(thumbUrl, imageUrl, height, width, false));

        }
        return imageList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
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
        onFabRefreshClick();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public void onItemClick(int position) {
        Log.d(X, "Clicked on " + position);
        final Intent imagePreviewIntent = new Intent(this, ImagePreviewActivity.class);
        imagePreviewIntent.putExtra(KEY_KEYWORD, keyword);
        imagePreviewIntent.putExtra(Image.KEY, getDataList().get(position));
        startActivity(imagePreviewIntent);
    }

    @Override
    public void onImageSelected(int position) {
        final Image image = getDataList().get(position);
        image.setSelected(true);
        Log.d(X, "Image selected : " + image);

        downloadList.add(image);
        checkMenu();
    }

    @Override
    public void onImageUnSelected(int position) {
        final Image image = getDataList().get(position);
        image.setSelected(false);
        Log.d(X, "Image unselected: " + image);

        downloadList.remove(image.getImageUrl());

        checkMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_DOWNLOAD_SELECTED:
                startDownload(MENU_ITEM_DOWNLOAD_SELECTED);
                return true;

            case MENU_ITEM_DOWNLOAD_THUMBNAIL_SELECTED:
                startDownload(MENU_ITEM_DOWNLOAD_THUMBNAIL_SELECTED);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startDownload(int downloadType) {

        final Intent idsIntent = new Intent(this, ImageDownloaderService.class);

        final ArrayList<String> imageUrls = new ArrayList<>();

        for (final Image image : downloadList) {
            imageUrls.add(downloadType == MENU_ITEM_DOWNLOAD_SELECTED ? image.getImageUrl() : image.getThumbImageUrl());
        }

        idsIntent.putStringArrayListExtra(KEY_IMAGE_URLS, imageUrls);

        idsIntent.putExtra(KEY_FOLDER, downloadType == MENU_ITEM_DOWNLOAD_SELECTED ? "img" : "thumb" + File.separator + keyword);
        startService(idsIntent);

    }

    private void checkMenu() {
        if (downloadList.isEmpty()) {
            menu.removeItem(MENU_ITEM_DOWNLOAD_SELECTED);
            menu.removeItem(MENU_ITEM_DOWNLOAD_THUMBNAIL_SELECTED);
        } else {
            if (menu.findItem(MENU_ITEM_DOWNLOAD_SELECTED) == null) {
                //add(int groupId, int itemId, int order, CharSequence title)
                menu.add(0, MENU_ITEM_DOWNLOAD_SELECTED, 0, R.string.Download_selected);
                menu.add(0, MENU_ITEM_DOWNLOAD_THUMBNAIL_SELECTED, 0, R.string.Download_selected_thumb);
            }
        }
    }
}
