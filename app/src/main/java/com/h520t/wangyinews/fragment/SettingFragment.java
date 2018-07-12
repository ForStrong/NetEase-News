package com.h520t.wangyinews.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h520t.wangyinews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @SuppressLint("ValidFragment")
    private SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    public static SettingFragment newsInstance(){
        return SettingFragmentHolder.sSettingFragment;
    }
    private static class SettingFragmentHolder{
        private static final SettingFragment sSettingFragment = new SettingFragment();
    }
}
