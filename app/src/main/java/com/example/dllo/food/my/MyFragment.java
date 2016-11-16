package com.example.dllo.food.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.my.collection.CollectionActivity;
import com.example.dllo.food.tools.CircleDrawable;

import cn.bmob.v3.BmobUser;

/**
 * Created by XiaoyuLu on 16/10/31.
 * <p/>
 * 我的 选项
 */
public class MyFragment extends BaseFragment implements View.OnClickListener{

    private ImageView settingIV;
    private ImageView iconIV;
    private Button loginBtn;

    // 登录状态显示的 控件
    private LinearLayout loginAlreadyLl;
    private TextView usernameTV;
    private Button modifyBtn;
    private String getUsername;

    private LinearLayout collectionLl;


    @Override
    protected int getLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initViews() {

        settingIV = bindView(R.id.my_setting);
        loginBtn = bindView(R.id.my_login);
        iconIV = bindView(R.id.my_icon);

        loginAlreadyLl = (LinearLayout) getView().findViewById(R.id.my_login_already_ll);
        usernameTV = bindView(R.id.my_login_already_username);
        modifyBtn = bindView(R.id.my_login_already_modify);


        collectionLl = (LinearLayout) getActivity().findViewById(R.id.my_collection_ll);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_analyse_default);
        CircleDrawable drawable = new CircleDrawable(bitmap);
        iconIV.setImageDrawable(drawable);

        setClick(this, settingIV, loginBtn, modifyBtn, collectionLl);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_setting:
                // 点击 我的 右上角的 设置按钮
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

                break;
            case R.id.my_login:
                // 点击 登录按钮
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);

                break;
            case R.id.my_collection_ll:
                // 界面跳转, 如果登录了就跳到收藏页, 需要带值跳转, 将账号和收藏的东西对应上
                // TODO 未登录时则跳到登录界面
                Intent intent3 = new Intent(mContext, CollectionActivity.class);
                startActivity(intent3);

                break;
            default:
                Log.d("MyFragment", "点击出错啦!");

        }
    }

    /** 复写 Fragment 的生命周期, 当处于登录状态时显示用户名 */
    @Override
    public void onResume() {
        super.onResume();

        BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        if (bmobUser != null) {
            // 处于登录状态
            loginAlreadyLl.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.INVISIBLE);
            getUsername = bmobUser.getUsername();
            usernameTV.setText(getUsername);

        } else {
            loginAlreadyLl.setVisibility(View.INVISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
        }


    }
}
