package com.ipx.demo_app.listeners;

/**
 * item的点击事件
 */
public interface OnItemClickListener<T> {
    //点击item
    void onClick(T item);
}
