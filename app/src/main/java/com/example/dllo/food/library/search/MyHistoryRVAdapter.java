package com.example.dllo.food.library.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.tools.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/12.
 *
 * 用于铺建历史记录里的数据
 * 因为 Item 的布局不一样, 所以搜索历史 和 大家都在搜必须用两个不同的 Adapter 铺建
 */
public class MyHistoryRVAdapter extends RecyclerView.Adapter{
    private ArrayList<String> stringArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonVH commonVH = CommonVH.getViewHolder(parent, R.layout.item_library_search_history);
        return commonVH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CommonVH commonVH = (CommonVH) holder;
        String string = stringArrayList.get(position);
        commonVH.setText(R.id.item_library_search_history_tv, string);

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
