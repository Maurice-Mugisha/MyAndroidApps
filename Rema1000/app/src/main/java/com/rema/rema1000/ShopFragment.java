package com.rema.rema1000;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class ShopFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        addSomeShopsProductsAndLocations();

        final ListView listview = (ListView)view.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.listview, getShops());
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "selected: ", Toast.LENGTH_SHORT).show();
                checkOutTheSelectedShop(position);
                ((RemaMenu)getActivity()).changeLook(2);
            }
        });

        return view;
    }


    public String[] getShops(){
        ArrayList<Shop> shops = Shop.getShops();
        String[] shopStrings = new String[shops.size()];
        int c = 0;
        for (Shop s: shops){
            shopStrings[c] = "Shop "+s.toString();
            c++;
        }
        return shopStrings;
    }


    public void addSomeShopsProductsAndLocations(){
        // Creating 50 shops,
        // and then adding 50 products to each shop.
        //The products and shops are strongly typed.

        Random random = new Random(2000);
        Shop[] shop = new Shop[50];
        for(int s = 0; s<(shop.length); s++) {
            shop[s] = new Shop("outlet:"+s, new Location("City"+s, "Street:"+s, ""+(new Random().nextInt(100))));
            for(int p = 0; p<50; p++) {
                String uniqueImageName = "free.jpj";
                double price = (double)random.nextInt();
                shop[s].addToInventory(new Product("Product: "+p, price, uniqueImageName));
            }
        }
    }


    public void checkOutTheSelectedShop(int shopNumber){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ProductFragment productFragment = new ProductFragment();
        productFragment.setShopNumber(shopNumber);
        ft.replace(R.id.fragment2, productFragment).commit();
    }



}
