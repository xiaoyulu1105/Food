package com.example.dllo.food.library.search;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;
import com.example.dllo.food.beans.event.TextEvent;
import com.example.dllo.food.library.LibraryFragment;
import com.example.dllo.food.dbtools.DBTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by XiaoyuLu on 16/11/1.
 *
 * 食物百科 查询的 Activity
 *
 * 使用EventBus 实现传值, 接收F1 中的ListView的点击的Item
 * 和 F1中的RecyclerView 中的点击 的 Item
 *
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    private ImageView returnIV;
    private EditText textEdt;
    private ImageView deleteIV;
    private ImageView searchIV;

    private String textStr; // 输入框的字符串, 设置GetSet方法
    private DBTool dbTool;

    // 当其他页跳到该页时, 需要传一个值,
    // 用于判断是简单搜索还是对比搜索, 这是传值时的KEY值
    public static final String INTENT_SEARCH_TYPE = "searchType";
    private  String getSearchType; // 存放搜索的类型的字符串
    private FragmentManager manager;  // 用于实现替换 Fragment 的管理员

    @Override
    protected int getLayout() {
        return R.layout.activity_library_search;
    }

    @Override
    protected void initViews() {
        returnIV = bindView(R.id.library_search_return);
        textEdt = bindView(R.id.library_search_edt);
        deleteIV = bindView(R.id.library_search_delete);
        searchIV = bindView(R.id.library_search_search);

        setClick(this, returnIV, deleteIV, searchIV);

        dbTool = new DBTool();
        manager = getSupportFragmentManager();

        // 注册 EventBus 订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

        transactToSearchFragment();

        textEdtListenerMethod(textEdt);

        Intent intent = getIntent();
        getSearchType = intent.getStringExtra(SearchActivity.INTENT_SEARCH_TYPE);
        Log.d("SearchActivity", getSearchType);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 取消注册
        EventBus.getDefault().unregister(this);
    }

    // 第二步, 注册订阅者, 实现 订阅者的方法
    // @Subscribe 必须写,手动添加 至少有一个方法需要要被 @Subscribe 修饰
    @Subscribe(threadMode = ThreadMode.MAIN)
    public TextEvent getTextEvent(TextEvent textEvent) {
        String getText = textEvent.getText();
        textEdt.setText(getText);

        return textEvent;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.library_search_return:
                finish();

                break;
            case R.id.library_search_delete:
                textEdt.getText().clear();
                transactToSearchFragment();

                break;
            case R.id.library_search_search:

                textStr = textEdt.getText().toString();
                if (textStr.length() <= 0) {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    // 实现Fragment的跳转, 将搜索数据存入数据库
                    setTextStr(textStr);
                    clickSearchSaveAndTransact(textStr);
                }

                break;
            default:
                Log.d("SearchActivity", "出错啦!");
                break;
        }

    }

    /** textEdt 的文本变化时 的监听事件 */
    private void textEdtListenerMethod(final EditText textEdt) {
        textEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                judgeIfTextNull(textEdt);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                judgeIfTextNull(textEdt);
            }
        });
    }

    /** 判断输入框是否为空 */
    private void judgeIfTextNull(EditText textEdt) {
        if (textEdt.getText().length() <= 0) {
            deleteIV.setVisibility(View.INVISIBLE);
            transactToSearchFragment();

        } else {
            deleteIV.setVisibility(View.VISIBLE);
            transactToSearchFragment();
        }
    }

    /** 点击搜索  保存和跳转 事件 */
    private void clickSearchSaveAndTransact(String textStr) {

        dbTool.insertHistory(textStr);

        FragmentTransaction transaction = manager.beginTransaction();
        if (getSearchType.equals(LibraryFragment.INTENT_SEARCH_SIMPLE_TYPE)) {
            // 如果是简单的搜索
            transaction.replace(R.id.library_search_frame, new SearchSimpleFragment());
            transaction.commit();

        } else {
            // 对比搜索
            transaction.replace(R.id.library_search_frame, new SearchCompareFragment());
            transaction.commit();
        }

    }


    /** fragment 转换为 搜索的 Fragment */
    private void transactToSearchFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.library_search_frame, new SearchSearchFragment());
        transaction.commit();

    }

    /** textStr 存放 输入框的字符串, 为其设置Get Set方法 */
    // 作用, 在 显示的 Fragment 中可以通过get方法获取到 搜索的字符,
    // 再将其变换为UTF-8格式, 实现网络请求
    public String getTextStr() {
        return textStr;
    }

    public void setTextStr(String textStr) {
        this.textStr = textStr;
    }
}
