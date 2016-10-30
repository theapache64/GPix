package com.theah64.gpix.models;

/**
 * Created by shifar on 15/10/16.
 */
public class Image {

    private final String thumbImageUrl, imageUrl;
    private final int height, width;

    public Image(String thumbImageUrl, String imageUrl, int height, int width) {
        this.thumbImageUrl = thumbImageUrl;
        this.imageUrl = imageUrl;
        this.height = height;
        this.width = width;
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

    @Override
    public String toString() {
        return "Image{" +
                "thumbImageUrl='" + thumbImageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
