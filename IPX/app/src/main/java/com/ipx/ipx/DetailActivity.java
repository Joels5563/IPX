package com.ipx.ipx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 展示项目详细信息的Activity
 */
public class DetailActivity extends AppCompatActivity implements HttpStatus {
    private static final String TAG = "DetailActivity";
    private TextView listTextView;
    private HttpApplication application;
    private ImageView imageView;
    private TextView projectNameView;
    private TextView projectAddressView;
    private JSONArray projectPics;

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
                    projectPics = projectInfo.getJSONArray("picList");
                    //从projectPics里面获取项目封面
                    String projectImage = getProjectImage();
                    if (null != projectImage && projectImage.trim().length() > 0) {
                        BitmapUtil.getHttpBitmap(projectImage, imageHandler);
                    }
                    projectNameView.setText(projectInfo.getString("title"));
                    projectAddressView.setText(projectInfo.getString("detailAddr"));
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

        private String getProjectImage() {
            String projectImage = "";
            //没有项目图片数据,取第一张为封面
            if (projectPics.size() > 0 && projectPics.get(0) != null) {
                JSONObject projectPic = projectPics.getJSONObject(0);
                projectImage = projectPic.getString("resourceUrl");
            }
            return projectImage;
        }
    };

    /**
     * 获取图片数据的handler
     */
    private Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Bitmap bitmap = (Bitmap) msg.obj;
            Bitmap projectImage = Bitmap.createScaledBitmap(bitmap,
                    100,
                    100,
                    false);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageBitmap(projectImage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击图片,获取图片满屏放大效果,并且可以滑动轮询图片
                    //跳转到新的Activity
                    Intent intent = new Intent();
                    //图片全屏查看
//                    intent.setClass(DetailActivity.this, ProjectPicActivity.class);
                    //图片全屏能够滑动查看所有
                    intent.setClass(DetailActivity.this, ProjectPicListActivity.class);
                    intent.putExtra("projectPics", projectPics.toJSONString());
                    DetailActivity.this.startActivity(intent); // 启动Activity
                }
            });
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        listTextView = (TextView) findViewById(R.id.token);
        //获取从ListActivity中传入的id信息
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Long projectId = bundle.getLong("projectId");

        //初始化页面的组件元素
        imageView = (ImageView) findViewById(R.id.project_image);
        projectAddressView = (TextView) findViewById(R.id.project_address);
        projectNameView = (TextView) findViewById(R.id.project_name);


        application = (HttpApplication) getApplication();

        //获取项目详情信息
        Request request = new Request.Builder()
                .url(Api.PROJECT_DETAIL + projectId)
                .header("authorization", "bearer " + application.getToken())
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
        listTextView.setText(message);
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT).show();
    }

}

