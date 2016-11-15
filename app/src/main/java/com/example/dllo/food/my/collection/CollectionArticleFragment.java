package com.example.dllo.food.my.collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseFragment;
import com.example.dllo.food.eat.evaluate.EvaluateMoreActivity;
import com.example.dllo.food.sqltools.CollectionSqlData;
import com.example.dllo.food.sqltools.DBTool;

import java.util.ArrayList;

/**
 * Created by XiaoyuLu on 16/11/14.
 *
 * 我的收藏中显示 文章 的Fragment
 */
public class CollectionArticleFragment extends BaseFragment {

    private ListView listView;
    private DBTool dbTool;
    private LinearLayout emptyLl; // 当没有收藏时显示该布局
    private ArrayList<CollectionSqlData> arrayList;  // 装载 ListView 显示的数据集合
    private MyCollectArticleBroadcast broadcast;

    @Override
    protected int getLayout() {
        return R.layout.fragment_collect_article;
    }

    @Override
    protected void initViews() {
        listView = bindView(R.id.my_collection_article_list);
        emptyLl = (LinearLayout) getView().findViewById(R.id.my_collection_article_null);

        dbTool = new DBTool();
    }

    @Override
    protected void initData() {

        showCollectionArticle();

        // 注册广播
        broadcast = new MyCollectArticleBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("broadcast");
        mContext.registerReceiver(broadcast, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(broadcast);
    }

    /** 显示收藏的数据 */
    private void showCollectionArticle() {
        dbTool.queryAllData(CollectionSqlData.class, new DBTool.OnQueryListener<CollectionSqlData>() {
            @Override
            public void onQuery(final ArrayList<CollectionSqlData> collectionSqlDatas) {

                arrayList = new ArrayList<>();
                arrayList = collectionSqlDatas;
                if (collectionSqlDatas.size() <= 0) {
                    // 当收藏记录为空
                    emptyLl.setVisibility(View.VISIBLE);
                } else {

                    // 当收藏记录不为空
                    MyCollectionArticleLVAdapter adapter = new MyCollectionArticleLVAdapter();
                    adapter.setArrayList(arrayList);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String title = collectionSqlDatas.get(position).getTitle();
                            String link = collectionSqlDatas.get(position).getLink();
                            Intent intent = new Intent(mContext, EvaluateMoreActivity.class);
                            intent.putExtra("title", title);
                            intent.putExtra("link", link);
                            startActivity(intent);
                        }
                    });
                }

            }
        });

    }

    /** 内部类 实现广播接收器 */
    private class MyCollectArticleBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String getLink = intent.getStringExtra("link");
            for (int i = 0; i < arrayList.size(); i++) {
                String link = arrayList.get(i).getLink();
                if (link.equals(getLink)) {
                    arrayList.remove(i);
                }
            }
            MyCollectionArticleLVAdapter adapter = new MyCollectionArticleLVAdapter();
            adapter.setArrayList(arrayList);
            listView.setAdapter(adapter);
        }
    }

}
