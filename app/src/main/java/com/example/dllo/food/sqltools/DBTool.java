package com.example.dllo.food.sqltools;

import android.os.Handler;
import android.util.Log;

import com.example.dllo.food.values.DBValues;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by XiaoyuLu on 16/10/29.
 *
 *  区分 Class<T> 与 T 的关系与区别
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

    /** 插入数据库的 泛型 方法 */
    public<T> void insert(T t) {
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

    /** 删除 数据库所有数据 泛型方法实现 */
    public<T> void deleteAllData(Class<T> tClass) {
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

    /** 按条件 删除某一条 收藏数据 */
    public void deleteCollectionByCondition(Class<CollectionSqlData> collectionSqlDataClass, String getLink) {
        threadPoolExecutor.execute(new DeleteCollectionDataByCondRunnable(collectionSqlDataClass, getLink));
    }
    // 内部类: 按条件 删除某一条 收藏数据
    private class DeleteCollectionDataByCondRunnable implements Runnable {
        private Class<CollectionSqlData> collectionSqlDataClass;
        private String link;
        public DeleteCollectionDataByCondRunnable(Class<CollectionSqlData> collectionSqlDataClass, String getLink) {
            this.collectionSqlDataClass = collectionSqlDataClass;
            this.link = getLink;
        }
        @Override
        public void run() {
            liteOrm.delete(new WhereBuilder(collectionSqlDataClass).where("link = ?", link));
        }
    }

    // 查询数据库的 泛型 方法
    /** 使用接口回调将数据返回到 主线程, 所以返回值不需要有, 也不应该有 !!! */
    public<T> void queryAllData(Class<T> tClass, OnQueryListener<T> onQueryListener) {

        threadPoolExecutor.execute(new QueryRunnable<>(tClass, onQueryListener));
    }


    /** 实现 查询数据库的  外层 Runnable 泛型 类 */
    private class QueryRunnable<T> implements Runnable{

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

    /** 实现用 Handler将线程从子线程切换到主线程, 用接口对象将数据存入接口 */
    private class CallbackRunnable<T> implements Runnable{

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

    /** 定义 查询数据库的 泛型 接口 */
    public interface OnQueryListener<T>{
        void onQuery(ArrayList<T> tArrayList);
    }


}
