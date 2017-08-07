package com.ipx.ipx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 查看全屏的项目图片
 */
public class ProjectPicActivity extends AppCompatActivity implements HttpStatus {

    private View controlsView;
    private ImageView projectPicView;
    private boolean mVisible;
    //动画效果
    private static final int UI_ANIMATION_DELAY = 300;

    //请求图片数据的handler
    private Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Bitmap bitmap = (Bitmap) msg.obj;
            projectPicView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            projectPicView.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_pic);
        mVisible = true;
        controlsView = findViewById(R.id.fullscreen_content_controls);
        projectPicView = (ImageView) findViewById(R.id.project_pic);
        //从Intent中获取数据
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String projectPicsJSON = bundle.getString("projectPics");
        String projectImage = getProjectImage(JSON.parseArray(projectPicsJSON));
        BitmapUtil.getHttpBitmap(projectImage, imageHandler);

        //点击图片,显示隐藏
        projectPicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    //从项目图片列表中获取项目图片
    private String getProjectImage(JSONArray projectPics) {
        String projectImage = "";
        //没有项目图片数据,取第一张为封面
        if (projectPics.size() > 0 && projectPics.get(0) != null) {
            JSONObject projectPic = projectPics.getJSONObject(0);
            projectImage = projectPic.getString("resourceUrl");
        }
        return projectImage;
    }

    //点击,显示或者隐藏
    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }


    //隐藏
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        controlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    //显示
    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        projectPicView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Handler mHideHandler = new Handler();

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            controlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Runnable mHidePart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            projectPicView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
}
