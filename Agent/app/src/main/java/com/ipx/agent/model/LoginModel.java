package com.ipx.agent.model;

import com.ipx.agent.net.DataListener;

/**
 * 登陆的操作的接口
 *
 * @author joels on 2017/8/19
 **/
public interface LoginModel {
    /**
     * 验证邮箱输入格式 <br/>
     * 若验证不通过,返回false
     *
     * @param email 输入的邮箱
     * @return 验证是否通过
     */
    boolean validateEmail(String email);

    /**
     * 登录
     *
     * @param email        邮箱
     * @param password     密码
     * @param language     系统语言
     * @param dataListener 请求返回响应
     */
    void login(String email, String password, String language, DataListener<String> dataListener);
}
