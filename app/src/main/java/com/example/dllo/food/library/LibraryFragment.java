package com.example.dllo.food.library;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.LibraryBean;
import com.example.dllo.food.library.more.LibraryMoreActivity;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;


/**
 * Created by XiaoyuLu on 16/10/31.
 *
 * 该Fragment 是 食物百科 选项
 */
public class LibraryFragment extends BaseFragment implements View.OnClickListener {

    private Button btnSearch;
    private Button btnAnalysis;
    private Button btnCompare;
    private Button btnScanCompare;

    private GridView gridViewSort;
    private GridView gridViewBrand;
    private GridView gridViewDrink;

    @Override
    protected int getLayout() {
        return R.layout.fragment_library;
    }

    @Override
    protected void initViews() {
        btnSearch = bindView(R.id.library_btn_search);

        btnAnalysis = bindView(R.id.library_btn_analysis);
        btnCompare = bindView(R.id.library_btn_compare);
        btnScanCompare = bindView(R.id.library_btn_scancompare);

        gridViewSort = bindView(R.id.library_grid1_sort);
        gridViewBrand = bindView(R.id.library_grid2_brand);
        gridViewDrink = bindView(R.id.library_grid3_drink);

    }

    @Override
    protected void initData() {
        setClick(this, btnSearch, btnAnalysis, btnCompare, btnScanCompare);

        gsonMethod();

    }

    /** 使用 Gson 进行的数据请求 在该方法中实现*/
    private void gsonMethod() {
        GsonRequest<LibraryBean> gsonRequest = new GsonRequest<LibraryBean>(
                LibraryBean.class, UrlValues.LIBRARY_URL,
                new Response.Listener<LibraryBean>() {
                    @Override
                    public void onResponse(LibraryBean response) {

                        getSortDataAndShow(response);

                        getBrandDataAndShow(response);

                        getDrinkDataAndShow(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 访问失败
                Log.d("LibraryFragment", "出错啦!");
            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    /** 用于获取 食物百科 连锁餐饮 数据并进行铺建 */
    private void getDrinkDataAndShow(final LibraryBean response) {
        final ArrayList<LibraryBean.GroupBean.CategoriesBean> beanArrayList2 =
                (ArrayList<LibraryBean.GroupBean.CategoriesBean>)
                        response.getGroup().get(2).getCategories();

        MyLibraryGvAdapter adapter = new MyLibraryGvAdapter();
        adapter.setBeanArrayList(beanArrayList2);
        gridViewDrink.setAdapter(adapter);

        gridViewDrink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LibraryBean.GroupBean groupBean = response.getGroup().get(2);

                Intent intent = new Intent(getActivity(), LibraryMoreActivity.class);
                putDataInIntent(intent, groupBean, position);
                startActivity(intent);
            }
        });
    }

    /** 用于获取 食物百科 热门品牌 数据并进行铺建 */
    private void getBrandDataAndShow(final LibraryBean response) {
        final ArrayList<LibraryBean.GroupBean.CategoriesBean> beanArrayList1 =
                (ArrayList<LibraryBean.GroupBean.CategoriesBean>)
                        response.getGroup().get(1).getCategories();

        MyLibraryGvAdapter adapter = new MyLibraryGvAdapter();
        adapter.setBeanArrayList(beanArrayList1);
        gridViewBrand.setAdapter(adapter);

        gridViewBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击进行 页面跳转
                LibraryBean.GroupBean groupBean = response.getGroup().get(1);

                Intent intent = new Intent(getActivity(), LibraryMoreActivity.class);
                putDataInIntent(intent, groupBean, position);
                startActivity(intent);

            }
        });
    }

    /** 用于获取 食物百科 食物分类 数据并进行铺建 */
    private void getSortDataAndShow(final LibraryBean response) {
        // 访问成功, 第一个 GridView 的内容
        final ArrayList<LibraryBean.GroupBean.CategoriesBean> beanArrayList =
                (ArrayList<LibraryBean.GroupBean.CategoriesBean>)
                        response.getGroup().get(0).getCategories();

        MyLibraryGvAdapter adapter = new MyLibraryGvAdapter();
        adapter.setBeanArrayList(beanArrayList);
        gridViewSort.setAdapter(adapter);

        // 数据铺建好后, 就应该进行点击监听了
        gridViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 将groupBean 的 kind ,
                // bean中的 name, id, 以及其内部类的 name的集合, id的集合进行传递
                LibraryBean.GroupBean groupBean = response.getGroup().get(0);

                Intent intent = new Intent(getActivity(), LibraryMoreActivity.class);
                putDataInIntent(intent, groupBean, position);
                startActivity(intent);

            }
        });

    }

    /** CategoriesBean类的数据拆分后通过 Intent 传到下一个 Intent */
    private void putDataInIntent(Intent intent, LibraryBean.GroupBean groupBean, int position) {

        String kind = groupBean.getKind();
        LibraryBean.GroupBean.CategoriesBean bean = groupBean.getCategories().get(position);

        // 两个集合, 分别存放 内部类SubCategoriesBean类中的 name 和 id
        ArrayList<String> subNameArrayList = new ArrayList<>();
        ArrayList<Integer> subIdArrayList = new ArrayList<>();

        for (int i = 0; i < bean.getSub_category_count(); i++) {
            String subName = bean.getSub_categories().get(i).getName();
            int subId = bean.getSub_categories().get(i).getId();

            subNameArrayList.add(subName);
            subIdArrayList.add(subId);
        }

        String name = bean.getName();
        int id = bean.getId();

        // 将groupBean 的 kind , bean中的 name, id, 以及其内部类的 name的集合, id的集合进行传递
        intent.putExtra("kind", kind);
        intent.putExtra("name", name);
        intent.putExtra("id", id);
        intent.putExtra("subNameArrayList", subNameArrayList);
        intent.putExtra("subIdArrayList", subIdArrayList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.library_btn_search:
                // 搜索 
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);

                break;
            case R.id.library_btn_analysis:
                // 饮食分析
                Toast.makeText(getActivity(), "饮食分析完成中...", Toast.LENGTH_SHORT).show();
                Log.d("LibraryFragment", "饮食分析完成中...");

                break;
            case R.id.library_btn_compare:
                // 搜索对比
                Intent intent2 = new Intent(getActivity(), CompareActivity.class);
                startActivity(intent2);

                break;
            case R.id.library_btn_scancompare:
                // 扫码对比
                Toast.makeText(getActivity(), "扫码对比完成中...", Toast.LENGTH_SHORT).show();
                Log.d("LibraryFragment", "扫码对比完成中...");

                break;
            default:
                Log.d("LibraryFragment", "出错啦!");
                break;
        }
    }

}
