package com.ipx.ipx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目图片列表滑动查看
 */
public class ProjectPicListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    //ViewPager
    private ViewPager viewPager;
    //装点点的ImageView数组
    private ImageView[] tips;
    //装ImageView数组
    private ImageView[] imageViews;

    private Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ProjectPic projectPic = (ProjectPic) msg.obj;
            imageViews[projectPic.getIndex()].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageViews[projectPic.getIndex()].setImageBitmap(projectPic.getProjectPic());
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_pic_list);

        ViewGroup picGroup = (ViewGroup) findViewById(R.id.pic_group);
        viewPager = (ViewPager) findViewById(R.id.project_pis);

        //载入图片资源
        //获取从DetailActivity中传入的图片信息
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String projectPicsJSON = bundle.getString("projectPics");
        //从projectPicsJSON获取projectPicURLs
        List<String> projectPicURLs = getProjectPicURLs(projectPicsJSON);
        int length = projectPicURLs.size();
        //将点点加入到ViewGroup中
        tips = new ImageView[length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            picGroup.addView(imageView, layoutParams);
        }

        //将图片装载到数组中
        imageViews = new ImageView[length];
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(this);
            imageViews[i] = imageView;
            BitmapUtil.getHttpBitmap(projectPicURLs.get(i), imageHandler, i, new ProjectPic());
        }
        //设置Adapter
        viewPager.setAdapter(new ProjectPicAdapter(length));
        //设置监听，主要是设置点点的背景
        viewPager.addOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((imageViews.length) * 100);

    }

    /**
     * 获取图片url
     *
     * @param projectPicsJSON 图片资源json
     * @return 图片url列表
     */
    private List<String> getProjectPicURLs(String projectPicsJSON) {
        List<String> projectPicURLs = new ArrayList<>();
        JSONArray projectPics = JSON.parseArray(projectPicsJSON);
        for (int i = 0, length = projectPics.size(); i < length; i++) {
            JSONObject projectPic = projectPics.getJSONObject(i);
            projectPicURLs.add(projectPic.getString("resourceUrl"));
        }
        return projectPicURLs;
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % imageViews.length);
    }


    /**
     * 设置选中的tip的背景
     *
     * @param selectItems 选中的图片
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ProjectPicAdapter extends PagerAdapter {
        //数据长度
        private int length;

        public ProjectPicAdapter(int length) {
            this.length = length;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            if(length > 1) {
                //可以无限轮播
                return Integer.MAX_VALUE;
            } else {
                //只一张,不让滑动
                return 1;
            }
        }

        /**
         * Determines whether a page View is associated with a specific key object
         * required for a PagerAdapter to function properly.
         *
         * @param view   Page View to check for association with <code>object</code>
         * @param object Object to check for association with <code>view</code>
         * @return true if <code>view</code> is associated with the key object <code>object</code>
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position % imageViews.length], 0);
            return imageViews[position % imageViews.length];
        }

        /**
         * Remove a page for the given position.  The adapter is responsible
         * for removing the view from its container, although it only must ensure
         * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
         *
         * @param container The containing View from which the page will be removed.
         * @param position  The page position to be removed.
         * @param object    The same object that was returned by
         *                  {@link #instantiateItem(View, int)}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

