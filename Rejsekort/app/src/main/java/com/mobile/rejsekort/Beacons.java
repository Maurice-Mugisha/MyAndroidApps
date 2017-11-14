package com.mobile.rejsekort;
import java.util.List;
import java.util.Arrays;

import io.realm.Realm;

/**
 * Created by MAURICE on 26/04/2017.
 */

public class Beacons {

    Realm realm;

    public Beacons(final String place){
        realm = Realm.getDefaultInstance();
        addNewPlace(place);
    }

    private static String[] beacons = {"1A", "1B","1C", "1D","1E",
                        "2A", "2B","2C", "2D","2E",
                        "3A", "3B","3C", "3D","3E",
                        "4A", "4B","4C", "4D","4E",
                        "5A", "5B","5C", "5D","5E"};

    private static List<String> acceptedBeaconsList = Arrays.asList(beacons);


    public static String  getAcceptedBeacon(String beaconString){
        List<String> beaconStringContents = Arrays.asList(beaconString.split(":"));
        if(acceptedBeaconsList.contains(beaconStringContents.get(0)))
             return beaconString;
        return null;
    }

    public int getFloor(String location){
        char[] characters = location.toCharArray();
        return Character.getNumericValue(characters[0]);
    }


    public void addNewPlace(final String placeString){

        final String acceptedBeacon = getAcceptedBeacon(placeString);

        if(acceptedBeacon != null)
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                Place place = new Place();
                place.setPlaceID(placeString);
                realm.insertOrUpdate(place);
            }
        });
    }

   public static String getValidity(String place){
        String validBeacon = getAcceptedBeacon(place);
        if(validBeacon != null)
          return place;
        return null;
    }
}
