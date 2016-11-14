package com.example.dllo.food.my.collection;

import android.content.Intent;
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
    }

    /** 显示收藏的数据 */
    private void showCollectionArticle() {
        dbTool.queryAllData(CollectionSqlData.class, new DBTool.OnQueryListener<CollectionSqlData>() {
            @Override
            public void onQuery(final ArrayList<CollectionSqlData> collectionSqlDatas) {

                if (collectionSqlDatas.size() <= 0) {
                    // 当收藏记录为空
                    emptyLl.setVisibility(View.VISIBLE);
                } else {

                    // 当收藏记录不为空
                    MyCollectionArticleLVAdapter adapter = new MyCollectionArticleLVAdapter();
                    adapter.setArrayList(collectionSqlDatas);
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

//    /** listView 的点击事件 */
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String title =
//        Intent intent = new Intent(mContext, EvaluateMoreActivity.class);
//        intent.putExtra("title", )
//
//    }
}
