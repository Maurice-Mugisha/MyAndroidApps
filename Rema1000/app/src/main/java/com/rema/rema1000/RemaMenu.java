package com.rema.rema1000;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class RemaMenu extends Activity {



    Button shop, product, basket;
    static ArrayList<Product> purchases = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rema_menu);

        shop = (Button)findViewById(R.id.shop);
        shop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                ft.replace(R.id.fragment2, new ShopFragment()).commit();
                changeLook(1);
            }
        });

        product = (Button)findViewById(R.id.product);
        product.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                ft.replace(R.id.fragment2, new ProductFragment()).commit();
                changeLook(2);
            }
        });

        basket = (Button)findViewById(R.id.basket);
        basket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                ft.replace(R.id.fragment2, new BasketFragment()).commit();
                changeLook(3);
            }
        });



    }


    public void changeLook(int buttonNumber){
        if(buttonNumber == 1) {
            shop.setBackgroundColor(0x000000);
            product.setBackgroundColor(Color.DKGRAY);
            basket.setBackgroundColor(Color.DKGRAY);
        }else if(buttonNumber == 2){
            shop.setBackgroundColor(Color.DKGRAY);
            product.setBackgroundColor(0x000000);
            basket.setBackgroundColor(Color.DKGRAY);
        }else if (buttonNumber == 3){
            shop.setBackgroundColor(Color.DKGRAY);
            product.setBackgroundColor(Color.DKGRAY);
            basket.setBackgroundColor(0x000000);
        }
    }


    public static void createBasket(ArrayList<Product> userPurchases){
        purchases = userPurchases;
    }

    public ArrayList<Product> getPurchases(){
        return purchases;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        RemaMenu.purchases = null;
    }



}
