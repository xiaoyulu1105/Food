package com.example.dllo.food.eat.homepage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.HomepageBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        Bitmap bitmap = null;
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

                commonVH.setText(R.id.homepage_item_publisher, publisher);
//                commonVH.setImage(R.id.homepage_item_publisher_image, iconUrl);
                commonVH.setCircleImage(R.id.homepage_item_publisher_image, iconUrl);

                // 图片需要圆形显示, 已知图片网址
                // 实现方法: 先用 Http 获得图片的输入流,
                // 再用 Bitmap工厂对流进行解码, 最后返回Bitmap

                break;
        }

    }

    @Override
    public int getItemCount() {
        return feedsBeanArrayList == null ? 0 : feedsBeanArrayList.size();
    }
}
