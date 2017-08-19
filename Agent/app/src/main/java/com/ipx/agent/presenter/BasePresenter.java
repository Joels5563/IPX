package com.ipx.agent.presenter;


import com.ipx.agent.mvpview.MvpView;

/**
 * view角色与model的交互中间人
 * <p>
 * 业务逻辑包含在该角色中,presenter需要持有view对象<br/>
 * 而我们的view对象往往是activity,fragment<br/>
 * 当activity退出时,presenter如果正在执行一个耗时的网络请求<br/>
 * 将导致activity的内存无法释放而造成内存泄漏<br/>
 * <p>
 * 因此,需要定义一个含有关联,取消关联view角色的presenter基类
 *
 * @param <T> T就是一个具体的MVP的view,每个presenter都需要与View打交道
 * @author joels on 2017/8/17
 **/
public abstract class BasePresenter<T extends MvpView> {
    protected T mvpView;

    /**
     * presenter关联view
     *
     * @param view view对象
     */
    public void attach(T view) {
        mvpView = view;
    }

    /**
     * presenter取消关联view
     */
    public void detach() {
        mvpView = null;
    }
}
