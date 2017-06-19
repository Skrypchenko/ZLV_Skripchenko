package com.example.zlv_skripchenko;

import android.app.Application;

import com.example.zlv_skripchenko.db.DatabaseOpenHelper;
import com.example.zlv_skripchenko.network.NetworkController;

/**
 * Created by Yevgen on 15.06.2017.
 */

public class MyAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseOpenHelper.getDatabaseOpenHelperInstance(getApplicationContext());
        NetworkController.getInstance().start(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabaseOpenHelper.getDatabaseOpenHelperInstance().close();
    }
}
