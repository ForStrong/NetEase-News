package com.h520t.wangyinews.news.news_inner_fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.h520t.wangyinews.R;
import com.h520t.wangyinews.news.adapter.HotAdapter;
import com.h520t.wangyinews.news.bean.Hot;
import com.h520t.wangyinews.news.bean.HotDetail;
import com.h520t.wangyinews.util.Contants;
import com.h520t.wangyinews.util.HttpResponse;
import com.h520t.wangyinews.util.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<HotDetail> mHotDetails;
    MyHandler mMyHandler;
    final int INIT_SUCCESS = 0;
    Context mContext;
    SwipeToLoadLayout mSwipeToLoadLayout;
    int start = 0;
    int end = 20;
    HotAdapter hotAdapter;

    @SuppressLint("ValidFragment")
    private HostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(container.getTag()==null){
            view = inflater.inflate(R.layout.fragment_host, container, false);
            mSwipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
            mRecyclerView = view.findViewById(R.id.swipe_target);
            container.setTag(view);
        }else{
            view = (View) container.getTag();
        }
        return view;
    }

    private void refreshRVBottomData() {
            start = end;
            end = start + 20;
    }


    private void refreshRVTopData() {
        if(start>=20){
            start -= 20;
            end -= 20;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyHandler = new MyHandler(this);
        mContext = getActivity();
        getData();
        mSwipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRVTopData();
                getData();

            }
        });

        mSwipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                refreshRVBottomData();
                getData();
                hotAdapter.notifyDataSetChanged();
                mSwipeToLoadLayout.setLoadingMore(false);
            }
        });

    }

    private void getData() {
        HttpUtil httpUtil = HttpUtil.getInstance();
        String hotUrl = Contants.getHotUrl(start, end);
        httpUtil.getData(hotUrl, new HttpResponse<Hot>(Hot.class) {

            @Override
            public void onError(String msg) {
                Log.i("h520it", "onError: "+msg);
            }

            @Override
            public void onSuccess(Hot hot) {
                if (mHotDetails==null){
                    mHotDetails = hot.getT1348647909107();
                }else {
                    mHotDetails.addAll(hot.getT1348647909107());
                }

                Log.i("h520it1", "onSuccess: "+mHotDetails.toString());
                mMyHandler.sendEmptyMessage(INIT_SUCCESS);
            }
        });
    }

    class MyHandler extends Handler{
        WeakReference<HostFragment> mHostFragmentWeakReference;
        public MyHandler(HostFragment hostFragment){
            mHostFragmentWeakReference = new WeakReference<>(hostFragment);
        }
        @Override
        public void handleMessage(Message msg) {
            HostFragment hot = mHostFragmentWeakReference.get();
            if (hot==null){
                return;
            }
            switch (msg.what) {
                case INIT_SUCCESS:
                        hot.initData();
                    break;
                default:
                    break;
            }
        }
    }

    private void initData() {
        if (hotAdapter==null) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(manager);
            hotAdapter = new HotAdapter(mHotDetails, getActivity());
            mRecyclerView.setAdapter(hotAdapter);
        }else{
            hotAdapter.notifyDataSetChanged();
        }

    }

    public static HostFragment newsInstance(){
        return HostFragment.HostFragmentHolder.sNewsFragment;
    }

    private static class HostFragmentHolder{
        private static  HostFragment sNewsFragment = new HostFragment();
    }




}
