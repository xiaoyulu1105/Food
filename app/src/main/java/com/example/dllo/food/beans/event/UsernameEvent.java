package com.example.dllo.food.beans.event;

/**
 * Created by XiaoyuLu on 16/11/15.
 *
 * 存放用户名, 当登录成功时, 就发送该事件,
 * 用于显示用户名 和 收藏时将 用户名 和 收藏数据绑定
 */
public class UsernameEvent {
    private String username;

    public UsernameEvent(String username) {
        this.username = username;
    }

    public UsernameEvent() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
