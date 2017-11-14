package com.mobile.rejsekort;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;



import io.realm.Realm;


/**
 * Created by MAURICE on 05/04/2017.
 */

public class FragmentRegisterTrip extends Fragment {

    Realm realm;
    TextView progressTextView, devices;

    public final static int REQUEST_ENABLE_BT = 1;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 100000;
    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler = new Handler();

    AtomicBoolean journeyProgress = new AtomicBoolean();
    AtomicBoolean alreadyCheckedOut = new AtomicBoolean();
    AtomicInteger count = new AtomicInteger(0);

    long startTime, elapsedTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_register_trip, container, false);

        progressTextView = (TextView)view.findViewById(R.id.progressTextView);


        Button checkInButton = (Button)view.findViewById(R.id.checkInButton);
        checkInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(checkBluetooth() == true) {
                     journeyProgress.set(true);
                     alreadyCheckedOut.set(false);
                     progressTextView.setText("");
                     showProgress(true);
                    count.set(0);
                     startTime = System.currentTimeMillis();
                }else {
                    devices.append("Please enable bluetooth." + "\n");
                }
            }
        });

        Button checkOutButton = (Button)view.findViewById(R.id.checkOutButton);
        checkOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                journeyProgress.set(false);
                alreadyCheckedOut.set(true);
                showProgress(false);
            }
        });

        devices = (TextView)view.findViewById(R.id.list_of_devices);

        return view;
    }

    public boolean checkBluetooth(){
        final BluetoothManager bluetoothManager = (BluetoothManager)getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if((mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled())) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        return (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled());
    }


    protected void showStations() {
        scanLeDevice(journeyProgress.get());
    }


    private void scanLeDevice(boolean enable) {

        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    devices.append("...." + "\n");
                }
            }, SCAN_PERIOD);
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }


    }


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String checkBeaconValidity = Beacons.getValidity(device.toString());
                            elapsedTime = System.currentTimeMillis();

                            if(checkBeaconValidity != null && alreadyCheckedOut.get() == false){
                                count.getAndIncrement();
                                trackCheckIn();
                                devices.append(count.get()+": "+device.toString() + "\n");
                            }else
                             if((elapsedTime - startTime) > 6000 && alreadyCheckedOut.get() == false && count.get() == 0){
                                devices.append("Timed out before checkIn. Please move closer and check in again.");
                                journeyProgress.set(false);
                                alreadyCheckedOut.set(true);
                                showStations();
                              }

                        }
                    });
                }
            };



    public void trackCheckIn(){
        int currentCount = count.get();
        if(currentCount == 1 ) {
            progressTextView.setText("Checked In\n");
            progressTextView.append("Starting trip ...\n\n");
        }
    }



    public void showProgress(boolean status){
        if(status) {
            if (journeyProgress.get() == true) {
                devices.setText("Waiting to check in ...\n");
                showStations();
            }
        }
        else {
            if(count.get() >= 1 && alreadyCheckedOut.get() == true){
                chargeAccount();
                showStations();
            }else{
               progressTextView.setText("Canceled, before check in.");
            }
        }
    }



    // Handling checkout
    public void chargeAccount(){
        //Charge and update the account.
        final double charge = calculateCharge();

        final long pid = realm.where(Person.class).findFirst().getPID();
        final String accountNumber = realm.where(Account.class).findFirst().getAccountNumber();
        final String pin = realm.where(Account.class).findFirst().getPIN();
        final double balance = realm.where(Account.class).findFirst().getAmount();


        devices.append("Checked out");

        realm.executeTransactionAsync(new Realm.Transaction(){
           @Override
           public void execute(Realm realm){
              Account account = new Account();
              account.setUID(pid);
              account.setPIN(pin);
              account.setAccountNumber(accountNumber);
              account.setAmount((balance-charge));
              realm.insertOrUpdate(account);
              }
           }, new Realm.Transaction.OnSuccess() {
              @Override
              public void onSuccess() {
              // show the charge, balance and status
              progressTextView.setText("Charged fee: "+(charge)
                                                      +"\n"+"Balance: "+(balance-charge)
                                                      +"\nStatus: checked Out after "+(count.get())+" stops\n\n\n\n"
                                                      +"Trip ended\n");
              saveTrip(10, 10, "Start point", "End point");
              count.set(0);                             }
           }, new Realm.Transaction.OnError() {
             @Override
             public void onError(Throwable error) {}
            }
        );

    }



    public double calculateCharge(){
        return (15.00*(count.get()-1));
    }


    public void saveTrip(final long UID, final long tripID, final String start, final String stop){
        realm.executeTransactionAsync(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
           Trip trip = new Trip();
           trip.setTripID(tripID);
           trip.setUID(UID);
           trip.setStartPoint(start);
           trip.setEndPoint(stop);
           realm.insertOrUpdate(trip);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "Trip completed.", Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
              //Log.d("FAILED", "failed to add the trip, please try again");
            }
        });
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }





}
