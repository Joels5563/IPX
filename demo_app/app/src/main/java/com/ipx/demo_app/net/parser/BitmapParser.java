package com.ipx.demo_app.net.parser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ipx.demo_app.net.ResponseParser;

import java.io.IOException;

import okhttp3.Response;

/**
 * 图片解析器
 *
 * @author joels on 2017/8/18
 **/
public class BitmapParser implements ResponseParser<Bitmap> {

    @Override
    public Bitmap parseResponse(Response response) {
        byte[] data = new byte[0];
        try {
            data = response.body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
