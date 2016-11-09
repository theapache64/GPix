package com.theah64.gpix.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.theah64.gpix.R;
import com.theah64.gpix.models.Image;
import com.theah64.gpix.services.ImageDownloaderService;
import com.theah64.gpix.ui.base.BaseAppCompatActivity;
import com.theah64.gpix.util.App;
import com.theah64.gpix.util.BitmapSaver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ImagePreviewActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private static final String X = ImagePreviewActivity.class.getSimpleName();
    private ImageView tivImage;
    private FloatingActionButton fabRefresh;
    private Image image;
    private String keyword;
    private Bitmap loadedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enableBackNavigation();

        keyword = getStringOrThrow(MainActivity.KEY_KEYWORD);
        image = (Image) getSerializableOrThrow(Image.KEY);

        tivImage = (ImageView) findViewById(R.id.tivImage);
        tivImage.setOnClickListener(this);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefreshOrDownload);
        fabRefresh.setOnClickListener(this);

        loadImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.miSave:
                //TODO: Save image here

                if (loadedBitmap != null) {

                    //Just save the bitmap
                    final File folderToSave = new File(App.getAppFolder() + File.separator + keyword);
                    Log.d(X, "Folder created : " + folderToSave.mkdirs());

                    final String absoluteImagePath = BitmapSaver.save(folderToSave, loadedBitmap);

                    if (absoluteImagePath != null) {
                        toast(getString(R.string.Image_saved_s, absoluteImagePath));
                    } else {
                        toast(R.string.Failed_to_save_the_image);
                    }

                } else {
                    //Bitmap not loaded, so direct download
                    final ArrayList<String> urlList = new ArrayList<>();
                    urlList.add(image.getImageUrl());
                    final Intent imageDownloaderService = new Intent(this, ImageDownloaderService.class);
                    imageDownloaderService.putExtra(MainActivity.KEY_KEYWORD, keyword);
                    imageDownloaderService.putStringArrayListExtra(MainActivity.KEY_IMAGE_URLS, urlList);
                    startService(imageDownloaderService);
                }

                return true;

            case R.id.miDownloadThumb:
                //TODO: Download thumbnail here
                return true;

            case R.id.miCopyDownloadLink:
                //TODO: Download link to clipboard
                return true;

            case R.id.miCopyThumbnailDownloadLink:
                //TODO: Thumbnail link to clipboard
                return true;

            case R.id.miShareImage:
                //TODO: Share current image
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadImage() {

        //Loading image
        ImageLoader.getInstance().displayImage(image.getImageUrl(), tivImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                fabRefresh.hide();
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                fabRefresh.show();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                fabRefresh.hide();
                loadedBitmap = loadedImage;
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                fabRefresh.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fabRefresh:
                loadImage();
                break;

            case R.id.tivImage:

                if (fabRefresh.isShown()) {
                    fabRefresh.hide();
                } else {
                    fabRefresh.show();
                }

                break;
        }
    }
}
