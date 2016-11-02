package com.example.dllo.food.library;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.LibraryBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/1.
 *
 * 该 适配器 用于显示食物百科 中 GridView 中的数据
 */
public class MyLibraryGvAdapter extends BaseAdapter{

    ArrayList<LibraryBean.GroupBean.CategoriesBean> beanArrayList;

    public void setBeanArrayList(ArrayList<LibraryBean.GroupBean.CategoriesBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beanArrayList == null ? 0 : beanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommonVH commonVH = CommonVH.getViewHolder(
                convertView, parent, R.layout.item_library);

        String imgUrl = beanArrayList.get(position).getImage_url();
        String name = beanArrayList.get(position).getName();

        commonVH.setImage(R.id.item_library_iv, imgUrl);
        commonVH.setText(R.id.item_library_tv, name);

        return commonVH.getItemView();
    }
}
