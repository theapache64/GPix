package com.theah64.gpix.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends BaseRecyclerViewActivity<Image> implements SearchView.OnQueryTextListener, BaseRecyclerViewAdapter.Callback, ImagesAdapter.ImageCallback {

    private static final String X = MainActivity.class.getSimpleName();
    public static final String KEY_KEYWORD = "keyword";
    public static final String KEY_IMAGE_URLS = "image_urls";
    private static final int MENU_ITEM_DOWNLOAD = 1;
    public static final String KEY_FOLDER = "folder";
    private String keyword = "Car";
    private RecyclerView rvImages;
    private Menu menu;
    private List<Image> downloadList = new ArrayList<>();
    private SearchView svSearchImage;
    private TextView tvSelectAllLabel;
    private CheckBox cbSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvImages = (RecyclerView) findViewById(R.id.rvImages);
        rvImages.setLayoutManager(new GridLayoutManager(this, 2));

        cbSelectAll = (CheckBox) findViewById(R.id.cbSelectAll);
        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                downloadList.clear();

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

        tvSelectAllLabel = (TextView) findViewById(R.id.tvSelectAllLabel);

        super.onBeforeFirstContentLoad(R.id.iMain, null, null, false);
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
        final List<Image> images = parse(apiResponse.getJSONObjectData().getJSONArray("images"));
        tvSelectAllLabel.setText(getString(R.string.Select_all_d_images, images.size()));
        return images;
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
        MenuItem miSearchImages = menu.findItem(R.id.miSearch);
        svSearchImage = (SearchView) MenuItemCompat.getActionView(miSearchImages);
        svSearchImage.setQueryHint(getString(R.string.Search));
        svSearchImage.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        svSearchImage.onActionViewCollapsed(); //Hiding keyboard
        menu.removeItem(MENU_ITEM_DOWNLOAD); //Removing download menu
        cbSelectAll.setChecked(false); //If select all checked - uncheck it
        this.keyword = query; // increasing visibility
        getSupportActionBar().setTitle(query); //setting query as the title
        downloadList.clear(); //clearing old download list if any
        onFabRefreshClick(); // GET THE DATA
        return true; //Yeah, we handled it here.
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
        refreshSelectedAllLabel();
    }

    private void refreshSelectedAllLabel() {
        tvSelectAllLabel.setText(getString(R.string.d_images_selected, downloadList.size()));
    }

    @Override
    public void onImageUnSelected(int position) {
        final Image image = getDataList().get(position);
        image.setSelected(false);
        Log.d(X, "Image unselected: " + image);

        downloadList.remove(image);

        checkMenu();
        refreshSelectedAllLabel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case MENU_ITEM_DOWNLOAD:
                showDownloadDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDownloadDialog() {
        @SuppressLint("InflateParams") final View downloadDialogLayout = LayoutInflater.from(this).inflate(R.layout.download_dialog_layout, null);

        final AlertDialog.Builder downloadDialogBuilder = new AlertDialog.Builder(this)
                .setTitle(R.string.Download)
                .setMessage(R.string.I_want_to_download)
                .setView(downloadDialogLayout)
                .setPositiveButton(R.string.Download, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final boolean isImageChecked = ((CheckBox) downloadDialogLayout.findViewById(R.id.cbImage)).isChecked();
                        final boolean isThumbnailChecked = ((CheckBox) downloadDialogLayout.findViewById(R.id.cbThumbnail)).isChecked();

                        final ArrayList<String> downloadUrls = new ArrayList<String>();

                        for (final Image image : downloadList) {
                            if (isImageChecked) {
                                downloadUrls.add(image.getImageUrl());
                            }
                            if (isThumbnailChecked) {
                                downloadUrls.add(image.getThumbImageUrl());
                            }
                        }

                        if (downloadUrls.isEmpty()) {
                            //No images selected
                            toast(R.string.Download_cancelled);
                        } else {
                            startDownload(downloadUrls);
                        }


                    }
                });

        downloadDialogBuilder.create().show();
    }

    private void startDownload(final ArrayList<String> imageUrls) {

        final Intent idsIntent = new Intent(this, ImageDownloaderService.class);
        idsIntent.putStringArrayListExtra(KEY_IMAGE_URLS, imageUrls);
        idsIntent.putExtra(KEY_FOLDER, keyword);
        startService(idsIntent);

        toast(R.string.Download_started);
    }

    private void checkMenu() {

        if (downloadList.isEmpty()) {
            menu.removeItem(MENU_ITEM_DOWNLOAD);
        } else {
            if (menu.findItem(MENU_ITEM_DOWNLOAD) == null) {
                //add(int groupId, int itemId, int order, CharSequence title)
                final MenuItem miDownload = menu.add(0, MENU_ITEM_DOWNLOAD, 0, R.string.Download).setIcon(R.drawable.ic_file_download_white_24dp);
                MenuItemCompat.setShowAsAction(miDownload, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
            }
        }
    }
}
