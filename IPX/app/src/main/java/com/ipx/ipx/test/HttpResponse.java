package com.ipx.ipx.test;

/**
 * http请求响应对象
 * 能够支持http请求线程和ui线程共享数据
 */
public class HttpResponse {
    //请求成功返回的响应json数据
    private String responseJson  = "";
    //请求失败错误信息
    private String errorInfo = "";
    //登陆返回的token信息
    private String token = "";

    public String getResponseJson() {
        return responseJson;
    }

    public synchronized void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public synchronized void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getToken() {
        return token;
    }

    public synchronized void setToken(String token) {
        this.token = token;
    }
}
