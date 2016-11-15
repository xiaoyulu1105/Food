package com.example.dllo.food.base;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by XiaoyuLu on 16/10/27.
 *
 * !!!!! 写完 MyApp 一定要注册
 * android:name=".tools.MyApp"
 *
 * 跟界面无关的 context 可以用这个
 * 但是, 与界面相关, 如 Inflate()就不可以
 */
public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this; // 因为本类对象Application 就是Context的子类


        //第一：默认初始化 BmobSDK
        // 建议初始化放到 Application 中
        // 第二个参数是 Application 的ID, 在bmob上查看
        Bmob.initialize(this, "e606fa350c3ca13a7a3a17c25cba7605");
    }

    public static Context getContext(){
        return context;
    }
}
