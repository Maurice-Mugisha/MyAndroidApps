package com.mobile.rejsekort;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MAURICE on 10/04/2017.
 */

public class Trip extends RealmObject{
    @PrimaryKey
    long tripID;
    long UID;
    String startPoint;
    String endPoint;

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public long getTripID() {
        return tripID;
    }

    public void setTripID(long tripID) {
        tripID = tripID;
    }

    public long getUID() {
        return UID;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }
}
