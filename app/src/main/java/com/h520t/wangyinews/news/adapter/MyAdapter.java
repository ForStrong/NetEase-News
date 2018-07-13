package com.h520t.wangyinews.news.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.h520t.wangyinews.news.bean.FragmentInfo;

import java.util.List;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class MyAdapter extends FragmentStatePagerAdapter {
    private List<FragmentInfo> mFragmentInfos;

    public MyAdapter(FragmentManager fm, List<FragmentInfo> fragmentInfos) {
        super(fm);
        mFragmentInfos = fragmentInfos;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentInfos.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragmentInfos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentInfos.get(position).getTitle();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

}
