package com.theah64.gpix.util;

import java.util.Random;

/**
 * Created by theapache64 on 9/4/16.
 */
public class RandomString {


    private static final String randomEngine = "0123456789AaBbCcDdEeFfGgHhIiJjKkLkMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    private static Random random;

    public static String getNewApiKey(final int apiKeyLength) {
        return getRandomString(apiKeyLength);
    }


    public static String getRandomString(final int length) {
        if (random == null) {
            random = new Random();
        }
        final StringBuilder apiKeyBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            apiKeyBuilder.append(randomEngine.charAt(random.nextInt(randomEngine.length())));
        }
        return apiKeyBuilder.toString();
    }

    public static String getRandomFilename(final int fileNameLength, final String fileExtension) {
        return getRandomString(fileNameLength) + fileExtension;
    }
}
