package com.ipx.demo_app.model;

/**
 * 登陆的操作的接口
 *
 * @author joels on 2017/8/19
 **/
public interface LoginModel {
    /**
     * 登录
     *
     * @param email    邮箱
     * @param password 密码
     */
    void login(String email, String password);
}
