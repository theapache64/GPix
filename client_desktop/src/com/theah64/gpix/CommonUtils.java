package com.theah64.gpix.core;

/**
 * Created by shifar on 15/10/16.
 */
public class CommonUtils {

    public static int parseInt(String string, int defValue) {

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
