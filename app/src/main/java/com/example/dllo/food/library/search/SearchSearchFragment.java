package com.example.dllo.food.library.search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.TextEvent;
import com.example.dllo.food.beans.library.EveryoneSearchBean;
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
 *
 * 跳转到搜索Activity时 进行搜索的 第一个Fragment
 */
public class SearchSearchFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout historyLl;  // 最近搜过的线性布局
    private ListView historyListView; // 最近搜过的数据显示
    private Button deleteHistoryBtn; // 删除历史记录
    private RecyclerView recyclerView; // 大家都在 的数据

    private ArrayList<String> historyArrayList; // 搜索历史的数据集合

    @Override
    protected int getLayout() {
        return R.layout.fragment_library_search_search;
    }

    @Override
    protected void initViews() {
        historyLl = (LinearLayout) getView().findViewById(R.id.library_search_history_ll);
        historyListView = bindView(R.id.library_search_history_list);
        deleteHistoryBtn = bindView(R.id.library_search_history_delete);
        recyclerView = bindView(R.id.library_search_search_recycler);

        setClick(this, deleteHistoryBtn);


        // 注册 EventBus 订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

        everyoneSearchGson();
        historyArrayList = new ArrayList<>();

        if (historyArrayList.size() <= 0) {
            historyLl.setVisibility(View.GONE);
        } else {

            historyLl.setVisibility(View.VISIBLE);

            String[] array = new String[historyArrayList.size()];
            historyArrayList.toArray(array);

            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                    R.layout.item_library_search_history,
                    R.id.item_library_search_history_tv, array);
            historyListView.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    /** 第二步, 注册订阅者, 实现 订阅者的方法 */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public TextEvent getTextEvent(TextEvent textEvent) {
        String getText = textEvent.getText();
        historyArrayList.add(0, getText);

        historyLl.setVisibility(View.VISIBLE);

        String[] array = new String[historyArrayList.size()];
        historyArrayList.toArray(array);


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                R.layout.item_library_search_history,
                R.id.item_library_search_history_tv, array);
        historyListView.setAdapter(arrayAdapter);

        return textEvent;
    }

    /** 通过Gson 请求 大家都在搜 的数据 */
    private void everyoneSearchGson() {
        GsonRequest<EveryoneSearchBean> gsonRequest = new GsonRequest<>(
                EveryoneSearchBean.class, UrlValues.LIBRARY_SEARCH_EVERYONE_URL,
                new Response.Listener<EveryoneSearchBean>() {
                    @Override
                    public void onResponse(EveryoneSearchBean response) {
                        ArrayList<String> stringArrayList  = (ArrayList<String>) response.getKeywords();

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        recyclerView.setLayoutManager(manager);

                        MyEveryoneSearchRVAdapter adapter = new MyEveryoneSearchRVAdapter();
                        adapter.setStringArrayList(stringArrayList);
                        recyclerView.setAdapter(adapter);

                        // 设置分隔线
                        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                                LinearLayoutManager.VERTICAL));

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

    /** recyclerView 的 Item 的点击事件 */
    private void recyclerViewItemClickMethod(MyEveryoneSearchRVAdapter adapter, final ArrayList<String> stringArrayList) {
        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String itemStr = stringArrayList.get(position);
                // EventBus 发布事件
                EventBus.getDefault().post(new TextEvent(itemStr));

                // TODO 跳转 将字符串转换成 UTF_8 形成新接口,
                // 实现第二个 Fragment 的跳转, 再将点击的 内容通过 EventBus 传递到搜索的EDT

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.library_search_history_delete:
                Toast.makeText(getActivity(), "清空历史记录", Toast.LENGTH_SHORT).show();

                // TODO 清空记录集合, 调用 判断到集合为空时最近搜过不显示(gone)
        }
    }
}
