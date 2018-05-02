package com.wang.bilaccount.fragment.home;

import android.text.TextUtils;

import com.wang.bilaccount.base.MyApp;
import com.wang.bilaccount.bean.BillBean;
import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.util.DateTimeUtil;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class HomePresenter {

    HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    public void getData(UserBean userBean) {

        String in = "暂无收入";
        String out = "暂无支出";
        List<BillBean> lstThisMothIn = DataSupport.where("billTimeInt > ? and userUUid = ?", "" + DateTimeUtil.getSupportBeginDayofMonth(new Date(System.currentTimeMillis())), userBean.getUuid()).find(BillBean.class);
        double thisMounthMounyIn = 0d;
        double thisMounthMounyout = 0d;
        for (BillBean billBean : lstThisMothIn) {
            if (!TextUtils.isEmpty(billBean.getBillMouny())) {
                if ("1".equals(billBean.getType())) {
                    thisMounthMounyIn = thisMounthMounyIn + Double.parseDouble(billBean.getBillMouny());
                } else {
                    thisMounthMounyout = thisMounthMounyout + Double.parseDouble(billBean.getBillMouny());
                }

            }
        }
        if (thisMounthMounyIn != 0d) {
            in = "￥" + MyApp.DoubleFormat(thisMounthMounyIn);

        }
        if (thisMounthMounyout != 0d) {
            out = "￥" + MyApp.DoubleFormat(thisMounthMounyout);
        }

        List<BillBean> lstThisThreeDay = DataSupport.where("billTimeInt > ? ", "" + DateTimeUtil.getDateStr(System.currentTimeMillis(), 3)).find(BillBean.class);
        homeView.show(in, out, lstThisThreeDay);
    }
}
