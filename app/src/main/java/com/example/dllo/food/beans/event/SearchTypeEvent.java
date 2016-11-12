package com.example.dllo.food.beans.event;

/**
 * Created by XiaoyuLu on 16/11/12.
 *
 * 用于EventBus 传值的 事件类
 * 传递字符串, 区分是哪种搜索(普通搜索和对比搜索)
 * 未实现
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
