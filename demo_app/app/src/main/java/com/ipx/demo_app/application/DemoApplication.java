package com.ipx.demo_app.application;

import android.app.Application;

import com.ipx.demo_app.db.DataBaseHelper;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class DemoApplication extends Application {
    private static RefWatcher mRefWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseHelper.init(this);
        mRefWatcher = LeakCanary.install(this);
    }


    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }
}
