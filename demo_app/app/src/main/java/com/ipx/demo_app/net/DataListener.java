package com.ipx.demo_app.net;

/**
 * 数据回调接口,用于网络请求中的onPostExecute
 *
 * @author joels on 2017/8/17
 **/
public interface DataListener<T> {
    void onComplete(T result);
    void onFailure();
}
