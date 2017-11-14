package com.mobile.rejsekort;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by MAURICE on 21/04/2017.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("project2.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
