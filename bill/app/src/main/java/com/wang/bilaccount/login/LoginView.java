package com.wang.bilaccount.login;

import com.wang.bilaccount.bean.UserBean;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public interface LoginView {

    void showToast(String msg);

    void setLoginBtnState(boolean flag);

    void setLoadingDailog(boolean isShow);

    void loginSuccess(UserBean userBean);

    void loginFail();
}
