package com.example.dllo.food.library.more;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.NutritionalElementBean;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/5.
 *
 * 用于显示 营养素排序 的 RecyclerView 的数据
 * ArrayAdapter, SimpleAdapter 和 BaseAdapter 只适用于 ListView
 */
public class MyPopRvAdapter extends RecyclerView.Adapter{

    ArrayList<NutritionalElementBean.TypesBean> beanArrayList;

    public void setBeanArrayList(ArrayList<NutritionalElementBean.TypesBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonVH commonVH = CommonVH.getViewHolder(parent, R.layout.item_pop_nutritional);

        return commonVH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CommonVH commonVH = (CommonVH) holder;
        String name = beanArrayList.get(position).getName();
        commonVH.setText(R.id.item_pop_nutritional_tv, name);
    }

    @Override
    public int getItemCount() {
        return beanArrayList == null ? 0 : beanArrayList.size();
    }
}
