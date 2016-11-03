package com.example.dllo.food.eat.knowledge;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 用于显示 逛吃 中的 知识 的数据
 * 共有两种布局
 * type = 1: 只有一张图片的格
 * type = 3: 共有三张图片的格式
 */
public class MyKnowLedgeLvAdapter extends BaseAdapter{

    ArrayList<KnowledgeBean.FeedsBean> feedsBeanArrayList;

    public void setFeedsBeanArrayList(ArrayList<KnowledgeBean.FeedsBean> feedsBeanArrayList) {
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
        // type = 1: 只有一张图片的格式
        // type = 3: 共有三张图片的格式
        int imgCount = feedsBeanArrayList.get(position).getImages().size();
        if (imgCount == 1) {
            return 1;
        }

        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 获取集合中的数据
        String title = feedsBeanArrayList.get(position).getTitle();
        String source = feedsBeanArrayList.get(position).getSource();
        String tail = feedsBeanArrayList.get(position).getTail();
        ArrayList<String> images = (ArrayList<String>) feedsBeanArrayList.get(position).getImages();

        int type = getItemViewType(position);
        Log.d("MyKnowLedgeLvAdapter", "type:" + type);
        CommonVH commonVH;

        switch (type) {
            case 1:
                commonVH = CommonVH.getViewHolder(convertView, parent, R.layout.item_eat_knowledge1);
                commonVH.setText(R.id.item_knowledge_title, title);
                commonVH.setText(R.id.item_knowledge_source, source);
                commonVH.setText(R.id.item_knowledge_tail, tail);
                commonVH.setImage(R.id.item_knowledge_iv, images.get(0));

                break;

            default:
                commonVH = CommonVH.getViewHolder(convertView, parent, R.layout.item_eat_knowledge2);
                commonVH.setText(R.id.item_knowledge_title2, title);
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
