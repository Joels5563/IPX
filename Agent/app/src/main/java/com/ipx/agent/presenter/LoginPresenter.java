package com.ipx.agent.presenter;

import com.ipx.agent.model.LoginModel;
import com.ipx.agent.model.impl.LoginModelImpl;
import com.ipx.agent.mvpview.LoginView;
import com.ipx.agent.net.DataListener;

/**
 * 登录中间交互人角色
 *
 * @author joels on 2017/8/19
 **/
public class LoginPresenter extends BasePresenter<LoginView> implements DataListener<String> {
    private LoginModel loginModel = new LoginModelImpl();


    /**
     * 登录操作<br/>
     * 1.先验证邮箱是否合法<br/>
     * 2.登录
     *
     * @param email    邮箱
     * @param password 密码
     * @param language 语言
     */
    public void login(String email, String password, String language) {
        if (mvpView != null) {
            mvpView.showProgress();
        }

        if (!loginModel.validateEmail(email) && mvpView != null) {
            mvpView.setupEmailErrorInfo();
        } else {
            loginModel.login(email, password, language, this);
        }
        mvpView.hideProgress();
    }

    /**
     * 完成时回调方法
     *
     * @param result 返回结果
     */
    @Override
    public void onComplete(String result) {
        if (mvpView != null) {
            mvpView.setupSuccessInfo(result);
        }
    }

    /**
     * 失败时回调方法
     */
    @Override
    public void onFailure() {
        if (mvpView != null) {
            mvpView.setupFailureInfo();
        }
    }

    /**
     * 请求过程中调用
     */
    @Override
    public void onProgress(int newProgress) {
        if (mvpView != null) {
            mvpView.setupProgress(newProgress);
        }
    }
}
