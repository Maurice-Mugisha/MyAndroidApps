package com.mobile.rejsekort;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MAURICE on 10/04/2017.
 */

public class Account extends RealmObject{
    @PrimaryKey
    String accountNumber;
    long UID;
    String PIN;
    double amount;


    public long getUID(){
        return this.UID;
    }

    public void setUID(long PID){
        this.UID = PID;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }
    public String getAccountNumber(){
        return accountNumber;
    }

    public void setPIN(String PIN){
        this.PIN = PIN;
    }

    public String getPIN(){
        return this.PIN;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }





}
