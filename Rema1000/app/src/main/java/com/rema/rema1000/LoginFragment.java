package com.rema.rema1000;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {



    EditText email, password;
    Button loginFragmentButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_login_fragment, container, false);

        email = (EditText)view.findViewById(R.id.email);
        password = (EditText)view.findViewById(R.id.password);
        loginFragmentButton = (Button)view.findViewById(R.id.loginFragmentButton);

        loginFragmentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (validate(email) && validate(password));
                    // authenticate somewhere, skipping for now
                changeToRemaMenu();
            }
        });

        return view;
    }



    public boolean validate(EditText editText){
        if(editText.getText().toString().length() > 3)
            return true;
        else
            return false;
    }


    public void changeToRemaMenu(){
        Intent intent = new Intent(getActivity(), RemaMenu.class);
        startActivity(intent);
    }

    public String getString(EditText editText){
        return editText.getText().toString();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}
