package com.ipx.demo_app.application;

import android.app.Application;

import com.ipx.demo_app.db.DataBaseHelper;


public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseHelper.init(this);
    }

}
