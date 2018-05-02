package com.wang.bilaccount;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wang.bilaccount.base.BaseActivity;
import com.wang.bilaccount.base.MyApp;
import com.wang.bilaccount.bean.BillBean;
import com.wang.bilaccount.widget.IconImageView;

import org.litepal.crud.DataSupport;

public class BillDetailActivity extends BaseActivity {

    private TextView mTxtTime;
    private IconImageView mClassReportIcon;
    /**
     * 购物
     */
    private TextView mTvClassReportName;
    private TextView mTvClassReportMoney;
    private TextView mTxtInfo;
    private ScrollView mScrollView;
    private SmartRefreshLayout mSmartRefreshLayout;
    BillBean billBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        initView();
        String uuid = getIntent().getStringExtra("uuid");
        billBean = DataSupport.where("uuid = ?", uuid).findFirst(BillBean.class);
        toolbar.setTitle("详情");
        setData();
    }

    private void initView() {
        mTxtTime = (TextView) findViewById(R.id.txt_time);
        mClassReportIcon = (IconImageView) findViewById(R.id.class_report_icon);
        mTvClassReportName = (TextView) findViewById(R.id.tv_class_report_name);
        mTvClassReportMoney = (TextView) findViewById(R.id.tv_class_report_money);
        mTxtInfo = (TextView) findViewById(R.id.txt_info);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smartRefreshLayout);
    }

    void setData() {
        mTxtTime.setText(billBean.getBillTime());
        mClassReportIcon.setResId(billBean.getResourceId());
        mClassReportIcon.greenSet();
        mTvClassReportName.setText(billBean.getBillClassName());
        mTxtInfo.setText(billBean.getInfo());
        mTvClassReportMoney.setText("￥" + MyApp.DoubleFormat(Double.parseDouble(billBean.getBillMouny())));
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true);
        mImmersionBar.init();
    }
}
