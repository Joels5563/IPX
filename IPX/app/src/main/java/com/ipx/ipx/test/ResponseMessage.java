package com.ipx.ipx.test;

import com.ipx.ipx.test.ResponseHeader;

/**
 * Created by Joels.Zhou on 2016/12/24.
 * 控制器响应
 */
public class ResponseMessage<T> {
    /**
     * 响应消息头
     */
    private ResponseHeader header;
    /**
     * 响应消息体
     */
    private T data;

    /**
     * 默认构造器
     * 默认成功
     */
    public ResponseMessage() {
        header = new ResponseHeader("0000", "ok");
    }

    /**
     * 构造器
     *
     * @param code    响应码
     * @param message 响应消息
     */
    public ResponseMessage(String code, String message) {
        header = new ResponseHeader(code, message);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }
}

