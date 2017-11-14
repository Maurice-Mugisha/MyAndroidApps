package com.dsb.rejsekorta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity {

    Button register;
    EditText firstName, lastName, cprNumber, accountNumber, pin;
    //Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        register = (Button) findViewById(R.id.registerButton);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        cprNumber = (EditText) findViewById(R.id.cprNumber);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        pin = (EditText) findViewById(R.id.pin);

        //Realm.init(this);
        //realm = Realm.getDefaultInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*realm.beginTransaction();

                Account account = realm.createObject(Account.class);
                account.setPID(cprNumber.getText().toString());
                account.setAccountNumber(accountNumber.getText().toString());
                account.setPIN(pin.getText().toString());

                Person person = realm.createObject(Person.class);
                person.setFirstName(firstName.getText().toString());
                person.setLastName(lastName.getText().toString());
                person.setPID(cprNumber.getText().toString());
                */
                Toast.makeText(Registration.this, R.string.registration, Toast.LENGTH_SHORT).show();

                //realm.commitTransaction();
                backToMainActivity();
            }
        });
    }

    public void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
