package com.tingle.tinglev4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by MAURICE on 03/03/2017.
 */

public class ViewThingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_thing, container, false);

        final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_view, getThings());
        final ListView listView = (ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thing removedThing = ThingsDB.get(getActivity()).getThingsDB().remove(position);
                listView.setAdapter(listAdapter);
                Toast.makeText(getActivity(), "Removed: "+removedThing.toString(), Toast.LENGTH_SHORT).show();
                //Refresh the fragment
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerB, new ViewThingFragment())
                        .commit();
            }
        });

        return view;

    }

    public String[] getThings(){
        final ThingsDB thingsDB = ThingsDB.get(this.getActivity());
        final String[] things = new String[thingsDB.size()];
        for(int c=0; c<thingsDB.size(); c++){
            things[c] = thingsDB.get(c).toString();
        }
        return things;
    }


}
