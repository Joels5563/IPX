package com.ipx.agent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ipx.agent.mvpview.LoginView;
import com.ipx.agent.presenter.LoginPresenter;

import java.util.Locale;

/**
 * 登录界面
 *
 * @author joels on 2017/8/18
 **/
public class LoginActivity extends AppCompatActivity implements LoginView {
    private LoginPresenter loginPresenter =
            new LoginPresenter();
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private TextView errorInfo;
    private View focusView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        errorInfo = (TextView) findViewById(R.id.login_error_info);
        final String language = getLanguage();
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setError(null);
                loginPresenter.login(email.getText().toString(),
                        password.getText().toString(), language);
            }
        });

        loginPresenter.attach(this);
    }

    /**
     * 获取当前系统的语言环境
     *
     * @return 语言
     */
    private String getLanguage() {
        Locale curLocale = getResources().getConfiguration().locale;
        //通过Locale的equals方法，判断出当前语言环境
        if (curLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
            //中文
            return "zh_CN";
        } else {
            //英文
            return "en_US";
        }
    }

    /**
     * 显示进度
     */
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏进度
     */
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 设置邮箱错误信息
     */
    @Override
    public void setupEmailErrorInfo() {
        email.setError(getString(R.string.email_error_info));
        focusView = email;
        focusView.requestFocus();
    }

    /**
     * 登录成功跳转
     */
    @Override
    public void setupSuccessInfo(String result) {
        errorInfo.setText(result);
    }

    /**
     * 登录失败错误信息
     */
    @Override
    public void setupFailureInfo() {
        errorInfo.setText(R.string.login_error_info);
    }

    /**
     * 设置请求过程中信息
     */
    @Override
    public void setupProgress(int newProgress) {
        progressBar.setProgress(newProgress);
        if (newProgress == 100) {
            //加载完成,隐藏进度条
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detach();
    }
}
