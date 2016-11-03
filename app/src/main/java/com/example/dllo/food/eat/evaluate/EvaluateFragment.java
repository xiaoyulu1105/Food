package com.example.dllo.food.eat.evaluate;

import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.EvaluateBean;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 这是 狂吃选项中的 测评
 */
public class EvaluateFragment extends BaseFragment {

    private ListView listView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_ealuate;
    }

    @Override
    protected void initViews() {
        listView = bindView(R.id.eat_evaluate_list);
    }

    @Override
    protected void initData() {

        GsonRequest<EvaluateBean> gsonRequest = new GsonRequest<EvaluateBean>(
                EvaluateBean.class, UrlValues.EAT_EVALUATE_URL,
                new Response.Listener<EvaluateBean>() {
                    @Override
                    public void onResponse(EvaluateBean response) {
                        ArrayList<EvaluateBean.FeedsBean> feedsBeanArrayList =
                                (ArrayList<EvaluateBean.FeedsBean>) response.getFeeds();

                        MyEvaluateLvAdapter adapter = new MyEvaluateLvAdapter();
                        adapter.setFeedsBeanArrayList(feedsBeanArrayList);
                        listView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 网络请求失败
                Log.d("EvaluateFragment", "请求失败啦!");
            }
        }

        );
        VolleySingleton.getInstance().addRequest(gsonRequest);

    }
}
