package com.ipx.agent.net;

/**
 * 数据回调接口,用于网络请求中的onPostExecute
 *
 * @author joels on 2017/8/17
 **/
public interface DataListener<T> {
    /**
     * 完成时回调方法
     *
     * @param result 返回结果
     */
    void onComplete(T result);

    /**
     * 失败时回调方法
     */
    void onFailure();

    /**
     * 请求过程中调用
     */
    void onProgress(int newProgress);
}
