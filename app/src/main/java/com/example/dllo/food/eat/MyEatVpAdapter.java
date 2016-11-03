package com.example.dllo.food.eat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/3.
 *
 * 用于显示 逛吃 TabLayout 的 适配器
 */
public class MyEatVpAdapter extends FragmentPagerAdapter{

    private String[] titles = new String[]{"首页", "测评", "知识", "美食"};

    private ArrayList<Fragment> fragmentArrayList;

    public MyEatVpAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentArrayList(ArrayList<Fragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
        notifyDataSetChanged();
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
