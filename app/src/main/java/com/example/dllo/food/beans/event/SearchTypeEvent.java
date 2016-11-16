package com.example.dllo.food.beans.event;

/**
 * Created by XiaoyuLu on 16/11/12.
 *
 * 用于EventBus 传值的 事件类
 * 传递字符串, 区分是哪种搜索(普通搜索和对比搜索)
 *
 * 如: 在 食物百科点击搜索 大Button 时发布该事件, 且发送方式是 黏性发送
 * SearchSearchFragment 中接收, 点击搜索历史 和大家都在搜后 才能判断是跳转到 SearchSimpleFragment 还是 SearchCompareFragment
 * SearchActivity 中接收 是通过 Intent 传递实现的, 点击搜索图标后 也需要判断跳到哪个Fragment
 */
public class SearchTypeEvent {

    private String searchType;

    public SearchTypeEvent(String searchType) {
        this.searchType = searchType;
    }

    public SearchTypeEvent() {
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
