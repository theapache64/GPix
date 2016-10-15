package com.theah64.gpix;

import com.sun.istack.internal.NotNull;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        final String keyword = "Ironman";

        GPix gi = GPix.getInstance();

        try {

            @NotNull final List<GPix.Image> images = gi.search(keyword);

            final File photosFolder = new File("/home/shifar/Desktop/Photos/" + keyword + "_" + System.currentTimeMillis());
            photosFolder.mkdirs();

            /*final int totalImages = images.size();
            int download = 0;*/

            for (final GPix.Image image : images) {
                /*NetworkHelper.download(photosFolder, image.getThumbImageUrl());
                System.out.print("\r" + ((++download * 100) / totalImages) + "% finished");*/
                System.out.println(image);
            }

        } catch (GPix.NoImageFoundException e) {

            e.printStackTrace();
            System.out.println("ERROR : " + e.getMessage());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
