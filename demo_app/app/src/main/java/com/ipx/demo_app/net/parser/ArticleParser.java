package com.ipx.demo_app.net.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipx.demo_app.beans.Article;
import com.ipx.demo_app.net.ResponseParser;

import java.util.LinkedList;
import java.util.List;

/**
 * 将服务器返回的Json数据转为文章列表的解析器
 *
 * @author joels on 2017/8/17
 **/
public class ArticleParser implements ResponseParser<List<Article>> {
    @Override
    public List<Article> parseResponse(String result) {
        List<Article> articleList = new LinkedList<>();
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("stories");
            for (int i = 0, length = jsonArray.size(); i < length; i++) {
                JSONObject articleJson = jsonArray.getJSONObject(i);
                Article article = new Article();
                article.setId(articleJson.getString("id"));
                article.setTitle(articleJson.getString("title"));
                article.setPic(articleJson.getJSONArray("images").get(0).toString());
                articleList.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleList;
    }
}
