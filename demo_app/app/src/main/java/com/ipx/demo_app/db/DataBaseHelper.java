package com.ipx.demo_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ipx.demo_app.beans.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    //文章表
    private static final String TABLE_ARTICLES = "articles";
    private static final String CREATE_ARTICLES_TABLE_SQL = "CREATE TABLE articles ( " +
            "id INTEGER PRIMARY KEY UNIQUE, " +
            "title VARCHAR(100) NOT NULL, " +
            "pic VARCHAR(100) " +
            ")";
    private static final String DB_NAME = "demo_app.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase database;
    private static DataBaseHelper dataBaseHelper;

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
    }

    public static void init(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context);
        }
    }

    public static DataBaseHelper getInstance() {
        if (dataBaseHelper == null) {
            throw new NullPointerException("dataBaseHelper is null, please call init method first.");
        }
        return dataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL(CREATE_ARTICLES_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //版本升级
        db.execSQL("DROP TABLE " + TABLE_ARTICLES);
        onCreate(db);
    }

    public void saveArticles(List<Article> articles) {
        //将文章列表保存到数据库中
        for (Article article : articles) {
            database.insertWithOnConflict(
                    TABLE_ARTICLES, null,
                    article2ContentValues(article),
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
    }


    private ContentValues article2ContentValues(Article article) {
        //将文章转换为数据库实体
        ContentValues newValues = new ContentValues();
        newValues.put("id", article.getId());
        newValues.put("title", article.getTitle());
        newValues.put("pic", article.getPic());
        return newValues;
    }

    public List<Article> loadArticles() {
        //从数据库中加载文章列表
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_ARTICLES, null);
        List<Article> articles = parseArticles(cursor);
        cursor.close();
        return articles;
    }

    private List<Article> parseArticles(Cursor cursor) {
        //从Cursor中解析文章列表
        List<Article> articles = new ArrayList<>();
        while (cursor.moveToNext()) {
            Article item = new Article();
            item.setId(cursor.getString(0));
            item.setTitle(cursor.getString(1));
            item.setPic(cursor.getString(2));
            articles.add(item);
        }
        return articles;
    }

}
