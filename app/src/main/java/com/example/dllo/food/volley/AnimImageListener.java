package com.example.dllo.food.volley;

import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.food.R;

/**
 * Created by XiaoyuLu on 16/11/7.
 *
 * 在VolleySingleton 中调用, 一个带有动画效果的 监听类
 */
public class AnimImageListener implements ImageLoader.ImageListener{

    private ImageView imageView;

    public AnimImageListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

        Bitmap bitmap = response.getBitmap();
        if (bitmap == null) {
            // 图片还在请求中, 先给一张默认的图片
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            // 图片请求成功
            imageView.setImageBitmap(bitmap);

            // 可以自定义添加动画效果, 这里实现了透明度渐变的动画效果
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1f);
            alphaAnimation.setDuration(1500);
            imageView.setAnimation(alphaAnimation);
            alphaAnimation.start();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
