package com.example.dllo.food.library.more;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;
import com.example.dllo.food.beans.FoodBean;
import com.example.dllo.food.beans.FoodMoreBean;
import com.example.dllo.food.beans.LibraryBean;
import com.example.dllo.food.beans.NutritionalElementBean;
import com.example.dllo.food.tools.DividerItemDecoration;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/5.
 * <p>
 * 点击 食物百科 选项中的 GridView item 后跳转到 该界面
 */
public class LibraryMoreActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    public static final String FOOD_MORE_URL_HEAD = "http://food.boohee.com/fb/v1/foods?kind=";
    public static final String FOOD_MORE_URL_CENTER = "&value=";
    public static final String FOOD_MORE_URL_TAIL = "&order_by=1&page=1&order_asc=0";

    private LinearLayout returnLl; // 返回
    private ImageButton returnImgBtn; // 返回的小图标
    private TextView title;
    private Button allBtn;         // 全部

    private LinearLayout nutritionalLl; // 营养素排序
    private ImageButton nutritionalImgBtn;  // 营养素排序小图标
    private ListView listView;    // 显示数据
    private PopupWindow popupWindowAll; // 点击全部 出现的弹窗
    private PopupWindow popupWindowNutrition; // 点击排序 出现的弹窗

    private RecyclerView popRecycler; // 营养素弹窗中的控件
    private ListView popList;  // 全部弹窗中的控件

    private LibraryBean.GroupBean.CategoriesBean categoriesBean; // 用来接收 从 LibraryFragment类 传过来的数据类对象

    private String getName; // 传递来的 name
    private int getId;       // 传递来的 id
    private ArrayList<String> subNameArrayList;  // 传递来的 全部中显示的 内部类 中的 name
    private ArrayList<Integer> subIdArrayList;   // 传递来的 内部类的 id

    @Override
    protected int getLayout() {
        return R.layout.activity_library_more;
    }

    @Override
    protected void initViews() {
        returnLl = (LinearLayout) findViewById(R.id.library_more_return_ll);
        returnImgBtn = (ImageButton) findViewById(R.id.library_more_return);

        title = bindView(R.id.library_more_title);
        allBtn = bindView(R.id.library_more_all);
        nutritionalLl = (LinearLayout) findViewById(R.id.library_more_nutritional_element_ll);
        nutritionalImgBtn = bindView(R.id.library_more_nutritional_element_ib);

        listView = bindView(R.id.library_more_list);

        setClick(this, returnLl, allBtn, nutritionalLl, nutritionalImgBtn);
        setClick(this, returnImgBtn);
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();

        String getKind = intent.getStringExtra("kind");
        getName = intent.getStringExtra("name");
        getId = intent.getIntExtra("id", 0);
        subNameArrayList = intent.getStringArrayListExtra("subNameArrayList");
        subNameArrayList.add(0, "全部");
        subIdArrayList = intent.getIntegerArrayListExtra("subIdArrayList");

        title.setText(getName);

        String url = FOOD_MORE_URL_HEAD + getKind +
                FOOD_MORE_URL_CENTER + getId + FOOD_MORE_URL_TAIL;

        GsonRequest<FoodMoreBean> gsonRequest = new GsonRequest<FoodMoreBean>(
                FoodMoreBean.class, )
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 注意逻辑运算 &&
        if (popupWindowAll != null && popupWindowAll.isShowing()){
            popupWindowAll.dismiss();
        }
        if (popupWindowNutrition != null && popupWindowNutrition.isShowing()) {
            popupWindowNutrition.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.library_more_return_ll:
            case R.id.library_more_return:
                finish();

                break;
            case R.id.library_more_all:
                clickAllMethod();

                break;
            case R.id.library_more_nutritional_element_ll:
            case R.id.library_more_nutritional_element_ib:
                clickNutritionMethod();

                break;
            default:
                Toast.makeText(this, "出错啦!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /** 点击全部 后 调用的方法 */
    private void clickAllMethod() {
        // 点击 全部, 显示 pop
        if (popupWindowAll == null || !popupWindowAll.isShowing()) {
            intiPopupWindowFirstMethod();
        } else {
            popupWindowAll.dismiss();
        }
        // 将营养素排序的弹窗关闭
        if (popupWindowNutrition != null && popupWindowNutrition.isShowing()) {
            popupWindowNutrition.dismiss();
        }
    }

    /** 点击营养素 后 调用的方法 */
    private void clickNutritionMethod() {
        // 营养素排序, 显示 pop
        if (popupWindowNutrition == null || !popupWindowNutrition.isShowing()) {
            intiPopupWindowSecondMethod();
        } else {
            popupWindowNutrition.dismiss();
        }
        // 将全部的弹窗关闭
        if (popupWindowAll != null && popupWindowAll.isShowing())
        {
            popupWindowAll.dismiss();
        }
    }

    /**
     * 点击 营养素排序 后 显示的界面
     */
    private void intiPopupWindowSecondMethod() {
        popupWindowNutrition = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        View view = LayoutInflater.from(this).inflate(R.layout.pop_library_more_nutritional, null);
        popRecycler = (RecyclerView) view.findViewById(R.id.pop_library_more_nutritional_recycler);
        nutritionalGsonMethod();
        // TODO popRecycler 的Item的点击事件

        popupWindowNutrition.setContentView(view);
        popupWindowNutrition.showAsDropDown(nutritionalLl);
    }

    /** 点击 全部 后 显示的界面 */
    private void intiPopupWindowFirstMethod() {
        popupWindowAll = new PopupWindow(
                200,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_library_more_all, null);
        popList = (ListView) view.findViewById(R.id.pop_library_more_all_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                LibraryMoreActivity.this,
                R.layout.item_pop_all, R.id.item_pop_all_tv, subNameArrayList);
        popList.setAdapter(arrayAdapter);

        popList.setOnItemClickListener(this);

        popupWindowAll.setContentView(view);
        popupWindowAll.showAsDropDown(allBtn);
    }

    /** 第二个pop(营养素排序) 的数据的请求 */
    private void nutritionalGsonMethod() {

        GsonRequest<NutritionalElementBean> gsonRequest = new GsonRequest<>(
                NutritionalElementBean.class, UrlValues.LIBRARY_NATRITIONALELEMENT_URL,
                new Response.Listener<NutritionalElementBean>() {
                    @Override
                    public void onResponse(NutritionalElementBean response) {

                        Log.d("LibraryMoreActivity", "response:" + response);

                        ArrayList<NutritionalElementBean.TypesBean> beanArrayList =
                                (ArrayList<NutritionalElementBean.TypesBean>) response.getTypes();

                        MyPopRvAdapter adapter = new MyPopRvAdapter();
                        adapter.setBeanArrayList(beanArrayList);
                        popRecycler.setAdapter(adapter);

                        GridLayoutManager manager = new GridLayoutManager(LibraryMoreActivity.this, 3);
                        popRecycler.setLayoutManager(manager);

                        popRecycler.addItemDecoration(new DividerItemDecoration(
                                LibraryMoreActivity.this, LinearLayoutManager.VERTICAL));

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

    /** 复写的监听方法, 点击全部的 ListView 中的 Item */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, subNameArrayList.get(position), Toast.LENGTH_SHORT).show();

    }
}
