package com.ipx.ipx.test;

/**
 * Created by Joels.Zhou on 2017/1/12
 * 控制器返回前台的响应头
 */
public class ResponseHeader {
    /**
     * 消息码
     */
    private String code;
    /**
     * 消息
     */
    private String message;

    public ResponseHeader() {
    }

    public ResponseHeader(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
