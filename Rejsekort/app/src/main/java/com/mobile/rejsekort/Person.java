package com.mobile.rejsekort;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MAURICE on 11/04/2017.
 */

public class Person extends RealmObject {

    String firstName;
    String lastName;
    @PrimaryKey
    long PID;


    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setPID(long PID){
        this.PID = PID;
    }
    public long getPID(){
        return this.PID;
    }



}
