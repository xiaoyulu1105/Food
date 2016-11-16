package com.example.dllo.food.sqltools;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by XiaoyuLu on 16/11/14.
 *
 * 收藏 的数据类
 */
public class CollectionSqlData {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private String username;
    private String title;
    private String link;

    public CollectionSqlData(String username, String title, String link) {
        this.username = username;
        this.title = title;
        this.link = link;
    }

    public CollectionSqlData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
