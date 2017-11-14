package com.mobile.getexample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.util.Log;
import com.mobile.getexample.ConnectTask.Param;

public class MainActivity extends Activity {

    EditText name;
    EditText age;
    TextView result;

    public final static String RESOURCE = "http://www.itu.dk/people/jacok/MMAD/services/exampleget/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.nameedit);
        age = (EditText)findViewById(R.id.ageedit);
        result = (TextView)findViewById(R.id.result);

    }

    public void submit(View view){
        Param[] param = new Param[2];
        param[0] = new Param("name", name.getText().toString());
        param[1] = new Param("age", age.getText().toString());
        new ConnectTask(RESOURCE, result).execute(param);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("APP CRUSHED HERE", "EXITING");
    }
}
