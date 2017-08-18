package com.ipx.demo_app.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipx.demo_app.R;
import com.ipx.demo_app.beans.Article;
import com.ipx.demo_app.net.DataListener;
import com.ipx.demo_app.net.HttpFlinger;
import com.ipx.demo_app.net.parser.BitmapParser;

/**
 * 文章的Adapter
 */
public class ArticleAdapter extends RecyclerBaseAdapter<Article, ArticleAdapter.ArticleViewHolder> {
    private static final String TAG = "ArticleAdapter";

    /**
     * 创建文章ViewHolder
     *
     * @param viewGroup 父级视图容器
     * @param position  索引
     * @return VH
     */
    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        //根据父级视图容器创建文章的ViewHolder
        View view = inflateItemView(viewGroup, R.layout.recyclerview_article_item);
        return new ArticleViewHolder(view);
    }

    /**
     * 将item绑定到ViewHolder
     *
     * @param articleViewHolder 文章的ViewHolder
     * @param item              文章信息
     */
    @Override
    protected void bindDataToItemView(final ArticleViewHolder articleViewHolder, final Article item) {
        articleViewHolder.titleTv.setText(item.getTitle());
        articleViewHolder.picIv.setImageBitmap(null);
        HttpFlinger.getInstance().get(item.getPic(), new BitmapParser(), new DataListener<Bitmap>() {
            @Override
            public void onComplete(Bitmap bitmap) {
                if (bitmap != null) {
                    articleViewHolder.picIv.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure() {
                Log.e(TAG, "获取图片失败");
            }
        });
        articleViewHolder.itemView.setTag(item);
    }

    /**
     * 文章列表中文章item的ViewHolder
     */
    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        ImageView picIv;

        ArticleViewHolder(View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.article_title_tv);
            picIv = itemView.findViewById(R.id.article_pic_iv);
        }
    }
}


