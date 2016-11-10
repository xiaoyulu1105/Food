package com.example.dllo.food.eat.evaluate;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
 * 这是 狂吃选项中的 第二项: 测评
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

        gsonMethod();

    }

    /** 用Gson 进行数据请求的 代码 */
    private void gsonMethod() {
        GsonRequest<EvaluateBean> gsonRequest = new GsonRequest<>(
                EvaluateBean.class, UrlValues.EAT_EVALUATE_URL,
                new Response.Listener<EvaluateBean>() {
                    @Override
                    public void onResponse(final EvaluateBean response) {

                        ArrayList<EvaluateBean.FeedsBean> feedsBeanArrayList =
                                (ArrayList<EvaluateBean.FeedsBean>) response.getFeeds();

                        MyEvaluateLvAdapter adapter = new MyEvaluateLvAdapter();
                        adapter.setFeedsBeanArrayList(feedsBeanArrayList);
                        listView.setAdapter(adapter);

                        //  将请求的数据 中的link 传送到下一个Activity 实现网页的显示
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String link = response.getFeeds().get(position).getLink();
                                Intent intent = new Intent(getActivity(), EvaluateMoreActivity.class);
                                intent.putExtra("link", link);
                                startActivity(intent);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EvaluateFragment", "请求失败啦!");
            }
        }
        );
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
