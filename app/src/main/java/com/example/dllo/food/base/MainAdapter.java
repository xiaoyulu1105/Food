//package com.example.dllo.food.base;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import java.util.ArrayList;
//
///**
// * Created by dllo on 16/10/31.
// */
//public class MainAdapter extends BaseAdapter{
//
//    ArrayList<String> stringArrayList = new ArrayList<>();
//
//    public void setStringArrayList(ArrayList<String> stringArrayList) {
//        this.stringArrayList = stringArrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return stringArrayList == null ? 0 : stringArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return stringArrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        // CommonVH 中使用静态方法的原因是 想再 在该方法中才实现 new一个CommonVH的对象,
//        // 如果不将方法写成静态的,就必须先new一个CommonVH对象, 这样就实现不了初衷了
//        // 在该静态方法里实现 行布局的加载, 等相关操作
//        CommonVH viewHolder = CommonVH.getViewHolder(convertView, parent, R.layout.item_main);
//
//        // 设置数据
//        // 链式编程
//        // 所有的 set 方法都可进行类似的改造
//        viewHolder.setText(R.id.item_tv, "测试").setImage(R.id.item_iv, R.mipmap.ic_launcher);
//        viewHolder.setImage(R.id.item_iv, "http://m2.quanjing.com/2m/mf065/mf700-03644606.jpg");
//
//        // View 的点击事件
//        viewHolder.setViewClick(R.id.item_tv, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        return viewHolder.getItemView();
//    }
//
//    // 在 CommonVH(通用 ViewHolder) 类里实现功能
////    class ViewHolder {
////
////        private TextView tv;
////        public ViewHolder(View view) {
////            tv = (TextView) view.findViewById(R.id.item_tv);
////        }
////    }
//
//}
