package com.rema.rema1000;
import java.util.ArrayList;

/**
 * Created by MAURICE on 12/05/2017.
 */

public class Shop {



    String name;
    Location location;
    Product product;


    private static ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<Product>  productList = new ArrayList<>();



    public Shop(String name, Location location){
        this.name = name;
        this.location = location;
        shops.add(this);
    }

    @Override
    public String toString(){
        return this.name+",  Location: "+this.location.toString();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void addToInventory(Product product){
        this.productList.add(product);
    }

    public Product getFromInventory(int pid){
        return productList.get(pid);
    }

    public ArrayList<Product> getInventory(){
        return this.productList;
    }



    public void removeFromInventory(int pid){
        productList.remove(pid);
    }

    public static ArrayList<Shop> getShops(){
        return shops;
    }
}
