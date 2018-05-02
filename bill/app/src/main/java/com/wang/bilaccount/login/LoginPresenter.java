package com.wang.bilaccount.login;

import android.text.TextUtils;

import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.util.AndroidDes3Util;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public class LoginPresenter {

    // View接口
    private LoginView mView;

    LoginModel loginModel;

    public LoginPresenter(LoginView loginView) {
        this.mView = loginView;
        loginModel = new LoginModel();
    }

    void checkLoginStates(String account, String pwd) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            mView.setLoginBtnState(false);
        } else {
            mView.setLoginBtnState(true);
        }
    }

    void doLogin(String account, String pwd) {

        if (pwd.length() < 6) {
            mView.showToast("密码长度太短了，最少要6个字符");
            return;
        }
        mView.setLoadingDailog(true);
        String encodePwd = AndroidDes3Util.encode(pwd);
        loginModel.doLogin(account, encodePwd, new LoginModel.OnLoginListener() {
            @Override
            public void onLoginSuccess(UserBean userBean) {
                mView.setLoadingDailog(false);
                mView.loginSuccess(userBean);
            }

            @Override
            public void onLoginFail() {
                mView.showToast("帐号密码错误，请重新输入");
                mView.setLoadingDailog(false);
                mView.loginFail();
            }
        });
    }
}
