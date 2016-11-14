package com.example.dllo.food.library.search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.beans.library.FoodMoreBean;
import com.example.dllo.food.beans.library.LibrarySearchBean;
import com.example.dllo.food.beans.library.NutritionalElementBean;
import com.example.dllo.food.library.more.MyLibraryMoreLvAdapter;
import com.example.dllo.food.library.more.MyPopRvAdapter;
import com.example.dllo.food.tools.DividerItemDecoration;
import com.example.dllo.food.tools.OnRecyclerViewItemClickListener;
import com.example.dllo.food.tools.UTF8Util;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/12.
 *
 * 简单搜索后 出来的 界面
 *
 * 获取 Activity 的值: http://dwtedx.com/itshare_296.html
 */
public class SearchSimpleFragment extends BaseFragment implements View.OnClickListener{

    private TextView nutritionTV;
    private ImageView nutritionIV;
    private Button orderBtn;
    private ImageView orderIV;
    private ListView listView; // 显示查询数据

    private PopupWindow popupWindowNutrition;
    private RecyclerView popRecycler;

    private String getTextStr;  // SearchActivity 的输入框的搜索内容
    private String getUTF8Str; // 存放关键字的UTF-8格式
    private String url; // 存放最后拼接完成的链接

    public static final String SEARCH_HEAD_URL1 = "http://food.boohee.com/fb/v1/search?page=1&order_asc=desc&q=红薯";
    public static final String SEARCH_HEAD_URL = "http://food.boohee.com/fb/v1/search?";
    public static final String PART1PAGE = "page=";
    public static final String PART2ORDER = "&order_asc=";
    public static final String PART3Q = "&q=";

    public static final String ORDER_ASC_TEXT = "由低到高";  // 升序
    public static final String ORDER_DEC_TEXT = "由高到低";  // 降序
    public static final int ORDER_ASC_IMAGE = R.mipmap.ic_food_ordering_up; // 上升箭头, 低到高
    public static final int ORDER_DEC_IMAGE = R.mipmap.ic_food_ordering_down; // 下降箭头, 高到低

    private String page; // 用于加载时改变其值
    private String order; // 存放两种排序的值

    @Override
    protected int getLayout() {
        return R.layout.fragment_library_search_simple;
    }

    @Override
    protected void initViews() {

        nutritionTV = bindView(R.id.library_search_simple_nutrition_tv);
        nutritionIV = bindView(R.id.library_search_simple_nutrition_iv);
        orderBtn = bindView(R.id.library_search_simple_order_btn);
        orderIV = bindView(R.id.library_search_simple_order_iv);
        listView = bindView(R.id.library_search_simple_list);

        setClick(this, nutritionTV, nutritionIV, orderBtn, orderIV);

    }

    @Override
    protected void initData() {
        getTextStr = ((SearchActivity)getActivity()).getTextStr();
        getUTF8Str = UTF8Util.stringToUTF8(getTextStr);

        initNutritionPopupWindowMethod();

        url = SEARCH_HEAD_URL + PART1PAGE + 1 + PART2ORDER + "desc" + PART3Q + getUTF8Str;
        Log.d("SearchSimpleFragment", url);


        showData(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.library_search_simple_nutrition_tv:
            case R.id.library_search_simple_nutrition_iv:
                // 显示 popupWindow
                if (!popupWindowNutrition.isShowing()) {
                    popupWindowNutrition.showAsDropDown(nutritionTV);
                } else {
                    popupWindowNutrition.dismiss();
                }

                break;
            case R.id.library_search_simple_order_btn:
            case R.id.library_search_simple_order_iv:
                // TODO 实现营养素的高低排序
                clickOrderMethod();

                break;
            default:
                Log.d("SearchSimpleFragment", "点击出错啦!");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 关闭营养素 pop
        if (popupWindowNutrition != null && popupWindowNutrition.isShowing()) {
            popupWindowNutrition.dismiss();
        }
    }

    /**
     * 点击 营养素排序 后 显示的界面
     */
    private void initNutritionPopupWindowMethod() {
        popupWindowNutrition = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_library_more_nutritional, null);
        popRecycler = (RecyclerView) view.findViewById(R.id.pop_library_more_nutritional_recycler);

        // 网络请求 营养素排序 数据
        // 并用 接口回调 实现 Recycler的Item 的点击事件
        nutritionalGsonMethod();

        popupWindowNutrition.setContentView(view);
    }

    /**
     * popUpWindow(营养素排序) 的数据的请求
     */
    private void nutritionalGsonMethod() {
        GsonRequest<NutritionalElementBean> gsonRequest = new GsonRequest<>(
                NutritionalElementBean.class, UrlValues.LIBRARY_NUTRITION_URL,
                new Response.Listener<NutritionalElementBean>() {
                    @Override
                    public void onResponse(NutritionalElementBean response) {

                        final ArrayList<NutritionalElementBean.TypesBean> beanArrayList =
                                (ArrayList<NutritionalElementBean.TypesBean>) response.getTypes();

                        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
                        popRecycler.setLayoutManager(manager);

                        MyPopRvAdapter adapter = new MyPopRvAdapter();
                        adapter.setBeanArrayList(beanArrayList);
                        popRecycler.setAdapter(adapter);

                        // 接口回调实现 RecyclerView 中的 Item 的点击监听
                        popRecyclerItemClickMethod(adapter, beanArrayList);

                        // 添加分割线
                        popRecycler.addItemDecoration(new DividerItemDecoration(
                                mContext, LinearLayoutManager.VERTICAL));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LibraryMoreActivity", "请求失败");
            }
        }
        );
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    /**
     * 点击 营养素排序 的弹窗中的 Item 的点击事件
     */
    private void popRecyclerItemClickMethod(MyPopRvAdapter adapter, final ArrayList<NutritionalElementBean.TypesBean> beanArrayList) {
        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {

                orderBtn.setVisibility(View.VISIBLE);
                orderIV.setVisibility(View.VISIBLE);
                nutritionTV.setText(beanArrayList.get(position).getName());

                popupWindowNutrition.dismiss();
            }
        });
    }

    /**
     * 点击由高到低 或者 由低到高 后的排序事件
     */
    private void clickOrderMethod() {
        //  判断当前Text, 将文字和图片变换,
        if (orderBtn.getText().equals(ORDER_DEC_TEXT)) {

            // 当文字是由高到低时, 将文字和图片变换
            orderBtn.setText(ORDER_ASC_TEXT);
            orderIV.setImageResource(ORDER_ASC_IMAGE);

        } else {
            orderBtn.setText(ORDER_DEC_TEXT);
            orderIV.setImageResource(ORDER_DEC_IMAGE);
        }
    }


    /**
     * 传入url 进行网络请求 获取数据, 并进行铺建
     */
    private void showData(String url) {

        GsonRequest<LibrarySearchBean> gsonRequest = new GsonRequest<>(
                LibrarySearchBean.class, url, new Response.Listener<LibrarySearchBean>() {
            @Override
            public void onResponse(LibrarySearchBean response) {
                ArrayList<LibrarySearchBean.ItemsBean> beanArrayList =
                        (ArrayList<LibrarySearchBean.ItemsBean>) response.getItems();

                MySearchShowLvAdapter adapter = new MySearchShowLvAdapter();
                adapter.setBeanArrayList(beanArrayList);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SearchSimpleFragment", error.getMessage());
                Log.d("SearchSimpleFragment", "Gson数据请求失败!");
            }
        }
        );
        VolleySingleton.getInstance().addRequest(gsonRequest);

    }
}
