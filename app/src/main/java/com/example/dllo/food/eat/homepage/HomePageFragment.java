package com.example.dllo.food.eat.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.HomepageBean;
import com.example.dllo.food.tools.OnRecyclerViewItemClickListener;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 这是 狂吃选项 中的 第一项: 首页
 */
public class HomePageFragment extends BaseFragment {


    private RecyclerView recyclerView;
    // 定义静态常量, 作为传值时 的 key 值
    public static final String INTENT_HOMEPAGE_MORE_ICON = "iconUrl";
    public static final String INTENT_HOMEPAGE_MORE_PUBLISHER = "publisher";
    public static final String INTENT_HOMEPAGE_MORE_IMAGE = "imageUrl";
    public static final String INTENT_HOMEPAGE_MORE_LIKE_CT = "likeCt";
    public static final String INTENT_BUNDLE_KEY = "bundle";

    @Override
    protected int getLayout() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initViews() {
        recyclerView = bindView(R.id.eat_homepage_rv);

    }

    @Override
    protected void initData() {
        gsonMethod();
    }

    /** 在该方法中实现 Gson 的相关操作 */
    private void gsonMethod() {
        GsonRequest<HomepageBean> gsonRequest = new GsonRequest<>(
                HomepageBean.class, UrlValues.EAT_HOMEPAGE_URL,
                new Response.Listener<HomepageBean>() {
                    @Override
                    public void onResponse(HomepageBean response) {

                        final ArrayList<HomepageBean.FeedsBean> feedsBeanArrayList =
                                (ArrayList<HomepageBean.FeedsBean>) response.getFeeds();

                        MyHomepageRvAdapter adapter = new MyHomepageRvAdapter();
                        adapter.setFeedsBeanArrayList(feedsBeanArrayList);
                        recyclerView.setAdapter(adapter);

                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(
                                2, LinearLayout.VERTICAL);
                        recyclerView.setLayoutManager(manager);

                        // TODO 接口回调 实现 RecyclerView 的 Item 的点击监听
                        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                setIntentDataAndStart(position, feedsBeanArrayList);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HomePageFragment", "Gson数据请求失败");
            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    /** 为 Intent 添加 数据 并进行跳转*/
    private void setIntentDataAndStart(int position, ArrayList<HomepageBean.FeedsBean> feedsBeanArrayList) {
        if (position == 0) {
            // 当点击的是第一个广告图时, 直接跳转到对应的网页
            Intent intent = new Intent(getActivity(), HomePageMoreFirstActivity.class);
            String link = feedsBeanArrayList.get(position).getLink();
            intent.putExtra("link", link);
            startActivity(intent);

        } else {
            String iconUrl = feedsBeanArrayList.get(position).getPublisher_avatar();
            String publisher = feedsBeanArrayList.get(position).getPublisher();
            String imageUrl = feedsBeanArrayList.get(position).getCard_image();
            int likeCt = feedsBeanArrayList.get(position).getLike_ct();

            Bundle bundle = new Bundle();
            bundle.putString("iconUrl", iconUrl);
            bundle.putString("publisher", publisher);
            bundle.putString("imageUrl", imageUrl);
            bundle.putInt("likeCt", likeCt);

            Intent intent = new Intent(getActivity(), HomePageMoreActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }
}
