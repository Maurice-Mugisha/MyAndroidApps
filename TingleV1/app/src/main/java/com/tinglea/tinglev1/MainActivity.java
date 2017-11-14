package com.tinglea.tinglev1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Button addThing;
    private TextView newWhat = null, newWhere = null, lastAdded = null;
    private List<Thing> thingsDB = new ArrayList<>();


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
            int s =thingsDB.size();
            savedInstanceState.putString("LAST_WHAT", thingsDB.get(s - 1).getWhat());
            savedInstanceState.putString("LAST_WHERE", thingsDB.get(s - 1).getWhere());
        /**
         * Alternatively, one could simply store a serialized object into the bundle object
         * as savedInstanceState.putSerializable("LAST_ADDED", thingsDB.get(s - 1));
         * then use (Thing)savedInstanceState.getSerializable("LAST_ADDED"); at the retrieving
         * point
         */
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillThingsDB();
        if((savedInstanceState != null)){
            String what = savedInstanceState.getString("LAST_WHAT");
            String where = savedInstanceState.getString("LAST_WHERE");
            thingsDB.add(new Thing(what, where));
        }
        lastAdded = (TextView)findViewById(R.id.last_added);
        updateUI();
        newWhat = (EditText)findViewById(R.id.what);
        newWhere = (EditText)findViewById(R.id.where);
        addThing = (Button)findViewById(R.id.add_button);

        addThing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               if(newWhat.getText().length()>0 && newWhere.getText().length()>0){
                  thingsDB.add(new Thing(newWhat.getText().toString(), newWhere.getText().toString()));
                  newWhat.setText("");
                  newWhere.setText("");
                  Toast.makeText(MainActivity.this, R.string.added_toast, Toast.LENGTH_SHORT).show();
                  updateUI();
               }
            }
        });


    }

    private void fillThingsDB(){
        thingsDB.add(new Thing("Android Phone", "Desk"));
        thingsDB.add(new Thing("Big Nerd Book", "Desk"));
        thingsDB.add(new Thing("Laptop", "Desk"));
        thingsDB.add(new Thing("Grades", "LearnIT"));
        thingsDB.add(new Thing("Maurice Mugisha", "ITU"));
    }

    private void updateUI(){
        int s = thingsDB.size();
        if(s>0) lastAdded.setText(thingsDB.get(s-1).toString());
    }

}
