package com.example.dllo.food;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.food.base.BaseActivity;

import cn.bmob.v3.Bmob;

/**
 * Created by XiaoyuLu on 16/11/5.
 *
 * 启动页面
 */
public class StartActivity extends BaseActivity implements View.OnClickListener{

    private CountDownTimer timer;
    private ImageView imageView;

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews() {
        imageView = bindView(R.id.start_iv);
        setClick(this, imageView);

    }

    @Override
    protected void initData() {
        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        };
        timer.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_iv:
                // 点击图片 实现提前跳转
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                timer.cancel();

                break;
            default:
                Log.d("StartActivity", "出错啦!");
                break;
        }
    }
}
