package com.example.dllo.food.eat.homepage;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.HomepageBean;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 这是 狂吃选项 中的 首页选项
 */
public class HomePageFragment extends BaseFragment {


    private RecyclerView recyclerView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initViews() {
        recyclerView = bindView(R.id.eat_homepage_rv);

        GsonRequest<HomepageBean> gsonRequest = new GsonRequest<HomepageBean>(
                HomepageBean.class, UrlValues.EAT_HOMEPAGE_URL,
                new Response.Listener<HomepageBean>() {
                    @Override
                    public void onResponse(HomepageBean response) {
                        // 网络请求成功
                        Log.d("HomePageFragment", "response:" + response);
                        ArrayList<HomepageBean.FeedsBean> feedsBeanArrayList =
                                (ArrayList<HomepageBean.FeedsBean>) response.getFeeds();

                        MyHomepageRvAdapter adapter = new MyHomepageRvAdapter();
                        adapter.setFeedsBeanArrayList(feedsBeanArrayList);
                        recyclerView.setAdapter(adapter);

//                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(
                                2, LinearLayout.VERTICAL);
                        recyclerView.setLayoutManager(manager);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 请求失败
            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);

    }

    @Override
    protected void initData() {

    }
}
