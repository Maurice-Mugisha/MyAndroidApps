package com.example.androidreviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Register extends AppCompatActivity {
    private static final String MESSAGE = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(MESSAGE, "Starting the registration process");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(MESSAGE, "The registration has been paused");
    }
}
