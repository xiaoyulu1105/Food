package com.example.dllo.food.my;

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
 * 自定义的 注册的界面
 */
public class MyRegisterActivity extends BaseActivity implements View.OnClickListener{

    private Button returnBtn;
    private Button saveBtn;

    private EditText usernameEdt;
    private EditText passwordEdt;
    private EditText passwordSecEdt;

    private String getUsername; // 输入的用户名
    private String getPassword; // 密码
    private String getPasswordSec; // 确认密码

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_register;
    }

    @Override
    protected void initViews() {
        returnBtn = bindView(R.id.mine_register_return);
        saveBtn = bindView(R.id.mine_register_save);

        usernameEdt = bindView(R.id.mine_register_username);
        passwordEdt = bindView(R.id.mine_register_password);
        passwordSecEdt = bindView(R.id.mine_register_password_second);

        setClick(this, returnBtn, saveBtn);

    }

    @Override
    protected void initData() {
//        getUsername = usernameEdt.getText().toString();
//        getPassword = passwordEdt.getText().toString();
//        getPasswordSec = passwordSecEdt.getText().toString();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_register_return:
                finish();

                break;
            case R.id.mine_register_save:
                judgeIfCorrect();

                break;
            default:
                Log.d("MyRegisterActivity", "点击出错啦!");
                break;

        }
    }

    /** 点击注册时先判断 输入是否正确 */
    private void judgeIfCorrect() {

        getUsername = usernameEdt.getText().toString();
        getPassword = passwordEdt.getText().toString();
        getPasswordSec = passwordSecEdt.getText().toString();

        if (getUsername.length() <= 0 ||
                getPassword.length() <= 0 || getPasswordSec.length() <= 0) {
            Toast.makeText(this, "输入不能为空!", Toast.LENGTH_SHORT).show();

        } else if (getUsername.length() > 0 && (!getPassword.equals(getPasswordSec))) {
            Toast.makeText(this, "密码 与 确认密码 不相同!", Toast.LENGTH_SHORT).show();

        } else if (getUsername.length() > 0 && (getPassword.equals(getPasswordSec))) {
            // 满足条件, 进行注册
            createUser();

        } else {
            Toast.makeText(this, "输入有误!", Toast.LENGTH_SHORT).show();
        }
    }

    /** 使用 bmob 创建用户 */
    private void createUser() {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(getUsername);
        bmobUser.setPassword(getPassword);

        Log.d("MyRegisterActivity", "bmobUser:" + bmobUser);

        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(MyRegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyRegisterActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                    Log.d("MyRegisterActivity", "注册失败!");
                    Log.d("MyRegisterActivity", e.getMessage());
                }
            }
        });
    }
}
