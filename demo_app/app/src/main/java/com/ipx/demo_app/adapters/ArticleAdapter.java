package com.ipx.demo_app.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipx.demo_app.R;
import com.ipx.demo_app.beans.Article;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文章的Adapter
 */
public class ArticleAdapter extends RecyclerBaseAdapter<Article, ArticleAdapter.ArticleViewHolder> {
    /**
     * 创建文章ViewHolder
     *
     * @param viewGroup 父级视图容器
     * @param position  索引
     * @return
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
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                URL myFileUrl;
                InputStream is = null;
                try {
                    myFileUrl = new URL(item.getPic());
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setConnectTimeout(0);
                    conn.setDoInput(true);
                    conn.connect();
                    is = conn.getInputStream();
                    return BitmapFactory.decodeStream(is);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                articleViewHolder.picIv.setImageBitmap(bitmap);
            }
        }.execute();
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


