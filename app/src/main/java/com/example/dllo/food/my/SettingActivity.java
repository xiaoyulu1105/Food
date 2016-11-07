package com.example.dllo.food.my;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dllo.food.R;
import com.example.dllo.food.base.BaseActivity;

/**
 * Created by XiaoyuLu on 16/11/4.
 * <p>
 * 设置界面 的Activity
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton returnImgBtn;
    private ListView listView;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        returnImgBtn = bindView(R.id.my_set_return);
        listView = bindView(R.id.my_set_list);

        setClick(this, returnImgBtn);

    }

    @Override
    protected void initData() {

        String item = "清除缓存";
        String item1 = "给我们提个建议";
        String item2 = "给个评分吧";
        String item3 = "将食物派分享给朋友";

        String[] items = new String[]{item, item1,item2, item3};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // 点击的是第一项: 清除缓存
                        Toast.makeText(SettingActivity.this, "清除缓存", Toast.LENGTH_SHORT).show();
                        Log.d("SettingActivity", "清除缓存");

                        break;
                    case 1:
                        // 点击的是第二项: 给我们提个建议
                        Toast.makeText(SettingActivity.this, "给我们提个建议", Toast.LENGTH_SHORT).show();
                        Log.d("SettingActivity", "给我们提个建议");

                        break;
                    case 2:
                        // 点击的是第三项: 评个分吧
                        Toast.makeText(SettingActivity.this, "评个分吧", Toast.LENGTH_SHORT).show();
                        Log.d("SettingActivity", "评个分吧");

                        break;
                    case 3:
                        // 点击的是第四项: 将食物派分享给朋友
                        Toast.makeText(SettingActivity.this, "将食物派分享给朋友", Toast.LENGTH_SHORT).show();
                        Log.d("SettingActivity", "将食物派分享给朋友");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_set_return:
                finish();

                break;


            default:
                Log.d("SettingActivity", "出错啦!");
        }
    }
}
