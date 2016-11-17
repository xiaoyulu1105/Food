package com.example.dllo.food.my.collection;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.dbtools.CollectionSqlData;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/14.
 *
 * 显示收藏中文章的数据
 */
public class MyCollectionArticleLVAdapter extends BaseAdapter{

    private ArrayList<CollectionSqlData> arrayList;

    public void setArrayList(ArrayList<CollectionSqlData> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommonVH commonVH = CommonVH.getViewHolder(convertView, parent, R.layout.item_collection_artical);
        String title = arrayList.get(position).getTitle();
        commonVH.setText(R.id.item_collection_article_tv, title);

        return commonVH.getItemView();
    }
}
