package com.theah64.gpix.models;

import java.io.Serializable;

/**
 * Created by shifar on 15/10/16.
 */
public class Image implements Serializable {

    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY = "image";
    private final String thumbImageUrl, imageUrl;
    private final int height, width;
    private boolean isSelected;

    public Image(String thumbImageUrl, String imageUrl, int height, int width, boolean isSelected) {
        this.thumbImageUrl = thumbImageUrl;
        this.imageUrl = imageUrl;
        this.height = height;
        this.width = width;
        this.isSelected = isSelected;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public String toString() {
        return "Image{" +
                "thumbImageUrl='" + thumbImageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
