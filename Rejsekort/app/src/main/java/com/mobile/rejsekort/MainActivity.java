package com.mobile.rejsekort;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import io.realm.Realm;


public class MainActivity extends Activity {



    Button registerButton, accountButton, tripButton;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        // Present the user with the check-in and check-out interface
        tripButton = (Button)findViewById(R.id.button1);
        tripButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                ft.replace(R.id.fragment2, new FragmentRegisterTrip()).commit();
                changeLook(1);
            }
        });

        // Load the interface for interacting with the account.
        accountButton = (Button)findViewById(R.id.button2);
        accountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, R.string.retrieve_account_info, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =  fm.beginTransaction();
                ft.replace(R.id.fragment2, new FragmentAccount()).commit();
                changeLook(2);
            }
        });


        // Load the sign up interface.
        registerButton = (Button)findViewById(R.id.button3);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
                changeLook(3);
            }
        });

        // simple check to find if the app already registered a user
        // The aftermath is to either show or remove the register button.
        long pid = realm.where(Person.class).count();
        if(pid > 0){
            registerButton.setVisibility(View.GONE);
        }else{
            accountButton.setVisibility(View.GONE);
            tripButton.setVisibility(View.GONE);
        }

    }




    public void changeLook(int buttonNumber){
        if(buttonNumber == 1) {
            tripButton.setBackgroundColor(0x000000);
            accountButton.setBackgroundColor(Color.DKGRAY);
            registerButton.setBackgroundColor(Color.DKGRAY);
        }else if(buttonNumber == 2){
            accountButton.setBackgroundColor(0x000000);
            tripButton.setBackgroundColor(Color.DKGRAY);
            registerButton.setBackgroundColor(Color.DKGRAY);
        }else if (buttonNumber == 3){
            registerButton.setBackgroundColor(0x000000);
            accountButton.setBackgroundColor(Color.DKGRAY);
            tripButton.setBackgroundColor(Color.DKGRAY);
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }


}
