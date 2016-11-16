package com.example.dllo.food.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by XiaoyuLu on 16/11/7.
 *
 * 用 Drawable 实现图片的圆形
 */
public class CircleDrawable extends Drawable {

    private Bitmap bitmap; // 图片
    private Paint paint;  // 画笔
    private int radius; // 半径

    public CircleDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;

        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);

        // 设置画笔的花纹
        BitmapShader shader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, // 横向超出, 超出部分用背景色填充
                Shader.TileMode.CLAMP);
        paint.setShader(shader);

        // 设置半径的大小
        radius = Math.min(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    }

    @Override
    public void draw(Canvas canvas) {
        // 画圆
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getWidth() / 2, radius, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    /** 手动添加两个方法*/
    @Override
    public int getIntrinsicWidth() {
        return radius * 2;
    }

    @Override
    public int getIntrinsicHeight() {
        return radius * 2;
    }
}
