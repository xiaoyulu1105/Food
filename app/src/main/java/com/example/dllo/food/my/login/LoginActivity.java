package com.example.dllo.food.my.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by XiaoyuLu on 16/11/5.
 *
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button returnBtn;

    private Button registerBtn;

    private EditText usernameEdt;
    private EditText passwordEdt;
    private Button loginBtn; // 登录按钮

    private String getUsername; // 输入的用户名
    private String getPassword; // 密码


    private Button qqBtn;
    private Button wechatBtn;
    private Button weBoBtn;
    private Button booHeeBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        returnBtn = bindView(R.id.my_login_return);
        registerBtn = bindView(R.id.my_login_register);

        usernameEdt = bindView(R.id.mine_login_username);
        passwordEdt = bindView(R.id.mine_login_password);
        loginBtn = bindView(R.id.mine_login_login);

        qqBtn = bindView(R.id.my_login_qq);
        wechatBtn = bindView(R.id.my_login_wechat);
        weBoBtn = bindView(R.id.my_login_weibo);
        booHeeBtn = bindView(R.id.my_login_boohee);

        setClick(this, returnBtn, registerBtn, loginBtn);
        setClick(this, qqBtn, wechatBtn, weBoBtn, booHeeBtn);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_login_return:
                finish();

                break;
            case R.id.my_login_register:
                Intent intent = new Intent(LoginActivity.this, MyRegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.mine_login_login:
                judgeIfCorrect();

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

    /** 点击登录 先判断满不满足简单的要求 */
    private void judgeIfCorrect() {

        getUsername = usernameEdt.getText().toString();
        getPassword = passwordEdt.getText().toString();

        if (getUsername.length() <= 0 || getPassword.length() <= 0) {
            Toast.makeText(this, "输入不能为空!", Toast.LENGTH_SHORT).show();

        } else {
            // 只要输入的不为空就直接登录试试

            loginMethod();
        }
    }

    /**  进行登录 */
    private void loginMethod() {

        cn.bmob.v3.BmobUser bmobUser = new cn.bmob.v3.BmobUser();
        bmobUser.setUsername(getUsername);
        bmobUser.setPassword(getPassword);
        bmobUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    // 登录成功, 退出该界面, 返回主界面
                    Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();

                } else {
                    Toast.makeText(LoginActivity.this, "登录失败!", Toast.LENGTH_SHORT).show();
                    Log.d("LoginActivity", "登录失败!");
                    Log.d("LoginActivity", e.getMessage());
                }
            }
        });

    }

}
