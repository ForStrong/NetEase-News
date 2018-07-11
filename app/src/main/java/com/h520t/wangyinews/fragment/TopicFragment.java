package com.h520t.wangyinews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h520t.wangyinews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends Fragment {


    public TopicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    public static TopicFragment newsInstance(){
        return TopicFragmentHolder.sTopicFragment;
    }

    private static class TopicFragmentHolder{
        private static final TopicFragment sTopicFragment = new TopicFragment();
    }

}
