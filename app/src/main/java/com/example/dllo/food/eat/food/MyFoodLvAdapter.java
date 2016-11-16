package com.example.dllo.food.eat.food;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.eat.DeliciousFoodBean;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/4.
 *
 * 用于显示 逛吃 中的 美食选项 的数据, 也适用于 知识 数据的显示
 * 共有两种布局
 * type = 1: 只有一张图片的格
 * type = 3: 共有三张图片的格式
 */
public class MyFoodLvAdapter extends BaseAdapter{

    private ArrayList<DeliciousFoodBean.FeedsBean> feedsBeanArrayList;

    public void setFeedsBeanArrayList(ArrayList<DeliciousFoodBean.FeedsBean> feedsBeanArrayList) {
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
    public int getItemViewType(int position) {
        // 共有两种布局
        // type = 0: 只有一张图片的格式
        // type = 1: 共有三张图片的格式
        int imgCount = feedsBeanArrayList.get(position).getImages().size();
        if (imgCount == 1) {
            return 0;
        }

        return 1;
    }

    /** 需要手动复写的 方法, 获取 type 的 个数, 值一定要比 type 的 值大*/
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 获取集合中的数据
        String title = feedsBeanArrayList.get(position).getTitle();
        String source = feedsBeanArrayList.get(position).getSource();
        String tail = feedsBeanArrayList.get(position).getTail();
        ArrayList<String> images = (ArrayList<String>) feedsBeanArrayList.get(position).getImages();

        int type = getItemViewType(position);
        CommonVH commonVH;

        switch (type) {
            case 0:
                commonVH = CommonVH.getViewHolder(convertView, parent, R.layout.item_eat_knowledge1);
                commonVH.setText(R.id.item_knowledge_title, title);
                commonVH.setText(R.id.item_knowledge_source, source);
                commonVH.setText(R.id.item_knowledge_tail, tail);
                commonVH.setImage(R.id.item_knowledge_iv, images.get(0));

                break;

            default:
                commonVH = CommonVH.getViewHolder(convertView, parent, R.layout.item_eat_knowledge2);
                commonVH.setText(R.id.item_knowledge_title, title);
                commonVH.setText(R.id.item_knowledge_source1, source);
                commonVH.setText(R.id.item_knowledge_tail1, tail);
                commonVH.setImage(R.id.item_knowledge_img1, images.get(0));
                commonVH.setImage(R.id.item_knowledge_img2, images.get(1));
                commonVH.setImage(R.id.item_knowledge_img3, images.get(2));
                break;

        }

        return commonVH.getItemView();
    }
}
