package com.example.dllo.food.volley;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.dllo.food.R;
import com.example.dllo.food.base.MyApp;
import com.example.dllo.food.tools.CircleDrawable;

/**
 * Created by XiaoyuLu on 16/10/27.
 *
 * 在单例类 中定义请求队列 RequestQueue 和 ImageLoader
 */
public class VolleySingleton {

    // 懒汉式单例类
    // 将需要用单例方式 初始化的 类或对象在该类里实现初始化
    // context 方式传参在这里不适合, 可能导致内存泄漏
    // 用Application的context更恰当,
    // 具体在 MyApp 类中实现, MyApp 需要注册

    private static VolleySingleton volleySingleton;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader; // 用来请求图片的 ImageLoader类
    private static Object object = new Object();

    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(MyApp.getContext());
        imageLoader = new ImageLoader(requestQueue, new MyImageCache()); // 自定义的MyImageCache类
    }

    public static VolleySingleton getInstance() {
        if (volleySingleton == null) {  // 目的: 提高效率, 让多余的线程排队等候
//            synchronized (VolleySingleton.class) { // 同步锁, 参数是 本类, this(本类对象, 每一次会)不可以
                synchronized (object) {   // 锁可以一个固定的任何类的 对象(需要初始化), this不可以
                if (volleySingleton == null) {
                    volleySingleton = new VolleySingleton();
                }
            }
        }

        return volleySingleton;
    }

    /** 向请求队列 添加 请求 */
    public <T> void addRequest(Request<T> request){
        requestQueue.add(request);
    }


    /** 请求图片 显示在 ImageView 中 */
    public void getImage(String url, final ImageView imageView) {
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                Bitmap bitmap = response.getBitmap();
                if (bitmap == null) {
                    // 图片还在请求中, 先给一张默认的图片
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    // 图片请求成功
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleySingleton", "图片请求失败");
                Log.d("VolleySingleton", error.getMessage());
            }
        });
    }

    /** 请求图片 实现图片的圆形显示 */
    public void getCircleImage(String url, final ImageView imageView) {

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                Bitmap bitmap = response.getBitmap();
                if (bitmap == null) {
                    // 图片还在请求中, 先给一张默认的图片
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    // 图片请求成功
                    CircleDrawable drawable = new CircleDrawable(bitmap);
                    imageView.setImageDrawable(drawable);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleySingleton", "图片请求失败");
                Log.d("VolleySingleton", error.getMessage());
            }
        });

    }


    /** 请求图片 方法2 带有动画效果 */
    public void getAnimImage(String url, ImageView imageView) {
        // 带渐变动画效果的请求图片
        imageLoader.get(url, new AnimImageListener(imageView));
    }



//    // 取消请求
//    public void  fun() {
//        requestQueue.cancelAll(tag);
//    }


}
