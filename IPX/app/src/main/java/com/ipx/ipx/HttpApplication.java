package com.ipx.ipx;

import android.app.Application;

import okhttp3.OkHttpClient;


/**
 * app启动时加载,直至app销毁
 */
public class HttpApplication extends Application {
    private String token;
    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public void onCreate() {
        super.onCreate();
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
