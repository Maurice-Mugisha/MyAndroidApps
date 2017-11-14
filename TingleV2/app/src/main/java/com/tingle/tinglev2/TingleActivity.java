package com.tingle.tinglev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class TingleActivity extends AppCompatActivity {
    
    private TextView newWhat = null, newWhere = null, lastAdded = null;
    private static ThingsDB thingsDB;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        int s = thingsDB.size();
        savedInstanceState.putString("LAST_WHAT", thingsDB.get(s - 1).getWhat());
        savedInstanceState.putString("LAST_WHERE", thingsDB.get(s - 1).getWhere());

         /** Alternatively, one could simply store a serialized object into the bundle object
         *   as savedInstanceState.putSerializable("LAST_ADDED", thingsDB.get(s - 1));
         *   then use (Thing)savedInstanceState.getSerializable("LAST_ADDED"); at the retrieving
         *   point
          */

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
        lastAdded = (TextView)findViewById(R.id.last_added);
        updateUI();
        newWhat = (EditText)findViewById(R.id.what);
        newWhere = (EditText)findViewById(R.id.where);
        Button addThing = (Button)findViewById(R.id.add_button);

        addThing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(newWhat.getText().length()>0 && newWhere.getText().length()>0){
                    thingsDB.addThing(new Thing(newWhat.getText().toString(), newWhere.getText().toString()));
                    newWhat.setText("");
                    newWhere.setText("");
                    Toast.makeText(TingleActivity.this, R.string.added_toast, Toast.LENGTH_SHORT).show();
                    updateUI();
                }
            }
        });

        //viewThing = (Button) findViewById(R.id.view_button);
        //viewThing.setOnClickListener(new View.OnClickListener(){
        //    @Override
        //    public void onClick(View view){
        //        if(viewThing.getText().toString() == "VIEW ITEMS")
        //            viewThing.setText("HIDE");
        //        else
        //            viewThing.setText("VIEW ITEMS");
        //    }
        //});

    }

    public void changeToViewList(View view){
        Intent changeTo = new Intent(TingleActivity.this, ListActivity.class);
        startActivity(changeTo);
    }

    private void updateUI(){
        int s = thingsDB.size();
        if(s>0) lastAdded.setText(thingsDB.get(s-1).toString());
    }

}
