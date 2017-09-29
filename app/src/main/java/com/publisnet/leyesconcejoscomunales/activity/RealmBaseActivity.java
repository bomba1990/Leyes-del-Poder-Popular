package com.publisnet.leyesconcejoscomunales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mariano on 29/09/17.
 * email: marianoramirez353@gmail.com
 */

public class RealmBaseActivity extends AppCompatActivity{
    protected Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
