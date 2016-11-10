package com.example.dllo.food.eat.evaluate;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/10.
 *
 * 点击 狂吃 中的 测评 后, 跳转到该Activity
 */
public class EvaluateMoreActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton returnImgBtn;
    private WebView webView;
    private Button shareBtn;
    private Button collectBtn;

    private String getLink;  // 点击测评中 Item 时传递过来的 网页的链接数据

    @Override
    protected int getLayout() {
        return R.layout.activity_eat_evaluate_more;
    }

    @Override
    protected void initViews() {
        returnImgBtn = bindView(R.id.eat_evaluate_more_return);
        webView = bindView(R.id.eat_evaluate_more_web);
        shareBtn = bindView(R.id.eat_evaluate_more_share);
        collectBtn = bindView(R.id.eat_evaluate_more_collection);

        setClick(this, returnImgBtn, shareBtn, collectBtn);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        getLink = intent.getStringExtra("link");

        webViewMethod(getLink);
    }

    /** 实现网页内容铺建的 相关操作 */
    private void webViewMethod(String linkUrl) {
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(linkUrl);
    }

    /** 手动复写的方法, 添加 finish() 方法 */
    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.eat_evaluate_more_return:
                finish();

                break;
            case R.id.eat_evaluate_more_share:
                Toast.makeText(this, "分享实现中...", Toast.LENGTH_SHORT).show();

                break;
            case R.id.eat_evaluate_more_collection:
                // TODO 收藏功能需要实现
                Toast.makeText(this, "收藏实现中...", Toast.LENGTH_SHORT).show();

                break;
            default:
                Log.d("EvaluateMoreActivity", "点错啦!");
                break;
        }
    }
}
