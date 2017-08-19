package com.ipx.agent.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络执行引擎,并引入OkHttp和FastJson
 */
public class HttpFlinger {
    private static final String TAG = "HttpFlinger";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 采用单例模式加载
     */
    private HttpFlinger() {

    }

    private static final class HttpFlingerHolder {
        private static final HttpFlinger instance = new HttpFlinger();
    }

    public static HttpFlinger getInstance() {
        return HttpFlingerHolder.instance;
    }


    /**
     * http的get请求
     *
     * @param requestUrl 请求url
     * @param parser     响应数据解析器
     * @param listener   响应回调
     * @param <T>        解析对象
     */
    public <T> void get(final String requestUrl,
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
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final T result = parser.parseResponse(response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete(result);
                    }
                });
            }
        });
    }

    /**
     * http的post请求
     *
     * @param requestUrl 请求url
     * @param params     请求参数
     * @param parser     响应数据解析器
     * @param listener   响应回调
     * @param <T>        解析对象
     */
    public <T> void post(final String requestUrl,
                         final Map<String, String> params,
                         final ResponseParser<T> parser,
                         final DataListener<T> listener) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.add(param.getKey(), param.getValue());
        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "请求数据失败");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final T result = parser.parseResponse(response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete(result);
                    }
                });
            }

        });
    }
}
