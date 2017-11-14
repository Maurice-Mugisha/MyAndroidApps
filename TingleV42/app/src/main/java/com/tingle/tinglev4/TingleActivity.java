package com.tingle.tinglev4;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class TingleActivity extends Activity {

    private static ThingsDB thingsDB;
    Button addThing, viewThings;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        ThingsDB thingsDB = ThingsDB.get(this);
        int s = thingsDB.size();
        savedInstanceState.putString("LAST_WHAT", thingsDB.get(s - 1).getWhat());
        savedInstanceState.putString("LAST_WHERE", thingsDB.get(s - 1).getWhere());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingle);

        thingsDB = ThingsDB.get(TingleActivity.this);
        if((savedInstanceState != null)){
            String what = savedInstanceState.getString("LAST_WHAT");
            String where = savedInstanceState.getString("LAST_WHERE");
            thingsDB.addThing(new Thing(what, where));
        }

        Configuration configuration = getResources().getConfiguration();
        FragmentTransaction ft;
        FragmentManager fm = getFragmentManager();
        ft = fm.beginTransaction();

        viewThings = (Button)findViewById(R.id.view_fragment_button);
        addThing = (Button)findViewById(R.id.add_button);


        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ft.replace(R.id.containerA, new FrontFragment()).commit();
        }else{
            ft.replace(R.id.containerA, new AddThingFragment());
            ft.replace(R.id.containerB, new ViewThingFragment());
            ft.commit();
        }

    }

}
