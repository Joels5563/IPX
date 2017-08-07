package com.ipx.ipx.test;

import android.os.Handler;
import android.os.Looper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * 封装工具类
 * 这一个类主要将OkHttp3工具类进行封装，用于对数据的传输，包括String,Json,img，表单等数据的提交与获取
 */

public class OkManager {
    //okHttp
    private OkHttpClient client;
    //获得类名
    private final String TAG = OkManager.class.getSimpleName();
    private Handler handler;

    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串数据
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    /**
     * 私有构造器
     */
    private OkManager() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    //采用单例模式获取对象,内部静态类的单例模式
    private static final class OkManagerClient {
        private static final OkManager INSTANCE = new OkManager();
    }

    public static OkManager getInstance() {
        return OkManagerClient.INSTANCE;
    }
}
