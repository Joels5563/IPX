package com.ipx.demo_app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipx.demo_app.adapters.ArticleAdapter;
import com.ipx.demo_app.beans.Article;
import com.ipx.demo_app.db.DataBaseHelper;
import com.ipx.demo_app.listeners.OnItemClickListener;
import com.ipx.demo_app.mvpview.ArticleListView;
import com.ipx.demo_app.presenter.ArticleListPresenter;
import com.ipx.demo_app.widgets.AutoLoadRecyclerView;

import java.util.List;

/**
 * 文章的Fragment
 */
public class ArticleListFragment extends Fragment implements ArticleListView,
        SwipeRefreshLayout.OnRefreshListener, AutoLoadRecyclerView.OnLoadListener {
    private static final String TAG = "ArticleListFragment";
    //文章的Adapter
    private ArticleAdapter articleAdapter;
    //下拉刷新组件
    private SwipeRefreshLayout swipeRefreshLayout;
    //文章RecyclerView
    private AutoLoadRecyclerView recyclerView;
    //文章列表view对象的交互中间人
    private ArticleListPresenter articleListPresenter =
            new ArticleListPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        initRefreshView(rootView);
        initAdapter();
        swipeRefreshLayout.setRefreshing(true);
        return rootView;
    }

    /**
     * 初始化文章adapter
     */
    private void initAdapter() {
        articleAdapter = new ArticleAdapter();
        articleAdapter.setOnItemClickListener(new OnItemClickListener<Article>() {
            @Override
            public void onClick(Article article) {
                if (article != null) {
                    //查看文章详细内容
                    jumpToDetailActivity(article);
                }
            }
        });
        recyclerView.setAdapter(articleAdapter);
    }

    /**
     * 查看文章详细内容
     *
     * @param article 文章信息
     */
    private void jumpToDetailActivity(Article article) {
        Log.i(TAG, "获取文章的详细信息" + article);
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("url", "http://news-at.zhihu.com/story/" + article.getId());
        startActivity(intent);
    }

    /**
     * 初始化文章RecyclerView
     *
     * @param rootView 根对象
     */
    private void initRefreshView(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.articles_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setOnLoadListener(this);
    }


    /**
     * 当单击手势触发刷新时调用
     */
    @Override
    public void onRefresh() {
        articleListPresenter.fetchLastestArticles();
    }

    @Override
    public void onLoad() {
        articleListPresenter.loadNextPageArticles();
    }


    @Override
    public void onResume() {
        super.onResume();
        //关联该View到presenter
        articleListPresenter.attach(this);
        //从数据库中加载缓存
        articleAdapter.addItems(DataBaseHelper.getInstance().loadArticles());
    }

    /**
     * 显示loading
     */
    @Override
    public void onShowLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 隐藏loading
     */
    @Override
    public void onHideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 将网络或者数据库中获取到的文章列表显示到RecycleView上
     *
     * @param result 文章列表
     */
    @Override
    public void onFetchedArticles(List<Article> result) {
        articleAdapter.addItems(result);
    }

    /**
     * 将缓存文章清除
     */
    @Override
    public void cleanCacheArticles() {
        articleAdapter.clear();
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        articleListPresenter.detach();
    }
}
