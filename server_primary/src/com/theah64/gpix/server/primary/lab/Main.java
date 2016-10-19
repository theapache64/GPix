package com.theah64.gpix.server.primary.lab;

import com.theah64.gpix.server.primary.core.GPix;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by theapache64 on 19/10/16,8:12 AM.
 */
public class Main {
    public static void main(String[] args) throws GPix.GPixException, JSONException, IOException {
        System.out.println(GPix.getInstance().search("http://gpix_server1.net23.net/?keyword=%s", "Car", true).size());
    }

}
