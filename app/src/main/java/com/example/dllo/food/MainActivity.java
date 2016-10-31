package com.example.dllo.food;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dllo.food.library.LibraryFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        radioButton = (RadioButton) findViewById(R.id.main_radio_library);

        radioButton.setChecked(true);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_framelayout, new LibraryFragment());
        transaction.commit();

        // 设置radioGroup的监听时间
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                judgeRadioButtonIsChecked(checkedId);
            }
        });
    }

    /** 选中不同项, 就将 FrameLayout 位置换成 相应的不同的 Fragment */
    private void judgeRadioButtonIsChecked(int checkedId) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (checkedId) {
            case R.id.main_radio_library:
                transaction.replace(R.id.main_framelayout, new LibraryFragment());

                break;
            case R.id.main_radio_homepage:
                transaction.replace(R.id.main_framelayout, new HomePageFragment());

                break;
            case R.id.main_radio_my:
                transaction.replace(R.id.main_framelayout, new MyFragment());

                break;
            default:
                Log.d("MainActivity", "出错啦!");
                break;
        }

        transaction.commit();
    }
}
