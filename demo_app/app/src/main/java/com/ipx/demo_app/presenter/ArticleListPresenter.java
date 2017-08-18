package com.ipx.demo_app.presenter;

import android.util.Log;

import com.ipx.demo_app.beans.Article;
import com.ipx.demo_app.db.DataBaseHelper;
import com.ipx.demo_app.mvpview.ArticleListView;
import com.ipx.demo_app.net.DataListener;
import com.ipx.demo_app.net.HttpFlinger;
import com.ipx.demo_app.net.parser.ArticleParser;

import java.util.List;

/**
 * 文章列表的presenter,负责从网络上加载最新的文章列表.<br/>
 * <p>
 * 第一次加载最新文章列表时,先从数据库中加载缓存,然后再从网络上加载最新的数据.
 *
 * @author joels on 2017/8/17
 **/
public class ArticleListPresenter extends BasePresenter<ArticleListView> {
    private static final String TAG = "ArticleListPresenter";
    //第一页数据,代表最新的数据
    public static final int FIRST_PAGE = 1;
    //索引,用于下拉下一页数据
    private int pageIndex = FIRST_PAGE;
    private boolean isCacheLoaded = false;

    /**
     * 第一次先从数据库中加载缓存,然后再从网络上获取数据
     */
    public void fetchLastestArticles() {
        if (!isCacheLoaded) {
            mvpView.onFetchedArticles(DataBaseHelper.getInstance().loadArticles());
        }
        //从网络上获取数据
        fetchArticlesAsync(FIRST_PAGE);
    }

    /**
     * 从网络上获取数据
     */
    private void fetchArticlesAsync(final int page) {
        mvpView.onShowLoading();
        String requestUrl = "https://news-at.zhihu.com/api/4/news/latest";
        Log.i(TAG, "请求第" + page + "页数据:" + requestUrl);
        HttpFlinger.get(requestUrl, new ArticleParser(), new DataListener<List<Article>>() {
            @Override
            public void onComplete(List<Article> result) {
                mvpView.onHideLoading();
                if (!isCacheLoaded && result != null) {
                    //第一次从网络加载数据,则需要清空数据库的旧缓存
                    mvpView.cleanCacheArticles();
                    isCacheLoaded = true;
                }

                if (result != null) {
                    //将文章列表回调给view角色
                    mvpView.onFetchedArticles(result);
                    //存储到数据库
                    DataBaseHelper.getInstance().saveArticles(result);
                    updatePageIndex(page, result);
                }

            }

            @Override
            public void onFailure() {
                Log.e(TAG, "获取文章列表失败");
            }
        });
    }

    /**
     * 更新下一页的索引,当请求成功且不是第一次请求最新数据时更新索引值。
     *
     * @param curPage 当前页
     * @param result  结果集
     */
    public void updatePageIndex(int curPage, List<Article> result) {
        if (result.size() > 0
                && shouldUpdatePageIndex(curPage)) {
            pageIndex++;
        }
    }

    /**
     * 是否应该更新Page索引。更新索引值的时机有两个，一个是首次成功加载最新数据时mPageIndex需要更新;另一个是每次加载更多数据时需要更新.
     *
     * @param curPage 当前页
     */
    private boolean shouldUpdatePageIndex(int curPage) {
        return (pageIndex > 1 && curPage > 1)
                || (curPage == 1 && pageIndex == 1);
    }

    /**
     * 加载下一页
     */
    public void loadNextPageArticles() {
        fetchArticlesAsync(pageIndex);
    }

}
