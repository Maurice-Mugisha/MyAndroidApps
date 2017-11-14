package com.tingle.tinglev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {

    private static ThingsDB thingsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        thingsDB = ThingsDB.get(ListActivity.this);
        String[] things = new String[thingsDB.size()];
        for(int c=0; c<thingsDB.size(); c++){
            things[c] = thingsDB.get(c).toString();
        }
        createListView(things);

    }

    public void createListView(String ... things){
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.list_view, things);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
    }
}
