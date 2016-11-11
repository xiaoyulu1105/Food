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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;
import com.example.dllo.food.beans.library.FoodMoreBean;
import com.example.dllo.food.beans.library.LibraryBean;
import com.example.dllo.food.beans.library.NutritionalElementBean;
import com.example.dllo.food.tools.DividerItemDecoration;
import com.example.dllo.food.tools.OnRecyclerViewItemClickListener;
import com.example.dllo.food.values.UrlValues;
import com.example.dllo.food.volley.GsonRequest;
import com.example.dllo.food.volley.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/5.
 * <p>
 * 点击 食物百科 选项中的 GridView item 后跳转到 该界面
 */
public class LibraryMoreActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout returnLl; // 返回
    private ImageButton returnImgBtn; // 返回的小图标
    private TextView title;
    private Button allBtn;         // 全部

    private TextView nutritionalTV; // 营养素排序
    private ImageButton nutritionalImgBtn;  // 营养素排序小图标
    private Button orderBtn; // 营养素排序方式
    private ImageView orderIV;  //
    private ListView listView;    // 显示数据

    private PopupWindow popupWindowAll; // 点击全部 出现的弹窗
    private PopupWindow popupWindowNutrition; // 点击排序 出现的弹窗

    private RecyclerView popRecycler; // 营养素弹窗中的控件
    private ListView popList;  // 全部弹窗中的控件

    private LibraryBean.GroupBean.CategoriesBean categoriesBean; // 用来接收 从 LibraryFragment类 传过来的数据类对象

    private String getKind;  // 传递来的 kind
    private String getName; // 传递来的 name
    private int getId;       // 传递来的 id
    private ArrayList<String> subNameArrayList;  // 传递来的 全部中显示的 内部类 中的 name
    private ArrayList<Integer> subIdArrayList;   // 传递来的 内部类的 id

    private int subValue;  // 从 上一个界面传递过来的 sub_value(subId) 的值(从1开始的, 13(包装谷薯))
    private String orderIndex = "1";  // 网络请求 获得的order_by 的 值(全部: 1, 热量: 2)
    private int orderAsc = 0;  // 由低到高为1, 高到低为0. 默认为高到低

    private String part1Kind = UrlValues.FOOD_MORE_PART1_KIND;
    private String part2Value = UrlValues.FOOD_MORE_PART2_VALUE;
    private String part3SubValue = UrlValues.FOOD_MORE_PART3_SUB_VALUE;
    private String part4OrderBy = UrlValues.FOOD_MORE_PART4_ORDER_BY;
    private String part5Page = UrlValues.FOOD_MORE_PART5_PAGE;
    private String part6OrderAsc = UrlValues.FOOD_MORE_PART6_ORDER_ASC;
    private String partTail = UrlValues.FOOD_MORE_PART_TAIL;

    public static final String ORDER_ASC_TEXT = "由低到高";  // 升序
    public static final String ORDER_DEC_TEXT = "由高到低";  // 降序
    public static final int ORDER_ASC_IMAGE = R.mipmap.ic_food_ordering_up; // 上升箭头, 低到高
    public static final int ORDER_DEC_IMAGE = R.mipmap.ic_food_ordering_down; // 下降箭头, 高到低
    public static final int ORDER_ASC_INT = 1; // 低到高
    public static final int ORDER_DEC_INT = 0; // 高到低
    private String url;  // 进行数据请求的链接

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

        nutritionalTV = bindView(R.id.library_more_nutritional_element_tv);
        nutritionalImgBtn = bindView(R.id.library_more_nutritional_element_ib);
        orderBtn = bindView(R.id.library_more_nutritional_element_order_btn);
        orderIV = bindView(R.id.library_more_nutritional_element_order_iv);

        listView = bindView(R.id.library_more_list);

        setClick(this, returnLl, allBtn, nutritionalTV, nutritionalImgBtn, orderBtn);
        setClick(this, returnImgBtn);
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();

        getKind = intent.getStringExtra("kind");
        getName = intent.getStringExtra("name");
        getId = intent.getIntExtra("id", 0);
        subNameArrayList = intent.getStringArrayListExtra("subNameArrayList");
        subNameArrayList.add(0, "全部");
        subIdArrayList = intent.getIntegerArrayListExtra("subIdArrayList");

        title.setText(getName);

        // 显示第一次跳转的数据
        String url = part1Kind + getKind + part2Value + getId + partTail;
        showData(url);

        // popUpWindow 只需要初始化一次, 点击之后自动显示
        initAllPopupWindowMethod();
        initNutritionPopupWindowMethod();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 注意逻辑运算 &&
        if (popupWindowAll != null && popupWindowAll.isShowing()) {
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
            case R.id.library_more_nutritional_element_tv:
            case R.id.library_more_nutritional_element_ib:
                clickNutritionMethod();

                break;
            case R.id.library_more_nutritional_element_order_btn:
                clickOrderMethod();

                break;
            default:
                Toast.makeText(this, "点击出错啦!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 点击全部 后 调用的方法
     */
    private void clickAllMethod() {
        // 点击 全部, 显示 pop
        if (!popupWindowAll.isShowing()) {
            popupWindowAll.showAsDropDown(allBtn);
        } else {
            popupWindowAll.dismiss();
        }
        // 将营养素排序的弹窗关闭
        if (popupWindowNutrition != null && popupWindowNutrition.isShowing()) {
            popupWindowNutrition.dismiss();
        }
    }

    /**
     * 点击营养素 后 调用的方法
     */
    private void clickNutritionMethod() {
        // 营养素排序, 显示 pop
        if (!popupWindowNutrition.isShowing()) {
            popupWindowNutrition.showAsDropDown(nutritionalTV);
        } else {
            popupWindowNutrition.dismiss();
        }
        // 将全部的弹窗关闭
        if (popupWindowAll != null && popupWindowAll.isShowing()) {
            popupWindowAll.dismiss();
        }
    }

    /**
     * 点击 全部 后 显示的界面
     */
    private void initAllPopupWindowMethod() {
        popupWindowAll = new PopupWindow(
                200,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_library_more_all, null);
        popList = (ListView) view.findViewById(R.id.pop_library_more_all_list);

        // 使用 ArrayAdapter 进行全部的数据的显示
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                LibraryMoreActivity.this,
                R.layout.item_pop_all, R.id.item_pop_all_tv, subNameArrayList);
        popList.setAdapter(arrayAdapter);

        popListItemClickMethod(popList);

        popupWindowAll.setContentView(view);
    }

    /**
     * 点击 营养素排序 后 显示的界面
     */
    private void initNutritionPopupWindowMethod() {
        popupWindowNutrition = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        View view = LayoutInflater.from(this).inflate(R.layout.pop_library_more_nutritional, null);
        popRecycler = (RecyclerView) view.findViewById(R.id.pop_library_more_nutritional_recycler);

        // 网络请求 营养素排序 数据
        // 并用 接口回调 实现 Recycler的Item 的点击事件
        nutritionalGsonMethod();

        popupWindowNutrition.setContentView(view);
    }


    /**
     * 点击 全部 的弹窗中的 Item 的点击事件
     */
    private void popListItemClickMethod(ListView popList) {
        popList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 将全部的Button 文字改变
                allBtn.setText(subNameArrayList.get(position));

                if (position == 0) {
                    // 点击全部的 全部选项
                     url = part1Kind + getKind + part2Value + getId + part4OrderBy +
                            orderIndex + part5Page + 1 + part6OrderAsc + orderAsc;

                } else {
                    subValue = subIdArrayList.get(position - 1);
                    url = part1Kind + getKind + part2Value + getId +
                            part3SubValue + subValue + ")" + part4OrderBy +
                            orderIndex + part5Page + 1 + part6OrderAsc + orderAsc;
                }
                showData(url);
                popupWindowAll.dismiss();
            }
        });
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
                nutritionalTV.setText(beanArrayList.get(position).getName());

                // order_by = 营养素排序的 index(1代表无序, 2代表热量, 依次向下)
                orderIndex = beanArrayList.get(position).getIndex();

                if (allBtn.getText().equals(subNameArrayList.get(0))) {
                    // 当全部 选择的是 全部的情况
                    url = part1Kind + getKind + part2Value + getId +
                            part4OrderBy + orderIndex + part5Page + 1 + part6OrderAsc + orderAsc;
                } else {
                    url = part1Kind + getKind + part2Value + getId +
                            part3SubValue + subValue + ")" + part4OrderBy +
                            orderIndex + part5Page + 1 + part6OrderAsc + orderAsc;
                }
                showData(url);
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
            orderAsc = ORDER_ASC_INT;

        } else {
            orderBtn.setText(ORDER_DEC_TEXT);
            orderIV.setImageResource(ORDER_DEC_IMAGE);
            orderAsc = ORDER_DEC_INT;
        }

        if (allBtn.getText().equals(subNameArrayList.get(0))) {
            // 当全部 选择的是 全部的情况
            url = part1Kind + getKind + part2Value + getId +
                    part4OrderBy + orderIndex + part5Page + 1 + part6OrderAsc + orderAsc;
        } else {
            url = part1Kind + getKind + part2Value + getId +
                    part3SubValue + subValue + ")" + part4OrderBy +
                    orderIndex + part5Page + 1 + part6OrderAsc + orderAsc;
        }
        showData(url);
    }

    /**
     * 第二个pop(营养素排序) 的数据的请求
     */
    private void nutritionalGsonMethod() {
        GsonRequest<NutritionalElementBean> gsonRequest = new GsonRequest<>(
                NutritionalElementBean.class, UrlValues.LIBRARY_NUTRITION_URL,
                new Response.Listener<NutritionalElementBean>() {
                    @Override
                    public void onResponse(NutritionalElementBean response) {

                        final ArrayList<NutritionalElementBean.TypesBean> beanArrayList =
                                (ArrayList<NutritionalElementBean.TypesBean>) response.getTypes();

                        GridLayoutManager manager = new GridLayoutManager(LibraryMoreActivity.this, 3);
                        popRecycler.setLayoutManager(manager);

                        MyPopRvAdapter adapter = new MyPopRvAdapter();
                        adapter.setBeanArrayList(beanArrayList);
                        popRecycler.setAdapter(adapter);

                        // 接口回调实现 RecyclerView 中的 Item 的点击监听
                        popRecyclerItemClickMethod(adapter, beanArrayList);

                        // 添加分割线
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


    /**
     * 传入url 进行网络请求 获取数据, 并进行铺建
     */
    private void showData(String url) {

        GsonRequest<FoodMoreBean> gsonRequest = new GsonRequest<>(
                FoodMoreBean.class, url, new Response.Listener<FoodMoreBean>() {
            @Override
            public void onResponse(FoodMoreBean response) {

                ArrayList<FoodMoreBean.FoodsBean> beanArrayList =
                        (ArrayList<FoodMoreBean.FoodsBean>) response.getFoods();

                MyLibraryMoreLvAdapter adapter = new MyLibraryMoreLvAdapter();
                adapter.setBeanArrayList(beanArrayList);
                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LibraryMoreActivity", "请求失败啦!");
            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);

    }

}
