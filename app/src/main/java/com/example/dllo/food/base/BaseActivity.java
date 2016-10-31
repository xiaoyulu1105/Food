package com.example.dllo.food.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by xiaoyulu on 16/10/21.
 * 抽象的 Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        initViews();
        initData();
    }

    // 获取布局文件
    protected abstract int getLayout();

    // 初始化各种 findViewById
    protected abstract void initViews();

    // 初始化数据, 基本上就是 拉取网络数据
    protected abstract void initData();

    // 简化 findViewById 省去强转的过程
    protected <T extends View>T bindView(int id) {
        return (T)findViewById(id);
    }

    // 方法重载, 指定在哪个 View 里 findViewById
    protected <T extends View> T bindView(View view, int id) {
        return (T)view.findViewById(id);
    }

    // 设置各种点击事件
    protected void setClick(View.OnClickListener onClickListener, View ... views){
        for (View view : views) {
            view.setOnClickListener(onClickListener);
        }
    }

}
