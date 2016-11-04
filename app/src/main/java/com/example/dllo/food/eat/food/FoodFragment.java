package com.example.dllo.food.eat.food;

import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.FoodBean;
import com.example.dllo.food.eat.knowledge.MyKnowLedgeLvAdapter;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 这是 逛吃选项 中的 美食选项
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

        GsonRequest<FoodBean> gsonRequest = new GsonRequest<FoodBean>(
                FoodBean.class, UrlValues.EAT_BEAUTY_URL,
                new Response.Listener<FoodBean>() {
                    @Override
                    public void onResponse(FoodBean response) {
                        ArrayList<FoodBean.FeedsBean> feedsBeanArrayList =
                                (ArrayList<FoodBean.FeedsBean>) response.getFeeds();

                        MyFoodLvAdapter adapter = new MyFoodLvAdapter();
                        adapter.setFeedsBeanArrayList(feedsBeanArrayList);
                        listView.setAdapter(adapter);

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
