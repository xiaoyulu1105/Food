package com.example.dllo.food.my.collection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/14.
 *
 * 收藏里用 ViewPager 显示的 两个Fragment的适配器
 */
public class MyCollectionVPAdapter extends FragmentPagerAdapter{

    ArrayList<Fragment> fragmentArrayList;
    String[]  titles = new String[]{"文章", "食物"};

    public void setFragmentArrayList(ArrayList<Fragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
        notifyDataSetChanged();
    }

    public MyCollectionVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList == null ? 0 : fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
