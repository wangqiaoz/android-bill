package com.wang.bilaccount.fragment.home;

import com.wang.bilaccount.bean.BillBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public interface HomeView {
    void show(String shouru, String zhichu, List<BillBean> lst);
}
