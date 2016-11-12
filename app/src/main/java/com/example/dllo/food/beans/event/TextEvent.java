package com.example.dllo.food.beans.event;

/**
 * Created by XiaoyuLu on 16/11/11.
 *
 * 参考: Course_Small 项目
 * EventBus 第一步: 创建Event 数据类
 * 第二步, 发布事件, 在 SearchSearchFragment 中发布
 * 第二步, 注册订阅者, 在 SearchActivity 中注册
 *
 * 传值的方法: 广播, EventBus, Bundle 也可以?
 */
public class TextEvent {

    private String text;

    public TextEvent(String text) {
        this.text = text;
    }

    public TextEvent() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
