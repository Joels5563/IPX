package com.ipx.agent.net;

import okhttp3.Response;

/**
 * 网络请求结果解析器
 */
public interface ResponseParser<T> {
    //将网络请求的响应解析成需要的对象T
    T parseResponse(Response response);
}
