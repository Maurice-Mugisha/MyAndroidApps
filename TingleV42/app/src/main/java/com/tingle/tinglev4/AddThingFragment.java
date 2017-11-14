package com.tingle.tinglev4;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MAURICE on 03/03/2017.
 */

public class AddThingFragment extends Fragment{
    private static ThingsDB thingsDB;
    Button addThing;
    EditText newWhat = null, newWhere = null;
    TextView lastAdded = null;

    public AddThingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_add_thing, container, false);

        thingsDB = ThingsDB.get(getActivity());

        newWhat = (EditText)view.findViewById(R.id.what);
        newWhere = (EditText)view.findViewById(R.id.where);
        lastAdded = (TextView)view.findViewById(R.id.last_added);

        addThing = (Button)view.findViewById(R.id.add_button);
        addThing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(newWhat.getText().toString().length()>0 && newWhere.getText().toString().length()>0){

                    thingsDB.addThing(new Thing(newWhat.getText().toString(), newWhere.getText().toString()));
                    newWhat.setText("");
                    newWhere.setText("");
                    Toast.makeText(getActivity(), R.string.added_toast, Toast.LENGTH_SHORT).show();
                    int s = thingsDB.size();

                    if(s>0)
                        lastAdded.setText(thingsDB.get(s-1).toString());

                    Configuration configuration = getResources().getConfiguration();
                    if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        //To refresh the fragment upon deleting
                       getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerB, new ViewThingFragment())
                                .commit();
                    }
                }
            }
        });

        return view;
    }


}
