package com.example.dllo.food.eat.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by XiaoyuLu on 16/11/10.
 *
 * 点击 逛吃中的 首页的普通 Item 后跳转到该界面
 */
public class HomePageMoreActivity extends BaseActivity implements View.OnClickListener{

    private ImageView returnIV;
    private ImageView shareIV;

    private ImageView iconIV;
    private TextView publisherTV;
    private ImageView imageIV;

    private ImageView agreeIV;
    private TextView likeCountTV;
    private boolean isLike = false;  // 用于判断是否点赞

    private String getIconUrl;  // 传递过来的头像的链接
    private String getPublisher;
    private String getImageUrl;
    private int getLikeCount;


    // 定义静态常量, 作为传值时 的 key 值
    public static final String INTENT_ICON = "iconUrl";
    public static final String INTENT_PUBLISHER = "publisher";
    public static final String INTENT_IMAGE = "imageUrl";
    public static final String INTENT_LIKE_CT = "likeCt";
    public static final String INTENT_BUNDLE_KEY = "bundle";

    @Override
    protected int getLayout() {
        return R.layout.activity_eat_homepage_more;
    }

    @Override
    protected void initViews() {
        returnIV = bindView(R.id.eat_homepage_more_return);
        shareIV = bindView(R.id.eat_homepage_more_share);

        iconIV = bindView(R.id.eat_homepage_more_icon);
        publisherTV = bindView(R.id.eat_homepage_more_publisher);
        imageIV = bindView(R.id.eat_homepage_more_image);

        agreeIV = bindView(R.id.eat_homepage_more_like);
        likeCountTV = bindView(R.id.eat_homepage_more_count);

        setClick(this, returnIV, shareIV, agreeIV);
    }

    @Override
    protected void initData() {

        getIntentDataAndShow();
    }

    /** 获取 Intent的传值, 并铺建 */
    private void getIntentDataAndShow() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(INTENT_BUNDLE_KEY);
        getIconUrl = bundle.getString(INTENT_ICON);
        getPublisher = bundle.getString(INTENT_PUBLISHER);
        getImageUrl = bundle.getString(INTENT_IMAGE);
        getLikeCount = bundle.getInt(INTENT_LIKE_CT);

        Picasso.with(HomePageMoreActivity.this).load(getIconUrl).into(iconIV);
        publisherTV.setText(getPublisher);
        Picasso.with(HomePageMoreActivity.this).load(getImageUrl).into(imageIV);

        String likeCountString = String.valueOf(getLikeCount);
        likeCountTV.setText(likeCountString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eat_homepage_more_return:
                finish();

                break;
            case R.id.eat_homepage_more_share:
                Toast.makeText(this, "分享实现中", Toast.LENGTH_SHORT).show();

                break;
            case R.id.eat_homepage_more_like:
                aboutLikeMethod();

                break;
            default:
                Log.d("HomePageMoreActivity", "点击出错啦!");
                break;
        }
    }

    /** 点赞后的处理 */
    private void aboutLikeMethod() {
        isLike = !isLike;
        if (isLike) {
            Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show();
            agreeIV.setImageResource(R.mipmap.ic_photo_agree_selected);

            getLikeCount++;
            String likeCountString = String.valueOf(getLikeCount);
            likeCountTV.setText(likeCountString);

        } else {
            Toast.makeText(this, "取消点赞", Toast.LENGTH_SHORT).show();
            agreeIV.setImageResource(R.mipmap.ic_photo_agree);

            getLikeCount--;
            String likeCountString = String.valueOf(getLikeCount);
            likeCountTV.setText(likeCountString);
        }
    }
}
