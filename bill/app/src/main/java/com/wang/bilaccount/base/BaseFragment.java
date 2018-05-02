package com.wang.bilaccount.base;

import android.support.v4.app.Fragment;

import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public class BaseFragment extends Fragment {
    private ImmersionBar mImmersionBar;

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    /**
     * 是否懒加载
     *
     * @return the boolean
     */
    protected boolean isLazyLoad() {
        return true;
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }
}
