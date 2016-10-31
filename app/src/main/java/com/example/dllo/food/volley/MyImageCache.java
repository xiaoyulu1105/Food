package com.example.dllo.food.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by XiaoyuLu on 16/10/27.
 *
 * 该类 是 ImageLoader 使用的内存缓存
 * 在 VolleySingleton类中的 构造方法有被作为参数使用
 *
 */
public class MyImageCache implements ImageLoader.ImageCache {

    // 实现了最近最少使用算法: LRU ,LruCache类
    // LruCache 用法和 HashMap 类似

    private LruCache<String, Bitmap> lruCache;

    public MyImageCache() {

        // 缓存的上限 是 内存的 1/8
        int maxSize = (int)(Runtime.getRuntime().maxMemory() / 8);

        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {

                // 返回每一个元素占用的大小, 单位需要和 maxSize 保持一致
                return value.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
