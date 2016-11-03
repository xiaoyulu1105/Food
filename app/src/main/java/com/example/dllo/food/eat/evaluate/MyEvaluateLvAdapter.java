package com.example.dllo.food.eat.evaluate;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.EvaluateBean;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 逛吃选项 里的 测评 的ListView的适配器
 */
public class MyEvaluateLvAdapter extends BaseAdapter{

    ArrayList<EvaluateBean.FeedsBean> feedsBeanArrayList;

    public void setFeedsBeanArrayList(ArrayList<EvaluateBean.FeedsBean> feedsBeanArrayList) {
        this.feedsBeanArrayList = feedsBeanArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return feedsBeanArrayList == null ? 0 : feedsBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedsBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommonVH commonVH = CommonVH.getViewHolder(
                convertView, parent, R.layout.item_eat_evaluate);

        String img = feedsBeanArrayList.get(position).getBackground();
        String source = feedsBeanArrayList.get(position).getSource();
        String title = feedsBeanArrayList.get(position).getTitle();
        String tail = feedsBeanArrayList.get(position).getTail();

        commonVH.setImage(R.id.item_evaluate_img, img);
        commonVH.setText(R.id.item_evaluate_source, source);
        commonVH.setText(R.id.item_evaluate_title, title);
        commonVH.setText(R.id.item_evaluate_tail, tail);

        return commonVH.getItemView();
    }
}
