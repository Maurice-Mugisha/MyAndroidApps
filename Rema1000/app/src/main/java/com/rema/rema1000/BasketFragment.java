package com.rema.rema1000;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BasketFragment extends Fragment {

    ArrayList<Product> purchases = new ArrayList<>();
    TextView textView;

    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        textView = (TextView)view.findViewById(R.id.basket_items);
        purchases = ((RemaMenu)getActivity()).getPurchases();
        if (purchases.size()>0)
           showReceipt(purchases);


        return view;
    }

    public void showReceipt(ArrayList<Product> products){
        double total = 0;
        int n = 0;
        textView.setText("********RECEIPT********\n\n");
        for (Product p: products){
            textView.append(n+": "+p.getName()+"     "+p.getPrice()+"\n");
            total += p.getPrice();
            n++;
        }
        textView.append("\n\n\nTOTAL: "+total+"DKK");
    }

}
