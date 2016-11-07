package com.example.dllo.food.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.food.tools.CircleDrawable;
import com.example.dllo.food.values.WhatValues;
import com.example.dllo.food.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Xiaoyulu on 16/10/31.
 *
 * 通用的 ViewHolder
 * 方法功能: 设置文字, 设置图片, 设置圆形图片
 */

public class CommonVH extends RecyclerView.ViewHolder{
    // SparseArray 用法和HashMap 相似
    // 但是 Key 固定是 int 类型
    // 用他来存放所有的 View , key 就是 View 的 id

    private SparseArray<View> views;
    private View  itemView; // 行布局
    private Handler handler; // 用于将网络请求的 Bitmap 传到主线程

    public CommonVH(View itemView) {
        super(itemView);

        this.itemView = itemView;
        views = new SparseArray<>();
    }

    /**
     * 通过View的id来获得指定的View
     * 如果该View没有赋值,就先执行findViewById
     * 然后把它放到View的集合里
     * 使用泛型来取消强转
     * @param id
     * @return 指定 View
     */
    public<T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            // 证明 SparseArray 里没有这个 View
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    // 专门给 ListView 使用的方法
    public static CommonVH getViewHolder(View itemView, ViewGroup parent, int layoutItemId) {
        CommonVH commonVH;
        if (itemView == null) {
            Context context = parent.getContext();
            itemView = LayoutInflater.from(context).inflate(layoutItemId, parent, false);
            commonVH = new CommonVH(itemView);
            itemView.setTag(commonVH);
        } else {
            commonVH = (CommonVH) itemView.getTag();
        }
        return commonVH;
    }

    // 方法重载, 专门给 RecyclerView 的Adapter 使用的方法
    public static CommonVH getViewHolder(ViewGroup parent, int layoutItemId) {
        return getViewHolder(null, parent, layoutItemId);
    }

    // 返回(获取)行布局
    public View getItemView() {
        return itemView;
    }


    /*** ViewHolder 设置数据的方法 */
    // 设置文字
    public CommonVH setText(int id, String text) {
        TextView textView = getView(id);
        textView.setText(text);

        return this;
    }

    // 设置图片, 图片是 mipmap里的
    public CommonVH setImage(int imageViewId, int imgId) {
        ImageView imageView = getView(imageViewId);
        imageView.setImageResource(imgId);

        return this;
    }

    // 这是图片, 图片需要网络请求
    public CommonVH setImage(int imageViewId, String imgUrl) {
        ImageView imageView = getView(imageViewId);
        // Picasso 这种写法不行, 应用时出错
//        Picasso.with(MyApp.getContext()).load(imgUrl);
        VolleySingleton.getInstance().getImage(imgUrl, imageView);
        return this;
    }

    /** 显示圆形图片 */
    public CommonVH setCircleImage(final int imageViewId, String imgUrl) {
        final ImageView imageView = getView(imageViewId);
        // TODO 网络请求图片
        // 出现 NetworkOnMainThreadException 异常, 网络请求需要在子线程实现

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == WhatValues.WHAT_HOMEPAGE_ICON) {

                    Bitmap bitmap = (Bitmap) msg.obj;

                    CircleDrawable drawable = new CircleDrawable(bitmap);
                    imageView.setImageDrawable(drawable);
                }

                return false;
            }
        });

        new Thread(new MyBitmapRunnable(imgUrl)).start();

        return this;
    }

    /** 进行网络请求, 将获取的 图片的 Bitmap 返回到 主线程*/
    private class MyBitmapRunnable implements Runnable {

        private String imgUrl;

        public MyBitmapRunnable(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imgUrl);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                // 网上的连网方法
//            connection.setDoInput(true);
//            connection.connect();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    InputStream inputStream = connection.getInputStream();

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    Message message = new Message();
                    message.what = WhatValues.WHAT_HOMEPAGE_ICON;
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 设置 View 的点击事件
    public CommonVH setViewClick(int id, View.OnClickListener onClickListener) {
        getView(id).setOnClickListener(onClickListener);

        return this;
    }

    // 为行布局设置点击事件
    public CommonVH setItemClick (View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);

        return this;
    }

}
