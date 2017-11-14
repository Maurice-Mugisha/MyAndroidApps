package com.example.androidreviewtest;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_STRING = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_STRING, "Starting the App");
        setContentView(R.layout.activity_main);
    }

    public void register(View v){
        Log.d(LOG_STRING, "First Time User Registration: FTUR");
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

    }

    public void login(View v){
         Log.d(LOG_STRING, "Normal Login Starting: NLS");
         Intent intent = new Intent(this, Login.class);
         startActivity(intent);
    }
}
