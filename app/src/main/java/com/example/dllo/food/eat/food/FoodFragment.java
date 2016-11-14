package com.example.dllo.food.eat.food;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.DeliciousFoodBean;
import com.example.dllo.food.eat.evaluate.EvaluateMoreActivity;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 这是 逛吃选项 中的 第四项: 美食
 */
public class FoodFragment extends BaseFragment {

    private ListView listView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initViews() {
        listView = bindView(R.id.eat_knowledge_list);
    }

    @Override
    protected void initData() {

        gsonMethod();

    }

    /** 用Gson 进行数据请求的 代码 */
    private void gsonMethod() {
        GsonRequest<DeliciousFoodBean> gsonRequest = new GsonRequest<>(
                DeliciousFoodBean.class, UrlValues.EAT_BEAUTY_URL,
                new Response.Listener<DeliciousFoodBean>() {
                    @Override
                    public void onResponse(final DeliciousFoodBean response) {

                        ArrayList<DeliciousFoodBean.FeedsBean> feedsBeanArrayList =
                                (ArrayList<DeliciousFoodBean.FeedsBean>) response.getFeeds();

                        MyFoodLvAdapter adapter = new MyFoodLvAdapter();
                        adapter.setFeedsBeanArrayList(feedsBeanArrayList);
                        listView.setAdapter(adapter);

                        //  将请求的数据 中的link 传送到下一个Activity 实现网页的显示
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String link = response.getFeeds().get(position).getLink();
                                String title = response.getFeeds().get(position).getTitle();
                                Intent intent = new Intent(getActivity(), EvaluateMoreActivity.class);
                                intent.putExtra("link", link);
                                intent.putExtra("title", title);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FoodFragment", "请求失败啦");
            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
