package com.wang.bilaccount.bean;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18/018.
 */

public class ChartBean extends ChartData<IPieDataSet> {

    String typeName;

    int resourceId;

    float sumMonery;

    int iconBg;

    List<BillBean> lst;

    public List<BillBean> getLst() {
        return lst;
    }

    public void setLst(List<BillBean> lst) {
        this.lst = lst;
    }

    public int getIconBg() {
        return iconBg;
    }

    public void setIconBg(int iconBg) {
        this.iconBg = iconBg;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public float getSumMonery() {
        return sumMonery;
    }

    public void setSumMonery(float sumMonery) {
        this.sumMonery = sumMonery;
    }
}
