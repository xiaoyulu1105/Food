package com.example.dllo.food.library.search;

import android.util.Log;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;

/**
 * Created by XiaoyuLu on 16/11/12.
 *
 * 简单搜索后 出来的 界面
 *
 * 获取 Activity 的值: http://dwtedx.com/itshare_296.html
 */
public class SearchSimpleFragment extends BaseFragment{

    private String getTextStr;  // SearchActivity 的输入框的内容

    @Override
    protected int getLayout() {
        return R.layout.fragment_library_search_simple;
    }

    @Override
    protected void initViews() {

        getTextStr = ((SearchActivity)getActivity()).getTextStr();
        Toast.makeText(mContext, getTextStr, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initData() {

    }
}
