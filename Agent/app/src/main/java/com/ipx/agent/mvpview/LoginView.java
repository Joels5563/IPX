package com.ipx.agent.mvpview;

/**
 * 登录视图角色
 *
 * @author joels on 2017/8/19
 **/
public interface LoginView extends MvpView {

    /**
     * 验证邮箱输入格式 <br/>
     * 若验证不通过,返回false
     * @param email 输入的邮箱
     * @return 验证是否通过
     */
    boolean validateEmail(String email);

}
