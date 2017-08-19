package com.ipx.agent.model.impl;


import com.ipx.agent.constants.Api;
import com.ipx.agent.constants.RegexConstants;
import com.ipx.agent.model.LoginModel;
import com.ipx.agent.net.DataListener;
import com.ipx.agent.net.HttpFlinger;
import com.ipx.agent.net.parser.StringParser;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登录操作的实现
 *
 * @author joels on 2017/8/19
 **/
public class LoginModelImpl implements LoginModel {


    /**
     * 验证邮箱输入格式 <br/>
     * 若验证不通过,返回false
     *
     * @param email 输入的邮箱
     * @return 验证是否通过
     */
    @Override
    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(RegexConstants.REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        boolean isEmail = matcher.matches();
        Logger.d("验证邮箱--{%s}是否正确--{%b}", email, isEmail);
        return isEmail;
    }

    /**
     * 登录
     *
     * @param email        邮箱
     * @param password     密码
     * @param dataListener 请求返回响应
     */
    @Override
    public void login(String email, String password, String language, DataListener<String> dataListener) {
        Logger.d("登录操作的参数为:email --{%s},password--{%s}", email, password);
        //模拟登录成功
        Logger.e("登录成功");
        Map<String, String> params = new HashMap<>();
        params.put("account", email);
        params.put("password", password);
        params.put("language", language);
        HttpFlinger.getInstance()
                .post(Api.LOGIN, params,
                        new StringParser(), dataListener);
    }
}
