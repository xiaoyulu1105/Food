package com.example.dllo.food.library;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/5.
 *
 * 食物百科 中的 搜索对比 里面的 Activity界面
 */
public class CompareActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected int getLayout() {
        return R.layout.activity_library_compare;
    }

    @Override
    protected void initViews() {
        ImageButton returnImgBtn = bindView(R.id.library_compare_return);
        ImageView firstIv = bindView(R.id.library_compare_first_img);
        ImageView secondIv = bindView(R.id.library_compare_second_img);

        setClick(this, returnImgBtn);
        setClick(this, firstIv, secondIv);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.library_compare_return:
                // 点击返回
                finish();

                break;
            case R.id.library_compare_first_img:
                // 点击第一个 添加图标
                Toast.makeText(this, "添加第一个对比物", Toast.LENGTH_SHORT).show();
                Log.d("CompareActivity", "添加第一个对比物");

                break;
            case R.id.library_compare_second_img:
                // 点击第二个 添加图片
                Toast.makeText(this, "添加第二个对比物", Toast.LENGTH_SHORT).show();
                Log.d("CompareActivity", "添加第二个对比物");

            default:
                Log.d("CompareActivity", "出错啦!");
                break;
        }
    }
}
