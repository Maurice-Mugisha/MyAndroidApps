package com.rema.rema1000;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button signUpButton, loginButton;
    volatile boolean loginClicked, signUpClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpButton = (Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                ft.replace(R.id.fragment1, new SignUpFragment()).commit();
                signUpClicked = true;
                loginClicked = false;
                changeLook(false, true);
            }
        });

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                ft.replace(R.id.fragment1, new LoginFragment()).commit();
                loginClicked = true;
                signUpClicked = false;
                changeLook(true, false);
            }
        });

    }


    public void changeLook(boolean loginClicked, boolean signUpClicked){
        if(loginClicked == true) {
            loginButton.setBackgroundColor(Color.DKGRAY);
            signUpButton.setBackgroundColor(Color.rgb(255, 100, 20));
        }else if(signUpClicked == true){
            signUpButton.setBackgroundColor(Color.DKGRAY);
            loginButton.setBackgroundColor(Color.rgb(255, 100, 20));
        }
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}
