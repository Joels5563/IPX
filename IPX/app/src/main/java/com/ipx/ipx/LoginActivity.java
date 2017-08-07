package com.ipx.ipx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements HttpStatus{
    private static final String TAG = "LoginActivity";

    // UI references.
    private EditText mAccountView;
    private EditText mPasswordView;
    private TextView mTextView;
    private HttpApplication application;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = (String) msg.obj;
            switch (msg.what) {
                case SUCCESS:
                    mTextView.setText("");
                    //将登陆token放入HttpApplication
                    JSONObject jsonObject = JSON.parseObject(response);
                    String token = jsonObject.getJSONObject("data").getString("token");
                    application.setToken(token);
                    Intent intent = new Intent();
                    //第一个参数指的就是要跳转的那个Activity
                    //第二个指的是跳到的那个Activity
                    intent.setClass(LoginActivity.this, ListActivity.class);
                    //传递返回的json数据到跳转后的Activity
//                    intent.putExtra("token", token);
                    //主要是获取通讯录的内容
                    startActivity(intent); // 启动Activity
                    break;
                case FAILURE:
                    JSONObject jsonObject1 = JSON.parseObject(response);
                    mTextView.setText(jsonObject1.getJSONObject("header").getString("message"));
                    break;
                default:
                    mTextView.setText(response);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        application = (HttpApplication) getApplication();

        // Set up the login form.
        mAccountView = (EditText) findViewById(R.id.account);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mTextView = (TextView) findViewById(R.id.textView);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String account = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(account)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //请求接口
            RequestBody formBody = new FormBody.Builder()
                    .add("account", account)
                    .add("password", password)
                    .add("language", "en-US")
                    .build();
            Request request = new Request.Builder()
                    .url(Api.LOGIN)
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
    }

}

