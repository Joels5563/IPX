package com.ipx.demo_app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipx.demo_app.adapters.MenuAdapter;
import com.ipx.demo_app.beans.MenuItem;
import com.ipx.demo_app.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面,管理3个Fragment,分别为文章类表,关于两个Fragment
 * 该界面由两个部分组成,分别是菜单视图和文章列表视图
 * 这两个视图通过DrawerLayout组织.
 * 菜单部分又分为上下两部分,即用户信息和菜单列表
 * 其中菜单列表通过RecyclerView实现
 * 文章列表则是由一个包含RecyclerView的Fragment组成
 */
public class MainActivity extends BaseActionBarActivity {
    private FragmentManager fragmentManager;
    //文章列表Fragment
    private Fragment articleListFragment = new ArticleListFragment();
    //菜单布局
    private DrawerLayout drawerLayout;

    /**
     * 初始化其他的视图,默认什么都不做
     */
    @Override
    protected void initWidgets() {
        fragmentManager = getFragmentManager();
        setupDrawerToggle();
        setupMenuRecyclerView();
        // 显示文章列表Fragment
        addFragment(articleListFragment);
    }

    private void addFragment(Fragment articleListFragment) {
        fragmentManager.beginTransaction()
                .add(R.id.articles_container, articleListFragment)
                .commitAllowingStateLoss();
    }

    private void setupMenuRecyclerView() {
        RecyclerView menuRecyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.home, getString(R.string.article)));
        //menuItems.add(new MenuItem(R.drawable.about, getString(R.string.about_menu)));
        menuItems.add(new MenuItem(R.drawable.exit, getString(R.string.exit)));
        MenuAdapter menuAdapter = new MenuAdapter();
        menuAdapter.addItems(menuItems);
        menuAdapter.setOnItemClickListener(new OnItemClickListener<MenuItem>() {
            @Override
            public void onClick(MenuItem item) {
                clickMenuItem(item);
            }
        });
        menuRecyclerView.setAdapter(menuAdapter);
    }

    private void setupDrawerToggle() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
    }

    /**
     * 获取contentView的资源id
     */
    @Override
    protected int getContentViewResId() {
        return R.layout.main_activity;
    }

    /**
     * 点击菜单项的处理函数
     *
     * @param item 菜单项
     */
    private void clickMenuItem(MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getIconResId()) {
            case R.drawable.home:
                fragmentManager.beginTransaction()
                        .replace(R.id.articles_container, articleListFragment).commit();
                break;
            case R.drawable.exit:
                isQuit();
                break;
            default:
                break;
        }
    }

    //退出系统
    private void isQuit() {
        new AlertDialog.Builder(this)
                .setTitle("确认退出?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", null).create().show();
    }
}
