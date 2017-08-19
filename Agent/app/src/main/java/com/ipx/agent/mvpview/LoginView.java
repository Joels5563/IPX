package com.ipx.agent.mvpview;

/**
 * 登录视图角色
 *
 * @author joels on 2017/8/19
 **/
public interface LoginView extends MvpView {

    /**
     * 设置邮箱错误信息
     */
    void setupEmailErrorInfo();

    /**
     * 登录成功跳转
     */
    void setupSuccessInfo(String result);

    /**
     * 登录失败错误信息
     */
    void setupFailureInfo();

    /**
     * 设置请求过程中信息
     */
    void setupProgress(int newProgress);
}
