package com.tingle.tinglev3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by MAURICE on 03/03/2017.
 */
public class ListActivity extends Activity{

    private static ThingsDB thingsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        createListView();

    }

    public void createListView(){

        final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.list_view, getThings());
        listAdapter.setNotifyOnChange(true);
        final ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thing removedThing = ThingsDB.get(ListActivity.this).getThingsDB().get(position);
                listAdapter.remove(listAdapter.getItem(position));
                listView.setAdapter(listAdapter);
                Toast.makeText(ListActivity.this, "Removed: "+removedThing.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String[] getThings(){

        final ThingsDB thingsDB = ThingsDB.get(ListActivity.this);
        final String[] things = new String[thingsDB.size()];
        for(int c=0; c<thingsDB.size(); c++){
            things[c] = thingsDB.get(c).toString();
        }
        return things;
    }

}
