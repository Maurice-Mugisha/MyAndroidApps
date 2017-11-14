package com.rema.rema1000;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class ProductFragment extends Fragment {

    int shopNumber = 0;
    Boolean shopSelected;
    ArrayList<String> entireProductList = new ArrayList<>();
    ArrayList<Product> entireProductObjectsList = new ArrayList<>();
    ArrayList<Product> selectedShopProductObjectsList = new ArrayList<>();
    ArrayList  basket = new ArrayList<>();
    ArrayList<Product> purchases = new ArrayList<>();



    public ProductFragment() {
        // Required empty public constructor
        shopSelected = false;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        /**
         * If a customer clicks on a shop (in the shop fragment)
         * then its products will be selected and displayed
         * Otherwise, all products from all shops will be displayed here
         * Products from the same shop are displayed next to each other.
         **/

        GridView gridview = (GridView)view.findViewById(R.id.list_of_products);
        ArrayAdapter<String> adapter = null;
        if (shopSelected == false) {
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview, getAllShopsProducts()) {
                @Override
                public View getView(final int position, View viewItem, ViewGroup parent) {
                    CheckBox checkBox = new CheckBox(getContext());
                    checkBox.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            addToBasket(position);
                            Toast.makeText(getActivity(), "Added to basket", Toast.LENGTH_LONG).show();
                        }
                    });
                    checkBox.setText((position + 1) + ":"+entireProductList.get(position));
                    viewItem = checkBox;
                    // adding background images, this is definately not the proffessional way
                    if (position == 1)viewItem.setBackgroundResource(R.drawable.drone);
                    else if (position == 2)viewItem.setBackgroundResource(R.drawable.free);
                    else if (position % 2 == 0)viewItem.setBackgroundResource(R.drawable.image4);
                    else if (position % 3 == 0)viewItem.setBackgroundResource(R.drawable.image3);
                    return viewItem;
                }
            };
        }
        else {
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview, getProducts()) {
                @Override
                public View getView(final int position, View viewItem, ViewGroup parent) {
                    CheckBox checkBox = new CheckBox(getContext());
                    String[] name = getProducts();
                    checkBox.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            addToBasket(position);
                            Toast.makeText(getActivity(), "Added to basket", Toast.LENGTH_LONG).show();
                        }
                    });
                    checkBox.setText((position + 1) + ":"+name[position]);
                    viewItem = checkBox;
                    if (position == 1)viewItem.setBackgroundResource(R.drawable.drone);
                    else if (position == 2)viewItem.setBackgroundResource(R.drawable.free);
                    else if (position % 2 == 0)viewItem.setBackgroundResource(R.drawable.image4);
                    else if (position % 3 == 0)viewItem.setBackgroundResource(R.drawable.image3);
                    return viewItem;
                }
            };
        }
        gridview.setAdapter(adapter);

        return view;
    }



    public void setShopNumber(int shopNumber){
        this.shopNumber = shopNumber;
        shopSelected = true;
    }



    public String[] getProducts(){
        Shop shop = getTheShop();
        ArrayList<Product> productsList = shop.getInventory();
        selectedShopProductObjectsList = productsList;
        return getProductsStringArray(productsList);
    }



    public Shop getTheShop(){
        return Shop.getShops().get(shopNumber);
    }


    public String[] getAllShopsProducts(){
        Shop[] shops = getAllShopsInAnArray();
        String[] allProducts = null;

        for (Shop shop: shops)
            addToEntireProductList(shop.getInventory());

        allProducts = new String[entireProductList.size()];
        int p = 0;
        for (String s: entireProductList) {
            allProducts[p] = s;
            p++;
        }
        return allProducts;
    }



    public void addToEntireProductList(ArrayList<Product> list){
        for (Product p: list) {
            entireProductList.add(p.toString());
            entireProductObjectsList.add(p);
        }
    }

    public String[] getProductsStringArray(ArrayList<Product> productsList){
        String[] productsArray = new String[productsList.size()];
        int count = 0;
        for (Product p: productsList){
            productsArray[count] = p.toString();
            count++;
        }
        return productsArray;
    }


     public Shop[] getAllShopsInAnArray(){
         ArrayList<Shop> list = Shop.getShops();
         Shop[] shops = new Shop[list.size()];
         int x = 0;
         for (Shop shop: list) {
             shops[x] = shop;
             x++;
         }
         return shops;
     }


    public void addToBasket(int id){
      if (basket.contains(id))
          basket.remove(id); // unchecked the item by clicking again
      else {
          basket.add(id);
      }
    }




    @Override
    public void onDestroy(){
       super.onDestroy();

         for (int n = 0; n<basket.size(); n++){
             int id = (int) basket.get(n);
           if(shopSelected == false) {
               purchases.add(entireProductObjectsList.get(id));
           }else{
               purchases.add(selectedShopProductObjectsList.get(id));
           }
       }
        RemaMenu.createBasket(purchases);
    }


}
