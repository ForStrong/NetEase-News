package com.h520t.wangyinews;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.h520t.wangyinews.fragment.NewsFragment;
import com.h520t.wangyinews.fragment.ReadFragment;
import com.h520t.wangyinews.fragment.SettingFragment;
import com.h520t.wangyinews.fragment.TopicFragment;
import com.h520t.wangyinews.fragment.VideoFragment;
import com.h520t.wangyinews.util.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mNavigationView;
    private FragmentManager fm;
    private Fragment currentF;
    int[] normal_img = {
            R.drawable.biz_navigation_tab_news
            , R.drawable.biz_navigation_tab_read
            , R.drawable.biz_navigation_tab_va
            , R.drawable.biz_navigation_tab_topic
            , R.drawable.biz_navigation_tab_pc};
    int[] selector_img = {
            R.drawable.biz_navigation_tab_news_selected
            , R.drawable.biz_navigation_tab_read_selected
            , R.drawable.biz_navigation_tab_va_selected
            , R.drawable.biz_navigation_tab_topic_selected
            , R.drawable.biz_navigation_tab_pc_selected};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        mNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);


        mNavigationView.setItemIconTintList(null);

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.i("h520it", "onNavigationItemSelected: " + item.getItemId());
                return showIcon(item);

            }
        });
        //动态原则字体颜色
        showTextColor();
//        mNavigationView.getMenu().getItem(0).setChecked(true);这句话只是让底部按钮设置为选中状态
                mNavigationView.setSelectedItemId(R.id.new_action);//这句话只是让底部按钮设置为选中状态，并执行监听事件
    }

    private void showTextColor() {
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.colorGrey),
                getResources().getColor(R.color.colorRed)
        };

        ColorStateList csl = new ColorStateList(states, colors);
        mNavigationView.setItemTextColor(csl);
    }

    private boolean showIcon(MenuItem item) {
        Log.i("h520it", "showIcon: "+item.getTitle().toString());
        //Fragment初始化使用静态内部类单例设计模式
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.new_action:
                selectedFragment = NewsFragment.newsInstance();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.red_text));
                }
                break;
            case R.id.read_action:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
                }
                selectedFragment = ReadFragment.newsInstance();
                break;
            case R.id.video_action:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
                }
                selectedFragment = VideoFragment.newsInstance();
                break;
            case R.id.topic_action:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
                }
                selectedFragment = TopicFragment.newsInstance();
                break;
            case R.id.my_action:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
                }
                selectedFragment = SettingFragment.newsInstance();
                break;
        }
        showF(selectedFragment);
        //切换图标
        int size = mNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            MenuItem item_index = mNavigationView.getMenu().getItem(i);
            if (item_index.getItemId() == item.getItemId()) {
                Log.i("h520it", "onNavigationItemSelected: " + item_index.getItemId());
                item_index.setIcon(selector_img[i]);
            } else {
                item_index.setIcon(normal_img[i]);
            }
        }
        return true;
    }

    public void showF(Fragment targetF){
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (currentF == null){
            transaction.add(R.id.fragment_layout,targetF);
        }else if (!targetF.isAdded()){
            transaction.hide(currentF).add(R.id.fragment_layout,targetF);
        }else{
            transaction.hide(currentF).show(targetF);
        }
        currentF = targetF;
        transaction.commit();
    }


}













