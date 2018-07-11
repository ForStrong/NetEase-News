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
public class ReadFragment extends Fragment {


    public ReadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read, container, false);
    }

    public static ReadFragment newsInstance(){
        return ReadFragmentHolder.sReadFragment;
    }

    private static class ReadFragmentHolder{
        private static final ReadFragment sReadFragment = new ReadFragment();
    }

}
