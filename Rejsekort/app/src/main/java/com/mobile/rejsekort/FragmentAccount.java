package com.mobile.rejsekort;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

/**
 * Created by MAURICE on 05/04/2017.
 */

public class FragmentAccount extends Fragment {

    EditText addedMoney;
    TextView errorTextView, accountBalanceTextView;
    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        addedMoney = (EditText)view.findViewById(R.id.addedMoney);
        errorTextView = (TextView)view.findViewById(R.id.accountBalanceErrorText);
        accountBalanceTextView = (TextView)view.findViewById(R.id.accountBalance);

        double balance = realm.where(Account.class).findFirst().getAmount();
        accountBalanceTextView.setText("Balance: "+(balance+0));

        Button accountChangesButton = (Button)view.findViewById(R.id.saveAccountChangesButton);
        accountChangesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // add the money to the bank account
                creditAccount();
            }
        });

        return view;
    }

    public double getMoney(String money){
        return Double.parseDouble(money);
    }

    public void creditAccount(){
        if(addedMoney.getText().toString().length() > 0){

            final long pid = realm.where(Person.class).findFirst().getPID();
            final String accountNumber = realm.where(Account.class).findFirst().getAccountNumber();
            final String pin = realm.where(Account.class).findFirst().getPIN();
            final double balance = realm.where(Account.class).findFirst().getAmount();

            final double money = getMoney(addedMoney.getText().toString());

            realm.executeTransactionAsync(new Realm.Transaction(){
                @Override
                public void execute(Realm realm){
                    Account account = new Account();
                    account.setUID(pid);
                    account.setPIN(pin);
                    account.setAccountNumber(accountNumber);
                    account.setAmount((money+balance));
                    realm.insertOrUpdate(account);
                }
            }, new Realm.Transaction.OnSuccess() {
                 @Override
                 public void onSuccess() {
                     accountBalanceTextView.setText("Balance: "+(money+balance));
                     addedMoney.setText("");
                 }
             }, new Realm.Transaction.OnError() {
                 @Override
                 public void onError(Throwable error) {
                     errorTextView.setText("Failed to add money");
                     //Log.d("FAILED", "failed to add the trip, please try again");
                 }
              }
            );

        }else{
            errorTextView.setText("Invalid input. Only digits are allowed");
        }
    }
}
