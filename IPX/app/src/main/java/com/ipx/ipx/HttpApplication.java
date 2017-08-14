package com.ipx.ipx;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import okhttp3.OkHttpClient;


/**
 * app启动时加载,直至app销毁
 */
public class HttpApplication extends Application {
    private String token;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private static RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getmRefWatcher() {
        return mRefWatcher;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
