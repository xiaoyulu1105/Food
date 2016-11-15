package com.example.dllo.food.my;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/5.
 *
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button returnBtn;
    private Button qqBtn;
    private Button wechatBtn;
    private Button weBoBtn;
    private Button booHeeBtn;
    private Button loginBtn; // 账号登录

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        returnBtn = bindView(R.id.my_login_return);
        loginBtn = bindView(R.id.my_login_login);

        qqBtn = bindView(R.id.my_login_qq);
        wechatBtn = bindView(R.id.my_login_wechat);
        weBoBtn = bindView(R.id.my_login_weibo);
        booHeeBtn = bindView(R.id.my_login_boohee);

        setClick(this, returnBtn, loginBtn);
        setClick(this, qqBtn, wechatBtn, weBoBtn, booHeeBtn);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_login_return:
                Toast.makeText(this, "return", Toast.LENGTH_SHORT).show();
                // TODO 疑惑: 为什么不返回 MainActivity, 而是跳出一个新界面
                finish();

                break;
            case R.id.my_login_login:
                Intent intent = new Intent(LoginActivity.this, MyLoginActivity.class);
                startActivity(intent);

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
