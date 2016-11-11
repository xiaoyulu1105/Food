package com.example.dllo.food.library.search;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;
import com.example.dllo.food.beans.TextEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by XiaoyuLu on 16/11/1.
 *
 * 食物百科 查询的 Activity
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton imgBtnReturn;
    private EditText textEdt;
    private ImageView deleteIV;
    private ImageView searchIV;

    private String textStr; // 输入框的字符串

    @Override
    protected int getLayout() {
        return R.layout.activity_library_search;
    }

    @Override
    protected void initViews() {
        imgBtnReturn = bindView(R.id.library_search_return);
        textEdt = bindView(R.id.library_search_edt);
        deleteIV = bindView(R.id.library_search_delete);
        searchIV = bindView(R.id.library_search_search);

        setClick(this, imgBtnReturn, textEdt, deleteIV, searchIV);

        // 注册 EventBus 订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.library_search_frame, new SearchSearchFragment());
        transaction.commit();
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
            case R.id.library_search_edt:
                // 输入框

                break;
            case R.id.library_search_delete:
                Toast.makeText(this, "点击了删除", Toast.LENGTH_SHORT).show();

                break;
            case R.id.library_search_search:
                // TODO 跳转

                textStr = textEdt.getText().toString();

                if (textStr.length() <= 0) {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO EventBus 发布, 历史记录进行数据接收
                    EventBus.getDefault().post(new TextEvent(textStr));
                }

                break;
            default:
                Log.d("SearchActivity", "出错啦!");
                break;
        }

    }
}
