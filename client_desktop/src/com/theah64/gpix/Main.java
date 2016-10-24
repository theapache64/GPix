package com.theah64.gpix;

import com.sun.istack.internal.NotNull;
import com.theah64.gpix.core.GPix;
import com.theah64.gpix.core.Image;
import com.theah64.gpix.core.NetworkHelper;
import com.theah64.gpix.core.Zipper;
import org.apache.commons.cli.*;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final int DOWNLOAD_FLAG_THUMB_ONLY = 1;
    private static final int DOWNLOAD_FLAG_ORIGINAL_ONLY = 2;
    private static final int DOWNLOAD_FLAG_BOTH_THUMB_AND_IMAGE = 3;

    private static final int DEFAULT_COUNT = 3;

    private static final String FLAG_KEYWORD = "k";

    private static final String FLAG_THUMBNAIL = "t";
    private static final String FLAG_ORIGINAL = "o";
    private static final String FLAG_OUTPUT_DIRECTORY = "d";
    private static final String FLAG_BOTH_ORIGINAL_AND_THUMB = "b";

    private static final String FLAG_KEEP_FILE_NAME = "f";
    private static final String FLAG_ZIPPED_OUTPUT = "z";

    private static final String FLAG_COUNT = "n";

    private static final String FLAG_HELP = "help";

    private static final Options options = new Options()
            .addOption(FLAG_KEYWORD, true, "Keyword")

            .addOption(FLAG_HELP, false, "To display this text")
            .addOption(FLAG_COUNT, true, "Number of images to be downloaded.")
            .addOption(FLAG_OUTPUT_DIRECTORY, true, "Directory to parse the data. If not specified, current directory will be used. ")
            .addOption(FLAG_THUMBNAIL, false, "Download thumbnail only")
            .addOption(FLAG_ORIGINAL, false, "Download original only")

            .addOption(FLAG_KEEP_FILE_NAME, false, "To keep original file name")
            .addOption(FLAG_ZIPPED_OUTPUT, false, "To get output as zipped file")

            .addOption(FLAG_BOTH_ORIGINAL_AND_THUMB, false, "Download  both thumbnail and image");

    private static final String DEFAULT_GPIX_DIR_NAME = "gpix";


    public static void main(String[] args) throws ParseException {

        if (false) {
            final String url = "https://webdesignledger.com/wp-content/uploads/2009/06/small_icons_1.jpg?3423432.ppg24";
            System.out.println(replaceExt("abc.fgdf.jpg", "png"));
            return;
        }


        final CommandLineParser parse = new DefaultParser();
        final CommandLine cmd = parse.parse(options, args);

        if (cmd.hasOption(FLAG_HELP) && args.length == 1) {

            //Show help
            final HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("gpix", "To download bulk images", options, "Please shoot issues to theapache64@gmail.com", true);

        } else {

            //Play

            final String keyword = cmd.getOptionValue(FLAG_KEYWORD);

            try {

                if (keyword != null) {

                    System.out.println("initializing gpix search : " + keyword);

                    String outputDir = cmd.getOptionValue(FLAG_OUTPUT_DIRECTORY);

                    if (outputDir == null) {
                        outputDir = System.getProperty("user.dir") + File.separator + DEFAULT_GPIX_DIR_NAME + File.separator + keyword + "_" + System.currentTimeMillis();
                    }

                    System.out.println("output directory : " + outputDir);

                    final int imgCount = parseInt(cmd.getOptionValue(FLAG_COUNT), DEFAULT_COUNT);

                    System.out.println("number of images : " + imgCount);

                    File oneDir = null, imgDir = null, thumbDir = null;

                    int downloadFlag = -1;

                    if (cmd.hasOption(FLAG_THUMBNAIL)) {

                        oneDir = new File(outputDir);
                        downloadFlag = DOWNLOAD_FLAG_THUMB_ONLY;
                        oneDir.mkdirs();

                        System.out.println("mode: thumbnail only");
                    }

                    if (cmd.hasOption(FLAG_ORIGINAL)) {

                        oneDir = new File(outputDir);
                        downloadFlag = DOWNLOAD_FLAG_ORIGINAL_ONLY;
                        oneDir.mkdirs();

                        System.out.println("mode: original image only");
                    }

                    if (cmd.hasOption(FLAG_BOTH_ORIGINAL_AND_THUMB)) {
                        downloadFlag = DOWNLOAD_FLAG_BOTH_THUMB_AND_IMAGE;

                        imgDir = new File(outputDir + File.separator + "img");
                        thumbDir = new File(outputDir + File.separator + "thumb");

                        imgDir.mkdirs();
                        thumbDir.mkdirs();

                        System.out.println("mode: both original and thumbnail.");
                    }

                    if (downloadFlag == -1) {
                        //No flag specified, setting default flag
                        downloadFlag = DOWNLOAD_FLAG_THUMB_ONLY;
                        oneDir = new File(outputDir);
                        oneDir.mkdirs();

                        System.out.println("default mode: thumbnail only");
                    }


                    GPix gi = GPix.getInstance();

                    try {


                        System.out.println("searching... please wait... this may take a minute.");
                        @NotNull final List<Image> images = gi.search(keyword, imgCount);


                        final int totalImages = images.size();

                        System.out.println(totalImages + " images found");
                        System.out.println("preparing download for " + imgCount + " image(s).");

                        int download = 0;

                        for (int i = 0; i < imgCount; i++) {

                            if (i < totalImages) {

                                final Image image = images.get(i);
                                boolean isDownloaded = false;

                                final String fullFileName;

                                if (cmd.hasOption(FLAG_KEEP_FILE_NAME)) {
                                    fullFileName = getFileNameFromUrl(image.getImageUrl());
                                } else {
                                    fullFileName = getFileName(keyword + "_" + i, image.getImageUrl());
                                }


                                switch (downloadFlag) {

                                    case DOWNLOAD_FLAG_THUMB_ONLY:
                                        isDownloaded = NetworkHelper.download(oneDir, image.getThumbImageUrl(), replaceExt(fullFileName, "jpg"));
                                        break;

                                    case DOWNLOAD_FLAG_ORIGINAL_ONLY:
                                        isDownloaded = NetworkHelper.download(oneDir, image.getImageUrl(), fullFileName);
                                        break;

                                    case DOWNLOAD_FLAG_BOTH_THUMB_AND_IMAGE:

                                        NetworkHelper.download(thumbDir, image.getThumbImageUrl(), replaceExt(fullFileName, "jpg"));
                                        isDownloaded = NetworkHelper.download(imgDir, image.getImageUrl(), fullFileName);
                                        break;
                                }

                                if (!isDownloaded) {
                                    System.out.println("error while downloading " + image.getImageUrl());
                                }

                                final int perc = (++download * 100) / imgCount;
                                System.out.print(perc + "% finished\r");

                            } else {
                                break;
                            }

                        }

                        System.out.println("download finished");

                        if (cmd.hasOption(FLAG_ZIPPED_OUTPUT)) {

                            final Zipper zipper = new Zipper(outputDir, outputDir + ".zip");
                            zipper.setCallback(new Zipper.ZipperProgressCallback() {
                                @Override
                                public void onStart() {
                                    System.out.println("zipping output...");
                                }

                                @Override
                                public void onProgress(int perc) {
                                    System.out.print(perc + "% finished\r");
                                }

                                @Override
                                public void onFinish() {
                                    System.out.println("zipping finished");
                                    zipper.deleteInput();
                                }
                            });
                            zipper.startZipping();
                        }

                    } catch (JSONException | IOException e) {
                        throw new GPix.GPixException(e.getMessage());
                    }


                } else {
                    throw new GPix.GPixException("keyword missing");
                }

            } catch (GPix.GPixException e) {
                System.out.println("error : " + e.getMessage());
            }


        }


    }

    private static final String[] IMAGE_EXTENSIONS = {"jpg", "png", "jpeg"};

    private static String replaceExt(final String fullFileName, final String ext) {
        if (fullFileName.contains(".")) {
            final String fileNameWithOutExt = fullFileName.substring(0, fullFileName.lastIndexOf('.'));
            return fileNameWithOutExt + "." + ext;
        }
        return fullFileName + "." + ext;
    }

    private static String getFileName(final String fileName, String imageUrl) {

        if (imageUrl.contains(".")) {
            final String[] dotsSeps = imageUrl.split("\\.");
            String susExt = dotsSeps[dotsSeps.length - 1];
            if (susExt != null) {
                susExt = susExt.trim();
                for (final String ext : IMAGE_EXTENSIONS) {
                    if (susExt.contains(ext)) {
                        return fileName + "." + ext;
                    }
                }
            }
        }

        //Couldn't find an extension, so return the complete file name from the url.
        return getFileNameFromUrl(imageUrl);
    }

    private static String getFileNameFromUrl(String imageUrl) {
        final String[] slashParts = imageUrl.split("/");
        String fileName = slashParts[slashParts.length - 1];
        final String[] dotParts = fileName.split("\\.");
        final String ext = dotParts[dotParts.length - 1];
        for (final String supExt : IMAGE_EXTENSIONS) {
            if (ext.contains(supExt)) {
                fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + supExt;
            }
        }
        return fileName;
    }

    private static int parseInt(String string, int defValue) {

        if (string != null) {

            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e) {
                return defValue;
            }
        }

        return defValue;
    }

}
