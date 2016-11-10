package com.example.dllo.food.values;

/**
 * Created by XiaoyuLu on 16/11/1.
 *
 * 该类 用于 存放 接口字符串文件
 */
public class UrlValues {

    // 食物百科界面 接口
    public static final String LIBRARY_URL = "http://food.boohee.com/fb/v1/categories/list";

    //逛吃界面--首页接口
    public static final String EAT_HOMEPAGE_URL = "http://food.boohee.com/fb/v1/feeds/category_feed?page=1&category=1&per=10";
    //逛吃--首页--下拉刷新--后半段
    public static final String EAT_HOMEPAGE_DOWN_AFTER = "&category=1&per=10";
    // 逛吃界面--首页-点击第一张图片后跳转的 网址
    public static final String EAT_HOMEPAGE_FIRST_LINK = "http://one.boohee.com/store/pages/sleep_app_lite";

    //逛吃界面--测评接口
    public static final String EAT_EVALUATE_URL = "http://food.boohee.com/fb/v1/feeds/category_feed?page=1&category=2&per=10";
    //逛吃--测试--知识--首页--美食--下拉刷新--前半段
    public static final String EAT_DOWN_BEFORE = "http://food.boohee.com/fb/v1/feeds/category_feed?page=";
    //逛吃--测试--下拉刷新--后半段
    public static final String EAT_TEST_DOWN_AFTER = "&category=2&per=10";

    //逛吃界面--知识接口
    public static final String EAT_KNOWLEDGE_URL = "http://food.boohee.com/fb/v1/feeds/category_feed?page=1&category=3&per=10";
    //逛吃--知识--下拉刷新--后半段
    public static String EAT_KNOWLEDGE_DOWN_AFTER ="&category=3&per=10";

    //逛吃界面--美食接口
    public static final String EAT_BEAUTY_URL = "http://food.boohee.com/fb/v1/feeds/category_feed?page=1&category=4&per=10";
    //逛吃--美食--下拉刷新--后半段
    public static String EAT_BEAUTY_DOWN_AFTER ="&category=4&per=10";

    //食物百科--详情--营养素排序pop
    public static final String LIBRARY_NATRITIONALELEMENT_URL = "http://food.boohee.com/fb/v1/foods/sort_types";
    public static final String LIBRARY_MORE_URL = "http://food.boohee.com/fb/v1/foods?kind=group&value=1&order_by=1&page=1&order_asc=0";
    public static final String LIBRARY_MORE_DETAIL_URL = "http://food.boohee.com/fb/v1/foods?kind=group&value=1(&sub_value=13)&order_by=1&page=1&order_asc=0";

    //食物百科--详情--下拉刷新--group--前半部分
    public static String FOOD_DESCRIPTION_DOWN_GROUP_BEFORE = "http://food.boohee.com/fb/v1/foods?kind=group&value=";
    //食物百科--详情--下拉刷新--brand--前半部分
    public static String FOOD_DESCRIPTION_DOWN_BRAND_BEFORE = "http://food.boohee.com/fb/v1/foods?kind=brand&value=";
    //食物百科--详情--下拉刷新--restaurant--前半部分
    public static String FOOD_DESCRIPTION_DOWN_RESTAURANT_BEFORE = "http://food.boohee.com/fb/v1/foods?kind=restaurant&value=";
    //食物百科--详情--下拉刷新--后半部分(上边三个通用后边的)
    public static String FOOD_DESCRIPTION_DOWN_AFTER = "&order_by=1&page=";


    // kind = 分类 品牌 餐饮
    // value = 点击的 gridView 的 position
    // order_by = 营养素排序的 index(1代表无序, 2开始, 2代表热量, 依次向下)
    // order_ac = 0 (高到低)   1(低到高)
    // sub_values = 13(包装谷薯) 12(谷薯制品)11(天然谷薯) 右上角分类
    // http://food.boohee.com/fb/v1/foods?kind=group&value=1
    // (&sub_value=13)&order_by=1&page=1&order_asc=0

    // 食物详情 接口组成
    public static final String FOOD_MORE_PART1_KIND = "http://food.boohee.com/fb/v1/foods?kind=";
    public static final String FOOD_MORE_PART2_VALUE = "&value=";
    public static final String FOOD_MORE_PART3_SUB_VALUE = "(&sub_value=";
    // 在 (&sub_value= 的值添加后 还要添加一个 括号 ")"
    public static final String FOOD_MORE_PART4_ORDER_BY = "&order_by=";
    public static final String FOOD_MORE_PART5_PAGE = "&page=";
    public static final String FOOD_MORE_PART6_ORDER_ASC = "&order_asc=";
    public static final String FOOD_MORE_PART_TAIL = "&order_by=1&page=1&order_asc=0";


}
