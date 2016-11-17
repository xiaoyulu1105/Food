package com.example.dllo.food.dbtools;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by XiaoyuLu on 16/11/11.
 *
 * 数据库类, 存储搜索的历史记录
 */
public class HistorySqlData {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    public HistorySqlData(String historyStr, long historyTime) {
        this.historyStr = historyStr;
        this.historyTime = historyTime;
    }

    public HistorySqlData() {
    }

    private String historyStr;
    private long historyTime;

    public String getHistoryStr() {
        return historyStr;
    }

    public void setHistoryStr(String historyStr) {
        this.historyStr = historyStr;
    }

    public long getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(long historyTime) {
        this.historyTime = historyTime;
    }
}
