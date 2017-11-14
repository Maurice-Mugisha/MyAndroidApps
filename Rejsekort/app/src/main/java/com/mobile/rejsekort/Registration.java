package com.mobile.rejsekort;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import io.realm.Realm;



public class Registration extends Activity {


    Realm realm;
    EditText firstName, lastName, accountNumber, pin;
    long key = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        realm = Realm.getDefaultInstance();

        long personResult = findLastId();
        key = personResult + 1;

        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        accountNumber = (EditText)findViewById(R.id.accountNumber);
        pin = (EditText)findViewById(R.id.pin);


        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
             // save user data
             if(checkStringLength(firstName)
               && checkStringLength(lastName)
               && checkStringLength(accountNumber)
               && checkStringLength(pin)) {
                 saveUserData(firstName.getText().toString(), lastName.getText().toString(), key);
                 saveAccountData(accountNumber.getText().toString(), pin.getText().toString(), key);
                 //showDialog();
                 backToMainActivity();
             }
            }
        });
    }



    public void saveUserData(final String firstName, final String lastName, final long pkey){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
              Person person = new Person();
              person.setPID(pkey);
              person.setFirstName(firstName);
              person.setLastName(lastName);
              realm.insertOrUpdate(person);
            }
        });
    }



    public void saveAccountData(final String accountNumber, final String pin, final long pkey){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
              Account account = new Account();
              account.setUID(pkey);
              account.setPIN(pin);
              account.setAccountNumber(accountNumber);
              account.setAmount(0);
              realm.insertOrUpdate(account);
            }
        });
    }



    public long findLastId(){
        long pid = realm.where(Person.class).count();
        return pid;
    }



    public boolean checkStringLength(EditText string){
        return (string.getText().toString().length() > 0);
    }



    public void backToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Registration.this);
        alert.setCancelable(true);
        alert.setTitle("Registration");
        alert.setMessage("Successfully registered.");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
                backToMainActivity();
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }


}
