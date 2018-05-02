package com.wang.bilaccount.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wang.bilaccount.base.BaseFragment;

import java.util.List;


/**
 * Created by Administrator on 2018/1/15/015.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mlist;

    public ViewPageAdapter(FragmentManager fm, List<BaseFragment> mlist) {
        super(fm);
        this.mlist = mlist;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
