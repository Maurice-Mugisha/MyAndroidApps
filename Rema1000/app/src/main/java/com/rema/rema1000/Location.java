package com.rema.rema1000;

/**
 * Created by MAURICE on 12/05/2017.
 */

public class Location {
    String city;
    String street;
    String block;

    public Location(String city, String street, String block){
        this.city = city;
        this.street = street;
        this.block = block;
    }


    @Override
    public String toString(){
        return city+", "+street+", "+block;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
