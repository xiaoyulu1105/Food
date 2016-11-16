package com.example.dllo.food.library.search;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.event.SearchTypeEvent;
import com.example.dllo.food.beans.event.TextEvent;
import com.example.dllo.food.beans.library.EveryoneSearchBean;
import com.example.dllo.food.library.LibraryFragment;
import com.example.dllo.food.sqltools.DBTool;
import com.example.dllo.food.sqltools.HistorySqlData;
import com.example.dllo.food.tools.DividerItemDecoration;
import com.example.dllo.food.tools.OnRecyclerViewItemClickListener;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/11.
 * <p/>
 * 跳转到搜索Activity时 进行搜索的 第一个Fragment
 */
public class SearchSearchFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout historyLl;  // 最近搜过的线性布局
    private RecyclerView historyRV; // 最近搜过的数据显示
    private Button deleteHistoryBtn; // 删除历史记录
    private RecyclerView recyclerView; // 大家都在 的数据

    private ArrayList<HistorySqlData> historyArrayList; // 搜索历史的数据集合
    private DBTool dbTool; // 用于对数据库的操作

    private String getSearchTypeStr; // 获取 EventBus 传递的搜索的类型的字符串


    @Override
    protected int getLayout() {
        return R.layout.fragment_library_search_search;
    }

    @Override
    protected void initViews() {
        historyLl = (LinearLayout) getView().findViewById(R.id.library_search_history_ll);
        historyRV = bindView(R.id.library_search_history_list);
        deleteHistoryBtn = bindView(R.id.library_search_history_delete);
        recyclerView = bindView(R.id.library_search_search_recycler);

        setClick(this, deleteHistoryBtn);

        historyArrayList = new ArrayList<>();
        dbTool = new DBTool();

        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

        everyoneSearchGson();
        judgeIfHistoryNull();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消 EventBus 的注册
        EventBus.getDefault().unregister(this);
    }

    /** 实现 EventBus 订阅者 */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public SearchTypeEvent getSearchTypeEvent(SearchTypeEvent event) {
        getSearchTypeStr = event.getSearchType();
        return event;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.library_search_history_delete:

                dbTool.deleteAllData(HistorySqlData.class);
                judgeIfHistoryNull();

                break;
            default:
                Log.d("SearchSearchFragment", "点击出错啦!");
                break;
        }
    }

    /**
     * 判断历史记录是否为空, 为空则 不显示
     */
    public void judgeIfHistoryNull() {
        dbTool.queryAllData(HistorySqlData.class, new DBTool.OnQueryListener<HistorySqlData>() {
            @Override
            public void onQuery(ArrayList<HistorySqlData> t) {
                historyArrayList = t;

                if (historyArrayList.size() <= 0) {

                    historyLl.setVisibility(View.GONE);

                } else {
                    showHistoryData(historyArrayList);
                }
            }
        });

    }

    /**
     * 当历史记录不为空时, 显示记录
     */
    private void showHistoryData(ArrayList<HistorySqlData> historyArrayList) {
        historyLl.setVisibility(View.VISIBLE);

        // 将数据类集合中的字符串集合获取, 并转换成数组类型
        // 集合顺序需要倒序
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = (historyArrayList.size() - 1); i >= 0 ; i--) {
            String string = historyArrayList.get(i).getHistoryStr();
            stringArrayList.add(string);
        }

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        historyRV.setLayoutManager(manager);
        MyHistoryRVAdapter adapter = new MyHistoryRVAdapter();
        adapter.setStringArrayList(stringArrayList);
        historyRV.setAdapter(adapter);

        historyRVItemClickMethod(adapter, stringArrayList);
    }

    /** RV时历史记录中 Item 使用接口回调实现点击监听 */
    private void historyRVItemClickMethod(MyHistoryRVAdapter adapter, final ArrayList<String> stringArrayList) {
        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String text = stringArrayList.get(position);
                EventBus.getDefault().post(new TextEvent(text));

                clickSearchSaveAndTransact(text);
            }
        });
    }

    /**
     * 通过Gson 请求 大家都在搜 的数据
     */
    private void everyoneSearchGson() {
        GsonRequest<EveryoneSearchBean> gsonRequest = new GsonRequest<>(
                EveryoneSearchBean.class, UrlValues.LIBRARY_SEARCH_EVERYONE_URL,
                new Response.Listener<EveryoneSearchBean>() {
                    @Override
                    public void onResponse(EveryoneSearchBean response) {
                        ArrayList<String> stringArrayList = (ArrayList<String>) response.getKeywords();

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        recyclerView.setLayoutManager(manager);

                        MyEveryoneSearchRVAdapter adapter = new MyEveryoneSearchRVAdapter();
                        adapter.setStringArrayList(stringArrayList);
                        recyclerView.setAdapter(adapter);

                        // 设置分隔线
                        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                                LinearLayoutManager.VERTICAL));
                        // 大家都在搜 Item 的点击实现
                        recyclerViewItemClickMethod(adapter, stringArrayList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SearchSearchFragment", "Gson请求失败啦!");
            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    /**
     * recyclerView 的 Item 的点击事件
     */
    private void recyclerViewItemClickMethod(MyEveryoneSearchRVAdapter adapter, final ArrayList<String> stringArrayList) {
        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String recyclerItemStr = stringArrayList.get(position);
                // EventBus 发布事件
                EventBus.getDefault().post(new TextEvent(recyclerItemStr));

                clickSearchSaveAndTransact(recyclerItemStr);
            }
        });
    }

    /**
     * 点击搜索 或者 记录 或者 大家都在搜 后进行的 保存和跳转 事件
     * @param itemText
     */
    private void clickSearchSaveAndTransact(String itemText) {

        dbTool.insertHistory(itemText);

        // 替换 Fragment, 并为SearchActivity的setTextStr方法设值, 最终是为了实现传值
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        ((SearchActivity)getActivity()).setTextStr(itemText);

        if (getSearchTypeStr.equals(LibraryFragment.INTENT_SEARCH_SIMPLE_TYPE)) {
            // 如果是简单的搜索
            SearchSimpleFragment searchSimpleFragment = new SearchSimpleFragment();
            transaction.replace(R.id.library_search_frame, searchSimpleFragment);
            transaction.commit();
        } else {
            // 对比搜索
            SearchCompareFragment searchCompareFragment = new SearchCompareFragment();
            transaction.replace(R.id.library_search_frame, searchCompareFragment);
            transaction.commit();
        }

    }


}
