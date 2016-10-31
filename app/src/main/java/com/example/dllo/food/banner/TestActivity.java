package com.example.dllo.food.banner;

import android.app.Activity;
import android.os.Bundle;

import com.example.dllo.food.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dllo on 16/10/31.
 */
public class TestActivity extends Activity {

    private ArrayList<String> pics = new ArrayList<>();
    String url1 = "http://img.boqiicdn.com/Data/BK/A/1411/26/img77931416972193.jpg";
    String url2 = "http://pic29.nipic.com/20130506/3822951_102005116000_2.jpg";
    String url3 = "http://pic36.nipic.com/20131125/8821914_090743677000_2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        pics.add(url1);
        pics.add(url2);
        pics.add(url3);

        Banner banner = (Banner) findViewById(R.id.banner_test);
        // 设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        // 设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        // 设置图片集合
        banner.setImages(pics);
        // 设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        // 设置标题集合（当banner样式有显示title时
        banner.setBannerTitles(Arrays.asList("Title1", "title2", "title3"));
        // 设置自动轮播，默认为true
        banner.isAutoPlay(true);
        // 设置轮播时间
        banner.setDelayTime(2000);
        // 设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        // banner设置方法全部调用完毕时最后调用
        banner.start();

    }
}
