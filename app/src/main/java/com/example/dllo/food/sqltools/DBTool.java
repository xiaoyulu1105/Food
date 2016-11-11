package com.example.dllo.food.sqltools;

import android.os.Handler;

import com.example.dllo.food.values.DBValues;
import com.litesuits.orm.LiteOrm;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by dllo on 16/10/29.
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

    // 插入数据库的 泛型 方法
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

    // 查询数据库的 泛型 方法
    // 使用接口回调将数据返回到 主线程, 所以返回值不需要有, 也不应该有
    public<T> void queryAllData(Class<T> tClass, OnQueryListener<T> onQueryListener) {

        threadPoolExecutor.execute(new QueryRunnable<T>(tClass, onQueryListener));
    }

    /* 实现 查询数据库的  外层 Runnable 泛型 类 */
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
            handler.post(new CallbackRunnable<T>(onQueryListener, tArrayList));
        }
    }

    /* 实现用 Handler将线程从子线程切换到主线程, 用接口对象将数据存入接口 */
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

    /* 定义 查询数据库的 泛型 接口 */
    public interface OnQueryListener<T>{
        void onQuery(ArrayList<T> t);
    }

}
