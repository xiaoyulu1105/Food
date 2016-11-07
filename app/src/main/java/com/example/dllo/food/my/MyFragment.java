package com.example.dllo.food.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.tools.CircleDrawable;

/**
 * Created by XiaoyuLu on 16/10/31.
 * <p/>
 * 我的 选项
 */
public class MyFragment extends BaseFragment implements View.OnClickListener{

    private ImageButton settingImgBtn;
    private Button loginBtn;
    private LinearLayout photoLl;
    private LinearLayout collectionLl;
    private LinearLayout foodDataLl;
    private LinearLayout orderLl;
    private ImageView iconIV;

    @Override
    protected int getLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initViews() {

        settingImgBtn = bindView(R.id.my_setting);
        loginBtn = bindView(R.id.my_login);
        iconIV = bindView(R.id.my_icon);

        photoLl = (LinearLayout) getActivity().findViewById(R.id.my_photo_ll);
        collectionLl = (LinearLayout) getActivity().findViewById(R.id.my_collection_ll);
        foodDataLl = (LinearLayout) getActivity().findViewById(R.id.my_data_ll);
        orderLl = (LinearLayout) getActivity().findViewById(R.id.my_order_ll);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_analyse_default);
        CircleDrawable drawable = new CircleDrawable(bitmap);
        iconIV.setImageDrawable(drawable);

        setClick(this, settingImgBtn, loginBtn);
        setClick(this, photoLl, collectionLl, foodDataLl, orderLl);

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
            case R.id.my_photo_ll:

                break;
            case R.id.my_collection_ll:

                break;
            case R.id.my_data_ll:

                break;
            case R.id.my_order_ll:

                break;
            default:
                Log.d("MyFragment", "出错啦!");

        }
    }
}
