package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.models.Server;

import java.util.List;

/**
 * Created by theapache64 on 19/10/16.
 */
public class Servers extends BaseTable<Server> {

    private static final Servers instance = new Servers();

    private Servers() {
        super("servers");
    }

    public static Servers getInstance() {
        return instance;
    }


    @Override
    public List<Server> getAll(String whereColumn, String whereColumnValue) {
        //TODO: Write query to return all active server order by the usage desc.
        return null;
    }
}
