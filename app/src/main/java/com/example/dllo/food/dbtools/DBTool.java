package com.example.dllo.food.dbtools;

import android.os.Handler;
import android.util.Log;

import com.example.dllo.food.values.DBValues;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by XiaoyuLu on 16/10/29.
 * <p/>
 * 区分 Class<T> 与 T 的关系与区别
 */
public class DBTool {

    private SingletonUtils singletonUtils;
    private ThreadPoolExecutor threadPoolExecutor;
    private LiteOrm liteOrm;
    private Handler handler;

    public DBTool() {
        singletonUtils = SingletonUtils.getInstance(DBValues.DB_NAME);
        threadPoolExecutor = singletonUtils.getThreadPoolExecutor();
        liteOrm = singletonUtils.getLiteOrm();
        handler = singletonUtils.getHandler();
    }

    /**
     * 插入数据库的 泛型 方法
     */
    public <T> void insert(T t) {
        threadPoolExecutor.execute(new InsertRunnable(t));
    }

    private class InsertRunnable<T> implements Runnable {
        private T t;

        public InsertRunnable(T t) {
            this.t = t;
        }

        @Override
        public void run() {
            liteOrm.insert(t);
        }
    }

    /**
     * 自定义方法, 实现去重后插入数据
     */
    public void insertHistory(final String history) {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                queryAllData(HistorySqlData.class, new OnQueryListener<HistorySqlData>() {
                    @Override
                    public void onQuery(ArrayList<HistorySqlData> historySqlData) {

                        // 1. 当数据库中存在时, 将数据库中的数据删除
                        for (int i = 0; i < historySqlData.size(); i++) {
                            String string = historySqlData.get(i).getHistoryStr();
                            if (string.equals(history)) {
                                liteOrm.delete(new WhereBuilder(HistorySqlData.class)
                                        .where("historyStr = ?", history));
                            }
                        }
                        // 2. 当数据库数据已经存满 10条数据,删除最旧一条
                        if (historySqlData.size() >= 10) {
                            String oldText = historySqlData.get(0).getHistoryStr();
                            liteOrm.delete(new WhereBuilder(HistorySqlData.class)
                                    .where("historyStr = ?", oldText));
                        }
                        // 3. 将数据 存入数据库
                        HistorySqlData historySqlData1 = new HistorySqlData();
                        long currentTime = System.currentTimeMillis();
                        historySqlData1.setHistoryTime(currentTime);
                        historySqlData1.setHistoryStr(history);

                        liteOrm.insert(historySqlData1);
                    }
                });
            }
        });
    }

    /**
     * 删除 数据库所有数据 泛型方法实现
     */
    public <T> void deleteAllData(Class<T> tClass) {
        threadPoolExecutor.execute(new DeleteAllDataRunnable(tClass));
    }

    private class DeleteAllDataRunnable<T> implements Runnable {
        private Class<T> tClass;

        public DeleteAllDataRunnable(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public void run() {
            liteOrm.delete(tClass);
        }
    }

    /**
     * 按条件删除 HistorySqlData 数据库的数据
     *
     * @param historySqlDataClass
     * @param text
     */
    public void deleteHistoryByCondition(Class<HistorySqlData> historySqlDataClass, String text) {
        threadPoolExecutor.execute(new DeleteHistoryDataByCondRunnable(historySqlDataClass, text));
    }

    private class DeleteHistoryDataByCondRunnable implements Runnable {
        private Class<HistorySqlData> historySqlDataClass;
        private String text;

        public DeleteHistoryDataByCondRunnable(
                Class<HistorySqlData> historySqlDataClass, String text) {
            this.historySqlDataClass = historySqlDataClass;
            this.text = text;
        }

        @Override
        public void run() {
            Log.d("DeleteHistoryDataByCond", "liteOrm:" + liteOrm);
            liteOrm.delete(new WhereBuilder(historySqlDataClass)
                    .where("historyStr = ?", text));
        }
    }

    /**
     * 按条件 删除某一条 收藏数据
     */
    public void deleteCollectionByCondition(Class<CollectionSqlData> collectionSqlDataClass, String username, String getLink) {
        threadPoolExecutor.execute(new DeleteCollectionDataByCondRunnable(collectionSqlDataClass, username, getLink));
    }

    // 内部类: 按条件 删除某一条 收藏数据
    private class DeleteCollectionDataByCondRunnable implements Runnable {
        private Class<CollectionSqlData> collectionSqlDataClass;
        private String username;
        private String link;

        public DeleteCollectionDataByCondRunnable(Class<CollectionSqlData> collectionSqlDataClass, String username, String getLink) {
            this.collectionSqlDataClass = collectionSqlDataClass;
            this.username = username;
            this.link = getLink;
        }

        @Override
        public void run() {
            liteOrm.delete(new WhereBuilder(collectionSqlDataClass).where("username = ? and link = ?", username, link));
        }
    }

    // 查询数据库的 泛型 方法

    /**
     * 使用接口回调将数据返回到 主线程, 所以返回值不需要有, 也不应该有 !!!
     */
    public <T> void queryAllData(Class<T> tClass, OnQueryListener<T> onQueryListener) {

        threadPoolExecutor.execute(new QueryRunnable<>(tClass, onQueryListener));
    }


    /**
     * 实现 查询数据库的  外层 Runnable 泛型 类
     */
    private class QueryRunnable<T> implements Runnable {

        private Class<T> tClass;
        private OnQueryListener onQueryListener;

        public QueryRunnable(Class<T> tClass, OnQueryListener onQueryListener) {
            this.tClass = tClass;
            this.onQueryListener = onQueryListener;
        }

        @Override
        public void run() {
            ArrayList<T> tArrayList = liteOrm.query(tClass);
            handler.post(new CallbackRunnable<>(onQueryListener, tArrayList));
        }
    }

    /**
     * 实现用 Handler将线程从子线程切换到主线程, 用接口对象将数据存入接口
     */
    private class CallbackRunnable<T> implements Runnable {

        private OnQueryListener onQueryListener;
        private ArrayList<T> tArrayList;

        public CallbackRunnable(OnQueryListener onQueryListener, ArrayList<T> tArrayList) {
            this.onQueryListener = onQueryListener;
            this.tArrayList = tArrayList;
        }

        @Override
        public void run() {
            onQueryListener.onQuery(tArrayList);
        }
    }

    // 自定义方法, 实现按用户名查询

    /**
     * 1.1 使用接口回调将数据返回到 主线程, 所以返回值不需要有, 也不应该有 !!!
     */
    public void queryCollectionDataByUsername(String username, OnQueryListener<CollectionSqlData> onQueryListener) {

        threadPoolExecutor.execute(new QueryCollectionRunnable(username, onQueryListener));
    }

    /**
     * 1.2 按条件 查询 收藏 数据库的  外层 Runnable  类
     */
    private class QueryCollectionRunnable implements Runnable {

        private String username;
        private OnQueryListener onQueryListener;

        public QueryCollectionRunnable(String username, OnQueryListener onQueryListener) {
            this.username = username;
            this.onQueryListener = onQueryListener;
        }

        @Override
        public void run() {
            ArrayList<CollectionSqlData> tArrayList = liteOrm.query(
                    new QueryBuilder<>(CollectionSqlData.class).where("username = ?", username));
            handler.post(new CallbackCollectionRunnable(onQueryListener, tArrayList));
        }
    }

    /**
     * 1.3 实现用 Handler将线程从子线程切换到主线程, 用接口对象将数据存入接口
     */
    private class CallbackCollectionRunnable implements Runnable {

        private OnQueryListener onQueryListener;
        private ArrayList<CollectionSqlData> tArrayList;

        public CallbackCollectionRunnable(OnQueryListener onQueryListener, ArrayList<CollectionSqlData> tArrayList) {
            this.onQueryListener = onQueryListener;
            this.tArrayList = tArrayList;
        }

        @Override
        public void run() {
            onQueryListener.onQuery(tArrayList);
        }
    }


    /**
     * 定义 查询数据库的 泛型 接口
     */
    public interface OnQueryListener<T> {
        void onQuery(ArrayList<T> tArrayList);
    }


}
