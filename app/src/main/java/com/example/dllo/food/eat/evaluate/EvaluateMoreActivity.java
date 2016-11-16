package com.example.dllo.food.eat.evaluate;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;
import com.example.dllo.food.my.LoginActivity;
import com.example.dllo.food.sqltools.CollectionSqlData;
import com.example.dllo.food.sqltools.DBTool;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * Created by XiaoyuLu on 16/11/10.
 *
 * 点击 狂吃 中的 测评, 知识, 美食 后, 跳转到该Activity
 * 在收藏中 取消收藏后, 收藏列表 不能马上删除该记录, 怎么办?
 * 解决发方法: 用广播实现
 */
public class EvaluateMoreActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton returnImgBtn;
    private WebView webView;
    private Button shareBtn;
    private LinearLayout collectionLl;
    private ImageView collectionIV;
    private Button collectBtn;

    private String getLink;  // 点击测评中 Item 时传递过来的 网页的链接数据
    private String getTitle; // 传递来的 Title
    private DBTool dbTool;

    public static final String BROADCAST_ARTICLE_KEY = "broadcast";
    public static final String BROADCAST_ARTICLE_LINK = "link";

    public static final String INTENT_ARTICLE_TITLE = "title";
    public static final String INTENT_ARTICLE_LINK = "link";

    private BmobUser bmobUser;
    private String getUsername;

    @Override
    protected int getLayout() {
        return R.layout.activity_eat_evaluate_more;
    }

    @Override
    protected void initViews() {
        returnImgBtn = bindView(R.id.eat_evaluate_more_return);
        webView = bindView(R.id.eat_evaluate_more_web);
        shareBtn = bindView(R.id.eat_evaluate_more_share);

        collectionLl = (LinearLayout)findViewById(R.id.eat_evaluate_more_collection_ll);
        collectionIV = bindView(R.id.eat_evaluate_more_collection_iv);
        collectBtn = bindView(R.id.eat_evaluate_more_collection_btn);

        setClick(this, returnImgBtn, shareBtn, collectionIV, collectBtn);
        collectionLl.setOnClickListener(this);

        dbTool = new DBTool();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        getLink = intent.getStringExtra("link");
        getTitle = intent.getStringExtra("title");

        bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        getUsername = bmobUser.getUsername();

        judgeIfCollection(getUsername, getLink);
        webViewMethod(getLink);
    }

    /** 判断收藏数据库中是否有该链接, 有则将红心设置为红色 */
    private void judgeIfCollection(String getUsername, final String getLink) {
        dbTool.queryCollectionDataByUsername(getUsername, new DBTool.OnQueryListener<CollectionSqlData>() {
            @Override
            public void onQuery(ArrayList<CollectionSqlData> collectionSqlDatas) {
                for (int i = 0; i < collectionSqlDatas.size(); i++) {
                    String link = collectionSqlDatas.get(i).getLink();
                    if (link.equals(getLink)) {
                        // 当该文章被收藏过时, 桃心为红色
                        collectBtn.setText("已收藏");
                        collectionIV.setImageResource(R.mipmap.ic_news_keep_heighlight);
                    }
                }
            }
        });
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
            case R.id.eat_evaluate_more_collection_ll:
            case R.id.eat_evaluate_more_collection_iv:
            case R.id.eat_evaluate_more_collection_btn:
                judgeIfLoginMethod();

                break;
            default:
                Log.d("EvaluateMoreActivity", "点错啦!");
                break;
        }
    }

    /** 保存或者取消收藏 */
    private void judgeIfLoginMethod() {

        // TODO 判断是否登录
        if (bmobUser == null) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(EvaluateMoreActivity.this, LoginActivity.class);
            startActivity(intent1);

        } else {
            saveOrCancelCollectMethod(getUsername);
        }



    }

    /** 将收藏的数据和用户名绑定在一起 实现保存和取消  */
    private void saveOrCancelCollectMethod(String getUsername) {
        if (collectBtn.getText().toString().equals("已收藏")) {
            // 取消收藏, 删除数据库数据
            collectBtn.setText("收藏");
            collectionIV.setImageResource(R.mipmap.ic_news_keep_default);
            Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();

            dbTool.deleteCollectionByCondition(CollectionSqlData.class, getUsername, getLink);

            // 为了更快的将消息传播出去
            // 我们可以选择用广播, 或者 EventBus
            Intent intent = new Intent("broadcast");
            intent.putExtra("link", getLink);
            sendBroadcast(intent);

        } else {
            // 收藏, 存入数据库
            collectBtn.setText("已收藏");
            collectionIV.setImageResource(R.mipmap.ic_news_keep_heighlight);
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();

            CollectionSqlData collectionSqlData = new CollectionSqlData();
            collectionSqlData.setUsername(getUsername);
            collectionSqlData.setTitle(getTitle);
            collectionSqlData.setLink(getLink);

            dbTool.insert(collectionSqlData);
        }
    }
}
