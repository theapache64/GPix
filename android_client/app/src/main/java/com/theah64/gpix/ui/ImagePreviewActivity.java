package com.theah64.gpix.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.theah64.gpix.R;
import com.theah64.gpix.models.Image;
import com.theah64.gpix.ui.base.BaseAppCompatActivity;


public class ImagePreviewActivity extends BaseAppCompatActivity {

    private ImageView tivImage;
    private FloatingActionButton fabRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        final String url = getStringOrThrow(Image.KEY_IMAGE_URL);

        tivImage = (ImageView) findViewById(R.id.tivImage);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage(url);
            }
        });

        loadImage(url);
    }

    private void loadImage(final String url) {
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

}
