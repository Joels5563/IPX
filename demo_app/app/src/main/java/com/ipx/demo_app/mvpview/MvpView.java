package com.ipx.demo_app.mvpview;

/**
 * View角色的抽象定义
 *
 * @author joels on 2017/8/17
 **/
public interface MvpView {

    /**
     * 显示loading
     */
    void onShowLoading();

    /**
     * 隐藏loading
     */
    void onHideLoading();
}
