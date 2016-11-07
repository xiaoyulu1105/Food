package com.example.dllo.food.my;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/5.
 *
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        ImageButton returnImgBtn = bindView(R.id.my_login_return);
        Button qqBtn = bindView(R.id.my_login_qq);
        Button weChatBtn = bindView(R.id.my_login_wechat);
        Button weBoBtn = bindView(R.id.my_login_weibo);
        Button booHeeBtn = bindView(R.id.my_login_boohee);

        setClick(this, returnImgBtn);
        setClick(this, qqBtn, weChatBtn, weBoBtn, booHeeBtn);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_login_return:
                // 点击 返回箭头
                finish();

                break;
            case R.id.my_login_qq:
                // 进行 QQ登录
                Toast.makeText(this, "QQ登录", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "QQ登录");

                break;
            case R.id.my_login_wechat:
                // 进行 微信登录
                Toast.makeText(this, "微信登录", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "微信登录");

                break;
            case R.id.my_login_weibo:
                // 进行 微博登录
                Toast.makeText(this, "微博登录", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "微博登录");

                break;
            case R.id.my_login_boohee:
                // 进行薄荷登录
                Toast.makeText(this, "薄荷登录", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "薄荷登录");

                break;
            default:
                Log.d("LoginActivity", "出错啦!");
        }
    }
}
