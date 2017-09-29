package com.publisnet.leyesconcejoscomunales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mariano on 29/09/17.
 * email: marianoramirez353@gmail.com
 */

public class RealmBaseFragment extends Fragment {
    protected Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
