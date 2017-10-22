package com.jrejaud.carlot;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by jrejaud on 10/22/17.
 */

public class CarLotApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
