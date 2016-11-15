package com.example.dllo.food.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by XiaoyuLu on 16/11/15.
 *
 * 用于存放用户信息
 */
class MyBmobUser extends BmobUser {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
