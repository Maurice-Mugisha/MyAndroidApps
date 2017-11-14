package com.rema.rema1000;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SignUpFragment extends Fragment {


    EditText name, email, accountNumber, pin;
    Button signUpFragmentButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_sign_up_fragment, container, false);

        name = (EditText)view.findViewById(R.id.name);
        email = (EditText)view.findViewById(R.id.email);
        accountNumber = (EditText)view.findViewById(R.id.accountNumber);
        pin = (EditText)view.findViewById(R.id.pin);

        signUpFragmentButton = (Button)view.findViewById(R.id.signUpFragmentButton);
        signUpFragmentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (validate(name) && validate(email) && validate(accountNumber) && validate(pin));
                // Register and go back to the First Activity

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



    public String getString(EditText editText){
        return editText.getText().toString();
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}
