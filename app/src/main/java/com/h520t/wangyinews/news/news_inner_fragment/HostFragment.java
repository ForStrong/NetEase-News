package com.h520t.wangyinews.news.news_inner_fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.h520t.wangyinews.R;
import com.h520t.wangyinews.RVlistener.RecyclerItemClickListener;
import com.h520t.wangyinews.fragment.NewsFragment;
import com.h520t.wangyinews.news.activity.DetailsActivity;
import com.h520t.wangyinews.news.adapter.HomeRVAdapter;
import com.h520t.wangyinews.news.adapter.HotAdapter;
import com.h520t.wangyinews.news.bean.Home;
import com.h520t.wangyinews.news.bean.HomeDetail;
import com.h520t.wangyinews.news.bean.Hot;
import com.h520t.wangyinews.news.bean.HotDetail;
import com.h520t.wangyinews.news.bean.VideoInfo;
import com.h520t.wangyinews.util.Contants;
import com.h520t.wangyinews.util.HttpResponse;
import com.h520t.wangyinews.util.HttpUtil;
import com.h520t.wangyinews.util.RecyclerViewDivider;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<HomeDetail> mHotDetails;
    MyHandler mMyHandler;
    final int INIT_SUCCESS = 0;
    Context mContext;
    SmartRefreshLayout mSmartRefreshLayout;
    int start = 0;
    int end = 20;
    HomeRVAdapter mHomeRVAdapter;
    private static HostFragment sHostFragment;
    private static final String TAG = "HostFragment";
    @SuppressLint("ValidFragment")
    public HostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(container.getTag()==null){
            view = inflater.inflate(R.layout.fragment_host, container, false);
            mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
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




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyHandler = new MyHandler(this);
        mContext = getActivity();
        mSmartRefreshLayout.setRefreshHeader(new WaterDropHeader(mContext));
        getData(start,end,false);

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            getData(0,20,true);
            mHomeRVAdapter.notifyDataSetChanged();
            mSmartRefreshLayout.finishRefresh();//传入false表示刷新失败
        });


        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshRVBottomData();
            getData(start,end,false);
            mHomeRVAdapter.notifyDataSetChanged();
            mSmartRefreshLayout.finishLoadMore();//传入false表示刷新失败
        });


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        HomeDetail homeDetail = mHotDetails.get(position);
                        VideoInfo videoinfo = homeDetail.getVideoinfo();
                        if (videoinfo==null) {
                            Intent intent = new Intent();
                            intent.putExtra("id",mHotDetails.get(position).getReplyid());
                            intent.setClass(mContext, DetailsActivity.class);
                            startActivity(intent);
                        }else{

                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(mContext, "onItemLongClick", Toast.LENGTH_SHORT).show();
                    }

                })
        );

    }

    private void getData(int start,int end,boolean refresh) {
        HttpUtil httpUtil = HttpUtil.getInstance();
        String hotUrl = Contants.getHotUrl(start, end);
        httpUtil.getData(hotUrl, new HttpResponse<Home>(Home.class) {
            @Override
            public void onError(String msg) {
                Log.i("h520it", "onError: "+msg);
            }
            @Override
            public void onSuccess(Home home) {
                if (mHotDetails==null){
                    mHotDetails = home.getT1348647909107();
                }else if(!refresh){
                    mHotDetails.addAll(home.getT1348647909107());
                }else{
                    ArrayList<HomeDetail> data = home.getT1348647909107();
                    Log.i(TAG, "onResponse: "+data.toString());
                    data.remove(0);
                    data.remove(0);
                    Log.i(TAG, "onResponse: "+data.toString());
                    mHotDetails.addAll(2,data);
                    Log.i(TAG, "onResponse: "+mHotDetails.toString());
                }

                Log.i("h520it1", "onSuccess: "+mHotDetails.toString());
                mMyHandler.sendEmptyMessage(INIT_SUCCESS);
            }
        });
    }

    class MyHandler extends Handler{
        WeakReference<HostFragment> mHostFragmentWeakReference;
        MyHandler(HostFragment hostFragment){
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
        if (mHomeRVAdapter==null) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
            mHomeRVAdapter = new HomeRVAdapter(mHotDetails);
            mRecyclerView.setAdapter(mHomeRVAdapter);
            mHomeRVAdapter.notifyDataSetChanged();
        }else{
            mHomeRVAdapter.notifyDataSetChanged();
        }



    }

    public static HostFragment newsInstance(){
        if(sHostFragment==null){
            synchronized (HostFragment.class){
                if(sHostFragment==null){
                    sHostFragment = new HostFragment();
                }
            }
        }
        return  sHostFragment;
    }





}
