package com.example.dllo.food.my;


import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/4.
 *
 * 设置界面 的Activity
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        ImageButton returnImgBtn = bindView(R.id.my_set_return);

        setClick(this, returnImgBtn);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_set_return:
                finish();

                break;
            default:
                Log.d("SettingActivity", "出错啦");
        }
    }
}
