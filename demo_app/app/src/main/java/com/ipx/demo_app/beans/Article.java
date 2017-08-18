package com.ipx.demo_app.beans;

/**
 * 文章对象
 */
public class Article {
    //标题
    private String title;
    //图片
    private String pic;
    //文章id
    private String id;

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
