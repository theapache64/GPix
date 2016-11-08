package com.theah64.gpix.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.theah64.gpix.R;
import com.theah64.gpix.models.Image;
import com.theah64.gpix.ui.base.BaseAppCompatActivity;


public class ImagePreviewActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ImageView tivImage;
    private FloatingActionButton fabRefresh;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        url = getStringOrThrow(Image.KEY_IMAGE_URL);

        tivImage = (ImageView) findViewById(R.id.tivImage);
        tivImage.setOnClickListener(this);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefreshOrDownload);
        fabRefresh.setOnClickListener(this);

        loadImage();
    }

    private void loadImage() {

        //Loading image
        ImageLoader.getInstance().displayImage(url, tivImage, new ImageLoadingListener() {
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
