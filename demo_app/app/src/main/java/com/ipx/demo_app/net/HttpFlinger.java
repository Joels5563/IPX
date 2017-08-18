package com.ipx.demo_app.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络执行引擎,并引入OkHttp和FastJson
 */
public class HttpFlinger {
    private static final String TAG = "HttpFlinger";
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    private HttpFlinger() {
        throw new AssertionError("HttpFlinger 私有构造器,禁止访问!");
    }

    /**
     * http的get请求
     *
     * @param requestUrl 请求url
     * @param parser     响应数据解析器
     * @param listener   响应回调
     * @param <T>        解析对象
     */
    public static <T> void get(final String requestUrl,
                               final ResponseParser<T> parser,
                               final DataListener<T> listener) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "请求数据失败");
                listener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                T result = parser.parseResponse(responseJson);
                listener.onComplete(result);
            }
        });
    }
}
