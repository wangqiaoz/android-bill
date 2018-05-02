package com.wang.bilaccount.base;

import com.wang.bilaccount.bean.UserBean;

import org.litepal.LitePalApplication;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class MyApp extends LitePalApplication {
    private static MyApp baseApp;
    public static UserBean userBean;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
    }
    public static MyApp getInstence(){
        return baseApp;
    }


    public static String DoubleFormat(Double number){
        NumberFormat nf = new DecimalFormat("######0.00");
        return nf.format(number);
    }
    public static String DoubleFormat(Float number){
        NumberFormat nf = new DecimalFormat("######0.00");
        return nf.format(number);
    }
}
