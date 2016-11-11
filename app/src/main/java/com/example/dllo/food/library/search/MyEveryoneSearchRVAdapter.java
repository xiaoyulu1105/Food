package com.example.dllo.food.library.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.tools.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/11.
 *
 * 用于显示搜索中的 大家都在搜 的RecyclerView 中的 String 数据
 */
public class MyEveryoneSearchRVAdapter extends RecyclerView.Adapter{

    private ArrayList<String> stringArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 用了 营养素 中Item的布局
        CommonVH commonVH = CommonVH.getViewHolder(parent, R.layout.item_pop_nutritional);
        return commonVH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CommonVH commonVH = (CommonVH) holder;
        commonVH.setText(R.id.item_pop_nutritional_tv, stringArrayList.get(position));

        commonVH.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList == null ? 0 : stringArrayList.size();
    }
}
