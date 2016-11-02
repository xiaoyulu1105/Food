package com.example.dllo.food.library;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/1.
 *
 * 食物百科 查询的 Activity
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected int getLayout() {
        return R.layout.activity_library_search;
    }

    @Override
    protected void initViews() {
        ImageButton imgBtnReturn = bindView(R.id.library_search_return);
        EditText edtText = bindView(R.id.library_search_edt);
        ImageButton imgBtnDelete = bindView(R.id.library_search_delete);
        ImageButton imgBtnSearch = bindView(R.id.library_search_search);

        LinearLayout ll = (LinearLayout) findViewById(R.id.library_search_history_ll);
        ListView listView = bindView(R.id.library_search_history_list);

        RecyclerView recyclerView = bindView(R.id.library_search_recycler);

        setClick(this, imgBtnReturn, edtText, imgBtnDelete, imgBtnSearch);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.library_search_return:
                // 返回键
                finish();

                break;
            case R.id.library_search_edt:
                // 输入框

                break;
            case R.id.library_search_delete:
                // 删除已经输入的文本

                break;
            case R.id.library_search_search:
                // 点击放大镜按钮, 进行搜索

                break;
            default:
                Log.d("SearchActivity", "出错啦!");
                break;

        }

    }
}
