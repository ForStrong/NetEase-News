package com.h520t.smarttablayouttest;

import android.support.v4.app.Fragment;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class FragmentInfo {
    private Fragment mFragment;
    private String title;

    public FragmentInfo(Fragment fragment, String title) {
        mFragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }


}
