package com.h520t.wangyinews.news.news_inner_fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h520t.wangyinews.R;
import com.h520t.wangyinews.news.bean.Hot;
import com.h520t.wangyinews.util.Contants;
import com.h520t.wangyinews.util.HttpResponse;
import com.h520t.wangyinews.util.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostFragment extends Fragment {


    public HostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_host, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.getData(Contants.HOT_URL, new HttpResponse<Hot>(Hot.class) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(Hot hot) {
                String s = hot.toString();
                Log.i("h520it", "onSuccess: "+s);
            }
        });
    */
    }
}
