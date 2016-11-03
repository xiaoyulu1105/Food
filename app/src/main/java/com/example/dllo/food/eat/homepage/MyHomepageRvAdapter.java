package com.example.dllo.food.eat.homepage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.HomepageBean;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 用于 逛吃选项 的 首页选项的 适配器
 */
public class MyHomepageRvAdapter extends RecyclerView.Adapter{

    private ArrayList<HomepageBean.FeedsBean> feedsBeanArrayList;

    public void setFeedsBeanArrayList(ArrayList<HomepageBean.FeedsBean> feedsBeanArrayList) {
        this.feedsBeanArrayList = feedsBeanArrayList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CommonVH commonVH;
        switch (viewType) {
            case 1:
                 commonVH = CommonVH.getViewHolder(parent, R.layout.item_eat_homepage1);
                break;
            default:
                 commonVH = CommonVH.getViewHolder(parent, R.layout.item_eat_homepage2);
        }

        return commonVH;
    }

    @Override
    public int getItemViewType(int position) {
        String title = feedsBeanArrayList.get(position).getTitle();
        if (title == null) {
            // type 为 1: 表示第一张图片
            // type 为 2: 表示正常的图片和文字结合
            return 1;
        }

        return 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int type = getItemViewType(position);
        CommonVH commonVH = (CommonVH) holder;
        // 获取 图片, 标题, 头像, 发布者
        // 点赞数在数据类里没找到
        String imgUrl = feedsBeanArrayList.get(position).getCard_image();
        String title = feedsBeanArrayList.get(position).getTitle();
        String description = feedsBeanArrayList.get(position).getDescription();

        String iconUrl = feedsBeanArrayList.get(position).getPublisher_avatar();
        String publisher = feedsBeanArrayList.get(position).getPublisher();

        switch (type) {
            case 1:
                commonVH.setImage(R.id.homepage_item_card_image1, imgUrl);

                break;
            default:
                commonVH.setImage(R.id.homepage_item_card_image, imgUrl);
                commonVH.setText(R.id.homepage_item_title, title);
                commonVH.setText(R.id.homepage_item_description, description);

                commonVH.setImage(R.id.homepage_item_publisher_image, iconUrl);
                commonVH.setText(R.id.homepage_item_publisher, publisher);

                break;
        }



    }

    @Override
    public int getItemCount() {
        return feedsBeanArrayList == null ? 0 : feedsBeanArrayList.size();
    }
}
