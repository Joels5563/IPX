package com.ipx.ipx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 获取bitmap的工具类
 * 由于不能在主线程运行httpClient,需要另开线程,通过handler发送和接收消息回调
 */
public class BitmapUtil implements HttpStatus {
    private static final String TAG = "BitmapUtil";

    private BitmapUtil() {
        throw new AssertionError("禁止通过私有构造器创建对象");
    }

    /**
     * 从服务器获取图片
     *
     * @param url     路径
     * @param handler 获取图片后回调handler
     */
    public static void getHttpBitmap(final String url, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                URL myFileUrl = null;
                Bitmap bitmap = null;
                try {
                    Log.d(TAG, url);
                    myFileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setConnectTimeout(0);
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    Message message = new Message();
                    message.what = SUCCESS;
                    message.obj = bitmap;
                    handler.sendMessage(message);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 从服务器获取图片
     *
     * @param url 路径
     * @return 获取图片信息
     */
    public static Bitmap getHttpBitmap(final String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            Log.d(TAG, url);
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            Message message = new Message();
            message.what = SUCCESS;
            message.obj = bitmap;
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从服务器获取图片
     *
     * @param url        路径
     * @param handler    获取图片后回调handler
     * @param projectPic 消息中传入的数据
     * @param index      顺序索引
     */
    public static void getHttpBitmap(final String url, final Handler handler,
                                     final int index, final ProjectPic projectPic) {
        new Thread() {
            @Override
            public void run() {
                URL myFileUrl = null;
                Bitmap bitmap = null;
                try {
                    Log.d(TAG, url);
                    myFileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setConnectTimeout(0);
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    Message message = new Message();
                    message.what = SUCCESS;
                    projectPic.setIndex(index);
                    projectPic.setProjectPic(bitmap);
                    message.obj = projectPic;
                    handler.sendMessage(message);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
