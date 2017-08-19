package com.ipx.agent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ipx.agent.mvpview.MvpView;

/**
 * 登录界面
 *
 * @author joels on 2017/8/18
 **/
public class LoginActivity extends AppCompatActivity implements MvpView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
