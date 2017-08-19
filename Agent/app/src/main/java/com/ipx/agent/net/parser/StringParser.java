package com.ipx.agent.net.parser;

import com.ipx.agent.net.ResponseParser;

import java.io.IOException;

import okhttp3.Response;

/**
 * 图片解析器
 *
 * @author joels on 2017/8/18
 **/
public class StringParser implements ResponseParser<String> {

    @Override
    public String parseResponse(Response response) {
        String result = "";
        try {
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
