package com.tingle.tinglev4;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAURICE on 03/03/2017.
 */

public class ThingsDB {

    private static ThingsDB sThingsDB;
    private List<Thing> mThingsDB;

    public static ThingsDB get(Context context) {
        if (sThingsDB == null) {
            sThingsDB = new ThingsDB(context);
        }
        return sThingsDB;
    }

    public List<Thing> getThingsDB() {
        return mThingsDB;
    }
    public void addThing(Thing thing) {
        mThingsDB.add(thing);
    }
    public int size() {
        return mThingsDB.size();
    }
    public Thing get(int i){
        return mThingsDB.get(i);
    }
    // Fill database for testing purposes
    private ThingsDB(Context context) {
        mThingsDB = new ArrayList<Thing>();
        mThingsDB.add(new Thing("Android Pnone", "Desk"));
        mThingsDB.add(new Thing("Big Nerd Book", "Desk"));
        mThingsDB.add(new Thing("Laptop", "Desk"));
        mThingsDB.add(new Thing("Maurice Mugisha", "ITU"));
    }
}
