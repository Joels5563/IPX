package com.ipx.demo_app.widgets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 滚动到底部时自动加载的RecyclerView
 */
public class AutoLoadRecyclerView extends RecyclerView {
    /**
     * 后续剩余4项数据时触发自动加载
     */
    private static final int COUNT = 4;
    /**
     * 加载监听器
     */
    private OnLoadListener mLoadListener;
    /**
     * 是否正在加载
     */
    private boolean isLoading = false;
    /**
     * 是否检查延迟
     */
    private boolean isValidDelay = true;

    public AutoLoadRecyclerView(Context context) {
        super(context);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            return;
        }
        init();
    }

    /**
     * 初始化窗体
     */
    private void init() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                checkLoadMore(dx, dy);
            }
        });
    }

    /**
     * 检查是否还有更多
     *
     * @param dx 滚动横坐标位置
     * @param dy 滚动纵坐标位置
     */
    private void checkLoadMore(int dx, int dy) {
        if (isBottom(dx, dy) && !isLoading
                && isValidDelay
                && mLoadListener != null) {
            isValidDelay = false;
            mLoadListener.onLoad();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    isValidDelay = true;
                }
            }, 1000);
        }
    }

    /**
     * 检查是否到达底部,当到达倒数COUNT项且滚动纵轴大于0时,返回true
     *
     * @param dx 滚动横坐标位置
     * @param dy 滚动纵坐标位置
     * @return
     */
    @SuppressWarnings("unused")
    private boolean isBottom(int dx, int dy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        //最后可见item位置
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        //当前窗体中总item数量
        int totalItemCount = layoutManager.getItemCount();
        return lastVisibleItem >= totalItemCount - COUNT && dy > 0;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public void setOnLoadListener(OnLoadListener mLoadListener) {
        this.mLoadListener = mLoadListener;
    }

    public interface OnLoadListener {
        void onLoad();
    }
}
