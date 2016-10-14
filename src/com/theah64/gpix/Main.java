package com.theah64.gpix;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class Main {

    public static void main(String[] args) {


        final String keyword = "Ironman 3";

        GPix gi = GPix.getInstance();

        try {

            @NotNull final List<GPix.Image> images = gi.search(keyword);

            for (final GPix.Image image : images) {
                System.out.println(image);
            }

        } catch (GPix.NoImageFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
