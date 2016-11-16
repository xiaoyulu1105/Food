package com.example.dllo.food.eat.homepage;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;
import com.example.dllo.food.values.UrlValues;

/**
 * Created by XiaoyuLu on 16/11/10.
 *
 * 点击 逛吃中的 首页的第一页(广告页) 后跳转到该网页
 */
public class HomePageMoreFirstActivity extends BaseActivity implements View.OnClickListener{

    private ImageView returnIV;
    private WebView webView;
    private String getLink; // 上级界面传递过来的 link
    public static final String INTENT_LINK = "link";

    @Override
    protected int getLayout() {
        return R.layout.activity_eat_homepage_more_first;
    }

    @Override
    protected void initViews() {
        returnIV = bindView(R.id.eat_homepage_more_first_iv);
        webView = bindView(R.id.eat_homepage_more_first_web);

        setClick(this, returnIV );
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        // getLink 获取为空
        getLink = intent.getStringExtra(INTENT_LINK);
        getLink = UrlValues.EAT_HOMEPAGE_FIRST_LINK;

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(getLink);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eat_homepage_more_first_iv:
                finish();

                break;
            default:
                Log.d("HomePageMoreFirstA", "点击出错啦!");
                break;
        }
    }
}
