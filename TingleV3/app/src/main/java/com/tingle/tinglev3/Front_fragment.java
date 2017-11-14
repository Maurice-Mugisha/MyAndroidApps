package com.tingle.tinglev3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by MAURICE on 02/03/2017.
 */

public class Front_fragment extends Fragment {

    public Front_fragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view  = inflater.inflate(R.layout.action_fragment, container, false);

        Button addThings = (Button)view.findViewById(R.id.add_fragment_button);
        addThings.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){

                Configuration configuration = getResources().getConfiguration();
                FragmentTransaction ft;
                FragmentManager fm = getFragmentManager();
                ft = fm.beginTransaction();

                if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ft.replace(R.id.containerB, new AddThingFragment()).commit();
                }
            }
        });

        Button viewThings = (Button)view.findViewById(R.id.view_fragment_button);
        viewThings.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                Configuration configuration = getResources().getConfiguration();
                FragmentTransaction ft;
                FragmentManager fm = getFragmentManager();
                ft = fm.beginTransaction();

                if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
