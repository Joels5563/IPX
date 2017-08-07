package com.ipx.ipx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 登录成功后,展示列表信息的Activity
 */
public class ListActivity extends AppCompatActivity implements HttpStatus {
    private static final String TAG = "ListActivity";
    private HttpApplication application;
    private ListView projectListView;

    /**
     * 请求后台数据的handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = (String) msg.obj;
            switch (msg.what) {
                case SUCCESS:
                    JSONObject projectInfo = JSON.parseObject(response).getJSONObject("data");
                    ProjectAdapter projectAdapter = new ProjectAdapter(ListActivity.this,
                            projectInfo.getJSONObject("page").getJSONArray("list"));
                    projectListView.setAdapter(projectAdapter);
                    /*为ListView添加点击事件*/
                    projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            //跳转到详情页面
                            ListView listView = (ListView)parent;
                            @SuppressWarnings("unchecked")
                            Map<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                            Intent intent = new Intent();
                            //第一个参数指的就是要跳转的那个Activity
                            //第二个指的是跳到的那个Activity
                            intent.setClass(ListActivity.this, DetailActivity.class);
                            //传递返回的projectId到跳转后的详情页面
                            intent.putExtra("projectId", Long.parseLong(map.get("projectId").toString()));
                            //主要是获取通讯录的内容
                            ListActivity.this.startActivity(intent); // 启动Activity

                        }
                    });
                    break;
                case FAILURE:
                    JSONObject jsonObject1 = JSON.parseObject(response);
                    showToast(jsonObject1.getJSONObject("header").getString("message"));
                    break;
                default:
                    showToast(response);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //初始化页面的组件元素
        projectListView = (ListView) findViewById(R.id.project_list);

        application = (HttpApplication) getApplication();

        //获取项目详情信息
        RequestBody formBody = new FormBody.Builder()
                .add("type", "1")
                .build();
        Request request = new Request.Builder()
                .url(Api.PROJECT_LIST)
                .header("authorization", "bearer " + application.getToken())
                .post(formBody)
                .build();
        Call call = application.getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "请求后台失败");
                //在子线程中将message对象发出去
                Message message = new Message();
                message.what = SYSTEM_ERROR;
                message.obj = "System Error";
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //在子线程中将message对象发出去
                String responseJson = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseJson);
                Message message = new Message();
                String code = jsonObject.getJSONObject("header").getString("code");
                if ("0000".equals(code)) {
                    message.what = SUCCESS;
                } else {
                    message.what = FAILURE;
                }
                message.obj = responseJson;
                handler.sendMessage(message);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT).show();
    }

}
