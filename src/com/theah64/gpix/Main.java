package com.theah64.gpix;

import com.sun.istack.internal.NotNull;
import com.theah64.gpix.core.CommonUtils;
import com.theah64.gpix.core.GPix;
import com.theah64.gpix.core.NetworkHelper;
import com.theah64.gpix.core.models.Image;
import org.apache.commons.cli.*;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final int MAX_COUNT = 100;

    private static final String FLAG_KEYWORD = "k";

    private static final String FLAG_THUMBNAIL = "t";
    private static final String FLAG_ORIGINAL = "o";
    private static final String FLAG_BOTH_ORIGINAL_AND_THUMB = "b";

    private static final String FLAG_COUNT = "c";

    private static final Options options = new Options()
            .addOption(FLAG_KEYWORD, true, "Keyword")
            .addOption(FLAG_COUNT, true, "Number of images to be downloaded.")
            .addOption(FLAG_THUMBNAIL, false, "Download thumbnail only")
            .addOption(FLAG_ORIGINAL, false, "Download original only")
            .addOption(FLAG_BOTH_ORIGINAL_AND_THUMB, false, "Download  both thumbnail and image");


    public static void main(String[] args) throws ParseException {

        final CommandLineParser parse = new DefaultParser();
        final CommandLine cmd = parse.parse(options, args);

        final String keyword = cmd.getOptionValue(FLAG_KEYWORD);

        try {

            if (keyword != null) {

                final int imgCount = CommonUtils.parseInt(cmd.getOptionValue(FLAG_COUNT), MAX_COUNT);

                if (imgCount > MAX_COUNT) {
                    System.out.println("WARNING: Max count can only be <=" + MAX_COUNT);
                }

                final boolean isThumbOnly = cmd.hasOption(FLAG_THUMBNAIL);

                GPix gi = GPix.getInstance();

                try {

                    @NotNull final List<Image> images = gi.search(keyword);

                    final File photosFolder = new File("/home/shifar/Desktop/Photos/" + keyword + "_" + System.currentTimeMillis() + File.separator +);
                    photosFolder.mkdirs();

                    final int totalImages = images.size();
                    int download = 0;

                    for (int i = 0; i < imgCount; i++) {

                        if (i < totalImages) {
                            final Image image = images.get(i);
                            NetworkHelper.download(photosFolder, isThumbOnly ? image.getThumbImageUrl() : image.getImageUrl());
                            System.out.print("\r" + ((++download * 100) / totalImages) + "% finished");
                        } else {
                            break;
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                throw new GPix.GPixException("Keyword missing");
            }

        } catch (GPix.GPixException e) {
            e.printStackTrace();
        }

    }
}
