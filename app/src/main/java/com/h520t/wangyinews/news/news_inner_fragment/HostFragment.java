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
            mRecyclerView = view.findViewById(R.id.recycler_view);
            container.setTag(view);
        }else{
            view = (View) container.getTag();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyHandler = new MyHandler(this);
        mContext = getActivity();
        getData();
    }

    private void getData() {
            HttpUtil httpUtil = HttpUtil.getInstance();
            httpUtil.getData(Contants.HOT_URL, new HttpResponse<Hot>(Hot.class) {

            @Override
            public void onError(String msg) {
                Log.i("h520it", "onError: "+msg);
            }

            @Override
            public void onSuccess(Hot hot) {
                String s = hot.toString();
                mHotDetails = hot.getT1348647909107();
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
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        HotAdapter hotAdapter = new HotAdapter(mHotDetails,getActivity());
        mRecyclerView.setAdapter(hotAdapter);
    }

    public static HostFragment newsInstance(){
        return HostFragment.HostFragmentHolder.sNewsFragment;
    }

    private static class HostFragmentHolder{
        private static  HostFragment sNewsFragment = new HostFragment();
    }


}
