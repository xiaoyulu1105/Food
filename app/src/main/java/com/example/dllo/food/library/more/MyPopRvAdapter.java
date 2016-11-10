package com.example.dllo.food.library.more;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.NutritionalElementBean;
import com.example.dllo.food.tools.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/5.
 *
 * 用于显示 营养素排序 的 RecyclerView 的数据
 * ArrayAdapter, SimpleAdapter 和 BaseAdapter 只适用于 ListView
 *
 * 在 tools 包中存放着自定义 接口, 实现 RecyclerView 中 Item 的点击事件
 * ListView 和 RecyclerView 的区别: 重用机制, 刷新机制 不同
 */
public class MyPopRvAdapter extends RecyclerView.Adapter{

    private ArrayList<NutritionalElementBean.TypesBean> beanArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    private int selectPos = -1; // 默认给-1,  因为第一次进来没有选中 任何营养素

    public void setOnRecyclerViewItemClickListener(
            OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final CommonVH commonVH = (CommonVH) holder;
        String name = beanArrayList.get(position).getName();
        commonVH.setText(R.id.item_pop_nutritional_tv, name);

        commonVH.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClick(position);

                selectPos = position;
                notifyDataSetChanged();
            }
        });

        if(position == selectPos){
            commonVH.setTextColor(R.id.item_pop_nutritional_tv, Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return beanArrayList == null ? 0 : beanArrayList.size();
    }


}
