package com.wang.bilaccount.login;

import android.os.Handler;

import com.wang.bilaccount.bean.UserBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public class LoginModel {

    public LoginModel() {

    }

    public void doLogin(final String username, final String password, final OnLoginListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserBean userBean = DataSupport.where("account = ? and pwd= ?", username, password).findFirst(UserBean.class);
                if (userBean == null) {
                    listener.onLoginFail();
                } else {
                    listener.onLoginSuccess(userBean);
                }
            }
        }, 2000);
    }

    interface OnLoginListener {
        void onLoginSuccess(UserBean userBean);

        void onLoginFail();
    }
}
