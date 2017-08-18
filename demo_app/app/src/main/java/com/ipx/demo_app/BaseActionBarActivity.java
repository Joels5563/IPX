package com.ipx.demo_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * 带ToolBar的基类Activity
 */
public abstract class BaseActionBarActivity extends AppCompatActivity {
    //工具栏
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        setupToolBar();
        initWidgets();
        afterOnCreate();
    }

    /**
     * 创建完之后的行为,默认什么都不做
     */
    protected void afterOnCreate() {
    }

    /**
     * 初始化其他的视图,默认什么都不做
     */
    protected void initWidgets() {
    }

    protected void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取contentView的资源id
     */
    protected abstract int getContentViewResId();
}
