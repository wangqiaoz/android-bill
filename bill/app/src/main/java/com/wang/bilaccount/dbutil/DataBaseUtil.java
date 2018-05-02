package com.wang.bilaccount.dbutil;

import com.wang.bilaccount.bean.BillBean;
import com.wang.bilaccount.bean.ChartBean;
import com.wang.bilaccount.util.DateTimeUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/18/018.
 */

public class DataBaseUtil {
    static DataBaseUtil instance;

    private DataBaseUtil() {

    }

    public static DataBaseUtil getInstance() {
        if (instance == null) {
            instance = new DataBaseUtil();
        }
        return instance;
    }

    public List<ChartBean> getChart(String nianyue, int type) {
        Date date = DateTimeUtil.string2Date(nianyue, "yyyy-MM");
        String begin = "" + DateTimeUtil.getSupportBeginDayofMonth(date);
        String end = "" + DateTimeUtil.getSupportEndDayofMonth(date);
        List<ChartBean> lst = new ArrayList<>();
        List<BillBean> lstBillBean = DataSupport.where("type= ? and billTimeInt > ? and billTimeInt < ?", String.valueOf(type), begin, end).find(BillBean.class);
        List<String> lstName = new ArrayList<>();
        for (BillBean item : lstBillBean) {
            if (!lstName.contains(item.getBillClassName())) {
                lstName.add(item.getBillClassName());
            }
        }
        for (String item : lstName) {
            ChartBean chartBean = new ChartBean();
            chartBean.setTypeName(item);
            float monery = 0f;
            List<BillBean> lstBillBeanTemp = new ArrayList<>();
            for (BillBean billBean : lstBillBean) {
                if (billBean.getBillClassName().equals(item)) {
                    chartBean.setResourceId(billBean.getResourceId());
                    chartBean.setIconBg(billBean.getIconBg());
                    monery = monery + Float.parseFloat(billBean.getBillMouny());
                    lstBillBeanTemp.add(billBean);
                }
            }
            chartBean.setLst(lstBillBeanTemp);
            chartBean.setSumMonery(monery);
            lst.add(chartBean);
        }
        return lst;
    }


    public List<Double> getYearIn(String selectYear) {
        List<Double> lstTemp = new ArrayList<>();

        lstTemp.add(getSum(getChart(selectYear + "-01", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-02", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-03", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-04", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-05", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-06", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-07", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-08", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-09", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-10", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-11", 1)));
        lstTemp.add(getSum(getChart(selectYear + "-12", 1)));

        return lstTemp;
    }

    public List<Double> getYearOut(String selectYear) {
        List<Double> lstTemp = new ArrayList<>();

        lstTemp.add(getSum(getChart(selectYear + "-01", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-02", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-03", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-04", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-05", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-06", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-07", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-08", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-09", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-10", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-11", 2)));
        lstTemp.add(getSum(getChart(selectYear + "-12", 2)));

        return lstTemp;
    }

    private Double getSum(List<ChartBean> lstOne) {
        if (lstOne == null || lstOne.size() == 0) {
            return 0d;
        } else {
            double temp = 0d;
            for (ChartBean item : lstOne) {
                temp = temp + item.getSumMonery();
            }
            return temp;
        }
    }
}
