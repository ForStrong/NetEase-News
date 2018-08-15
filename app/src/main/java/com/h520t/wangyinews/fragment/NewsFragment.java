package com.h520t.wangyinews.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.h520t.wangyinews.R;
import com.h520t.wangyinews.news.adapter.MyAdapter;
import com.h520t.wangyinews.news.bean.FragmentInfo;
import com.h520t.wangyinews.news.news_inner_fragment.EmptyFragment;
import com.h520t.wangyinews.news.news_inner_fragment.HostFragment;
import com.h520t.wangyinews.util.HttpUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    ArrayList<FragmentInfo> mFragmentInfos;
    SmartTabLayout mSmartTabLayout;
    ViewPager mViewPager;
    static NewsFragment mNewsFragment;
    @SuppressLint("ValidFragment")
    private NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mSmartTabLayout = view.findViewById(R.id.view_pager_tab);
        mViewPager = view.findViewById(R.id.viewpager);
        mFragmentInfos = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] stringArray = getResources().getStringArray(R.array.tab_titles);
        for (int i = 0; i < stringArray.length; i++) {
            Fragment fragment;
            FragmentInfo fragmentInfo;
            if (i==0){
                fragment = HostFragment.newsInstance();
            }else {
                fragment = new EmptyFragment();
            }
            fragmentInfo = new FragmentInfo(fragment,stringArray[i]);
            mFragmentInfos.add(fragmentInfo);
        }
        MyAdapter adapter = new MyAdapter(getChildFragmentManager(),mFragmentInfos);
        mViewPager.setAdapter(adapter);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    public static NewsFragment newsInstance(){
        if(mNewsFragment==null){
            synchronized (NewsFragment.class){
                if(mNewsFragment==null){
                    mNewsFragment = new NewsFragment();
                }
            }
        }
        return  mNewsFragment;
    }

/*
    private static class NewsFragmentHolder{

        private static  NewsFragment sNewsFragment = new NewsFragment();
    }
*/

}
