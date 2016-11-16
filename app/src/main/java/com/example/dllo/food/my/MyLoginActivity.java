package com.example.dllo.food.my;

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
 * Created by XiaoyuLu on 16/11/15.
 *
 * 我的 自己的账号登录的登录界面
 */
public class MyLoginActivity extends BaseActivity implements View.OnClickListener{

    private Button returnBtn;
    private Button registerBtn;

    private EditText usernameEdt;
    private EditText passwordEdt;
    private Button loginBtn;

    private String getUsername; // 输入的用户名
    private String getPassword; // 密码

    @Override
    protected int getLayout() {
        return R.layout.activity_regist_login;
    }

    @Override
    protected void initViews() {
        returnBtn = bindView(R.id.mine_login_return);
        registerBtn = bindView(R.id.mine_login_register);

        usernameEdt = bindView(R.id.mine_login_username);
        passwordEdt = bindView(R.id.mine_login_password);
        loginBtn = bindView(R.id.mine_login_login);

        setClick(this, returnBtn, registerBtn, loginBtn);
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_login_return:
                finish();

                break;
            case R.id.mine_login_register:
                // TODO 跳转到注册界面
                Intent intent = new Intent(MyLoginActivity.this, MyRegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.mine_login_login:
                judgeIfCorrect();

                break;
            default:
                Log.d("MyLoginActivity", "点击出错啦!");
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
                    Toast.makeText(MyLoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                    MyLoginActivity.this.finish();

                } else {
                    Toast.makeText(MyLoginActivity.this, "登录失败!", Toast.LENGTH_SHORT).show();
                    Log.d("MyLoginActivity", "登录失败!");
                    Log.d("MyLoginActivity", e.getMessage());
                }
            }
        });

    }
}
