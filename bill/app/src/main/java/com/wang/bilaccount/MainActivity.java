package com.wang.bilaccount;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.wang.bilaccount.adapter.ViewPageAdapter;
import com.wang.bilaccount.base.BaseActivity;
import com.wang.bilaccount.base.BaseFragment;
import com.wang.bilaccount.fragment.BillFragment;
import com.wang.bilaccount.fragment.MyFragment;
import com.wang.bilaccount.fragment.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String ARCHIVES_REFRESH = "archives_refresh";
    private ViewPager mContainer;
    /**
     * 首页
     */
    private TextView mTxt1;
    /**
     * 账单
     */
    private TextView mTxt2;
    /**
     * 我
     */
    private TextView mTxt3;

    HomeFragment homeFragment;
    BillFragment billFragment;
    MyFragment myFragment;

    ViewPageAdapter viewPageAdapter;
    List<BaseFragment> lstBaseFragMent;
    MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBroadcastReceiver();
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ARCHIVES_REFRESH.equals(intent.getAction())) {
                homeFragment.refresh();
                billFragment.refresh();
            }
        }
    }

    void initBroadcastReceiver() {
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ARCHIVES_REFRESH);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    private void initView() {

        mContainer = (ViewPager) findViewById(R.id.Container);
        mTxt1 = (TextView) findViewById(R.id.txt1);
        mTxt2 = (TextView) findViewById(R.id.txt2);
        mTxt3 = (TextView) findViewById(R.id.txt3);
        lstBaseFragMent = new ArrayList<>();
        homeFragment = new HomeFragment();
        billFragment = new BillFragment();
        myFragment = new MyFragment();
        lstBaseFragMent.add(homeFragment);
        lstBaseFragMent.add(billFragment);
        lstBaseFragMent.add(myFragment);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), lstBaseFragMent);
        mContainer.setAdapter(viewPageAdapter);
        mContainer.setOffscreenPageLimit(3);
        mContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                reSet();
                if (position == 0) {
                    mTxt1.setSelected(true);
                } else if (position == 1) {
                    mTxt2.setSelected(true);
                } else {
                    mTxt3.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTxt1.setOnClickListener(this);
        mTxt2.setOnClickListener(this);
        mTxt3.setOnClickListener(this);
        mTxt1.setSelected(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.txt1:
                reSet();
                mTxt1.setSelected(true);
                mContainer.setCurrentItem(0);
                mImmersionBar.statusBarDarkFont(false);
                mImmersionBar.init();

                break;
            case R.id.txt2:
                reSet();
                mTxt2.setSelected(true);
                mContainer.setCurrentItem(1);
                mImmersionBar.statusBarDarkFont(true);
                mImmersionBar.init();


                break;
            case R.id.txt3:
                reSet();
                mTxt3.setSelected(true);
                mContainer.setCurrentItem(2);
                mImmersionBar.statusBarDarkFont(false);
                mImmersionBar.init();

                break;
        }
    }

    void reSet() {
        mTxt1.setSelected(false);
        mTxt2.setSelected(false);
        mTxt3.setSelected(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
