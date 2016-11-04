package com.example.dllo.food.eat;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.eat.evaluate.EvaluateFragment;
import com.example.dllo.food.eat.food.FoodFragment;
import com.example.dllo.food.eat.homepage.HomePageFragment;
import com.example.dllo.food.eat.knowledge.KnowledgeFragment;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/10/31.
 *
 * 逛吃 选项
 */
public class EatFragment extends BaseFragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentArrayList;
    private ImageButton camera;

    @Override
    protected int getLayout() {
        return R.layout.fragment_eat;
    }

    @Override
    protected void initViews() {
        tabLayout = (TabLayout) getView().findViewById(R.id.eat_tab_layout);
        viewPager = (ViewPager) getView().findViewById(R.id.eat_view_pager);

        camera = bindView(R.id.eat_label_camera);

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomePageFragment());
        fragmentArrayList.add(new EvaluateFragment());
        fragmentArrayList.add(new KnowledgeFragment());
        fragmentArrayList.add(new FoodFragment());

        MyEatVpAdapter adapter = new MyEatVpAdapter(getFragmentManager());
        adapter.setFragmentArrayList(fragmentArrayList);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initData() {

    }
}
