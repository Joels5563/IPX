package com.ipx.demo_app.mvpview;

import com.ipx.demo_app.beans.Article;

import java.util.List;

/**
 * 定义文章的功能接口,文章view
 *
 * @author joels on 2017/8/17
 **/
public interface ArticleListView extends MvpView {
    /**
     * 将网络或者数据库中获取到的文章列表显示到RecycleView上
     *
     * @param result 文章列表
     */
    void onFetchedArticles(List<Article> result);

    /**
     * 将缓存文章清除
     */
    void cleanCacheArticles();
}
