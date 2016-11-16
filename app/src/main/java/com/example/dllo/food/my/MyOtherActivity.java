package com.example.dllo.food.my;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/16.
 *
 * 点击我的照片, 上传食物数据 和我的订单时进行图片的显示
 */
public class MyOtherActivity extends BaseActivity{

    private ImageView photoIV;
    private ImageView dataIV;
    private ImageView orderIV;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_other;
    }

    @Override
    protected void initViews() {
        photoIV = bindView(R.id.my_other_photo);
        dataIV = bindView(R.id.my_other_upload_food);
        orderIV = bindView(R.id.my_other_order);
    }

    @Override
    protected void initData() {
        String otherType = getIntent().getStringExtra("otherType");
        if (otherType.equals("photo")) {
            photoIV.setVisibility(View.VISIBLE);
            dataIV.setVisibility(View.INVISIBLE);
            orderIV.setVisibility(View.INVISIBLE);

        } else if (otherType.equals("data")) {
            photoIV.setVisibility(View.INVISIBLE);
            dataIV.setVisibility(View.VISIBLE);
            orderIV.setVisibility(View.INVISIBLE);

        } else if (otherType.equals("order")) {
            photoIV.setVisibility(View.INVISIBLE);
            dataIV.setVisibility(View.INVISIBLE);
            orderIV.setVisibility(View.VISIBLE);
        } else {
            Log.d("MyOtherActivity", "出错啦!");
        }
    }
}
