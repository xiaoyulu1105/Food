package com.example.dllo.food.library.more;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.food.R;
import com.example.dllo.food.base.CommonVH;
import com.example.dllo.food.beans.FoodMoreBean;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/8.
 *
 * 显示食物百科中详情界面的 数据
 */
public class MyLibraryMoreLvAdapter extends BaseAdapter{

    private ArrayList<FoodMoreBean.FoodsBean> beanArrayList;

    public void setBeanArrayList(ArrayList<FoodMoreBean.FoodsBean> beanArrayList) {
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

        CommonVH commonVH = CommonVH.getViewHolder(convertView, parent, R.layout.item_library_more);

        String imgUrl = beanArrayList.get(position).getThumb_image_url();
        String name = beanArrayList.get(position).getName();
        String calory = beanArrayList.get(position).getCalory();
        int health_light = beanArrayList.get(position).getHealth_light();

        commonVH.setCircleImage(R.id.item_food_more_iv, imgUrl);
        commonVH.setText(R.id.item_food_more_name, name);
        commonVH.setText(R.id.item_food_more_calory, calory);
        switch (health_light){
            case 1:
                commonVH.setImage(R.id.item_food_more_health_light, R.mipmap.ic_food_light_green);
                break;
            case 2:
                commonVH.setImage(R.id.item_food_more_health_light, R.mipmap.ic_food_light_yellow);
                break;
            default:
                commonVH.setImage(R.id.item_food_more_health_light, R.mipmap.ic_food_light_red);
                break;
        }

        return commonVH.getItemView();
    }
}
