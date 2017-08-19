package com.ipx.demo_app.model.impl;

import com.ipx.demo_app.model.LoginModel;
import com.orhanobut.logger.Logger;

/**
 * 登录操作的实现
 *
 * @author joels on 2017/8/19
 **/
public class LoginModelImpl implements LoginModel {

    @Override
    public void login(String email, String password) {
        Logger.d("登录操作的参数为:email --%s,password--%s", email, password);

    }
}
