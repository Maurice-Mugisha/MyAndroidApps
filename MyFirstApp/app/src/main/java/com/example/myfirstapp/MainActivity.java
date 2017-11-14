package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);Log.d(TAG, "Creating the activity");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "Executing onStart()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Executing onResume();");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Executing onPause();");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Executing onStop();");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "Executing onDestroy();");
    }

    public void nextPage(View view){
        Log.d(TAG, "Switching to a new activity");
    }
}
