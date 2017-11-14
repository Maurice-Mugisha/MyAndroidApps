package com.mobile.rejsekort;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MAURICE on 25/04/2017.
 */

public class Place extends RealmObject {
    @PrimaryKey
    String placeID;


    public String getPlaceID() {
        return this.placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

}
