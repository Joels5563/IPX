package com.ipx.agent.mvpview;

/**
 * View角色的抽象定义
 *
 * @author joels on 2017/8/19
 **/
public interface MvpView {
    /**
     * 显示进度
     */
    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();
}
