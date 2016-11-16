package com.example.dllo.food.beans.library;

import java.util.List;

/**
 * Created by XiaoyuLu on 16/11/10.
 *
 * SearchActivity 中输入东西后, 搜索后所得的 数据类
 */
public class LibrarySearchBean {

    /**
     * page : 1
     * total_pages : 51
     * tags : [{"type":"tags","name":"热、亚热带水果"},{"type":"tags","name":"加餐"},{"type":"tags","name":"全年"}]
     * items : [{"id":611,"code":"xiangjiao","name":"香蕉","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_2015131152726611.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"93","type":"food"},{"id":481,"code":"qingxiangjiaopingguo","name":"青香蕉苹果","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_20152493717481.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"52","type":"food"},{"id":16976,"code":"xiangjiaobing","name":"香蕉饼","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_20115241542116976.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"359","type":"food"},{"id":478,"code":"huangxiangjiaopingguo","name":"黄香蕉苹果","thumb_image_url":"http://s.boohee.cn/house/food_mid/m_huangxiangjiaopg.gif","is_liquid":false,"health_light":1,"weight":"100","calory":"53","type":"food"},{"id":474,"code":"hongxiangjiaopingguo","name":"红香蕉苹果","thumb_image_url":"http://s.boohee.cn/house/food_mid/m_hongxiangjiaopg.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"51","type":"food"},{"id":17012,"code":"xiangjiaopian","name":"HAWAII'S 香蕉片","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/7/12/84325_1215819779mid.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"533","type":"food"},{"id":19583,"code":"xiangjiaodangao2","name":"香蕉蛋糕","thumb_image_url":"http://s.boohee.cn/house/upload_food/2009/5/30/118906_1243692124mid.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"282","type":"food"},{"id":11767,"code":"cuipixiangjiao","name":"脆皮香蕉","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/10/18/user_147120_1224325966.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"201","type":"food"},{"id":13835,"code":"xiangjiaojuan","name":"香蕉卷","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/27/mid_photo_url_560878dacd0f4470bbc999cce160687c.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"183","type":"food"},{"id":16972,"code":"doushaxiangjiaobing","name":"豆沙香蕉饼","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_201151815451116972.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"260","type":"food"},{"id":17229,"code":"meiweixiangjiaobing","name":"美味香蕉饼","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/8/18/44054_1219037788mid.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"241","type":"food"},{"id":1628,"code":"xiangjiao_hongpi_hainan","name":"香蕉（红皮、海南）","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/6/12/user_mid_23474_1213284414.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"82","type":"food"},{"id":12200,"code":"supixiangjiao","name":"酥皮香蕉","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/26/mid_photo_url_0a6f8e086481463ea826e430094bf5c8.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"190","type":"food"},{"id":11660,"code":"xiangjiaoguozha","name":"香蕉锅炸","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/27/mid_photo_url_3eaa068d69ed4a3cb58c19dd9d6f3a96.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"450","type":"food"},{"id":1629,"code":"xiangjiao_hongpi_taiguo","name":"香蕉（红皮、泰国）","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/6/12/user_mid_23474_1213284431.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"78","type":"food"},{"id":103103,"code":"fd2a3dea","name":"香蕉子","thumb_image_url":"http://s.boohee.cn/house/upload_food/2015/8/7/mid_photo_url_D9624021B92E.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"504","type":"food"},{"id":95923,"code":"sudianhixiangjiaopai","name":"速点 香蕉派","thumb_image_url":"http://s.boohee.cn/house/upload_food/2014/11/7/1275155_1415325162mid.png","is_liquid":false,"health_light":2,"weight":"100","calory":"145","type":"food"},{"id":6379,"code":"xiangjiaohaixianjuan","name":"香蕉海鲜卷","thumb_image_url":"http://s.boohee.cn/house/menu_mid/1170405732351_mid.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"210","type":"food"},{"id":32915,"code":"xiangjiaoji","name":"香蕉鸡","thumb_image_url":"http://s.boohee.cn/house/upload_food/2012/3/25/1282401_1332664635mid.png","is_liquid":false,"health_light":2,"weight":"100","calory":"201","type":"food"},{"id":34017,"code":"Bananachips","name":"Woolworths Bananachips","thumb_image_url":"http://s.boohee.cn/house/upload_food/2012/5/30/1429986_1338339099mid.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"535","type":"food"}]
     */

    private int page;
    private int total_pages;
    /**
     * type : tags
     * name : 热、亚热带水果
     */

    private List<TagsBean> tags;
    /**
     * id : 611
     * code : xiangjiao
     * name : 香蕉
     * thumb_image_url : http://s.boohee.cn/house/food_mid/mid_photo_2015131152726611.jpg
     * is_liquid : false
     * health_light : 1
     * weight : 100
     * calory : 93
     * type : food
     */

    private List<ItemsBean> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class TagsBean {
        private String type;
        private String name;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ItemsBean {
        private int id;
        private String code;
        private String name;
        private String thumb_image_url;
        private boolean is_liquid;
        private int health_light;
        private String weight;
        private String calory;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumb_image_url() {
            return thumb_image_url;
        }

        public void setThumb_image_url(String thumb_image_url) {
            this.thumb_image_url = thumb_image_url;
        }

        public boolean isIs_liquid() {
            return is_liquid;
        }

        public void setIs_liquid(boolean is_liquid) {
            this.is_liquid = is_liquid;
        }

        public int getHealth_light() {
            return health_light;
        }

        public void setHealth_light(int health_light) {
            this.health_light = health_light;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getCalory() {
            return calory;
        }

        public void setCalory(String calory) {
            this.calory = calory;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
