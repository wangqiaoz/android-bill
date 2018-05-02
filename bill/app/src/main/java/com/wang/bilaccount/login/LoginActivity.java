package com.wang.bilaccount.login;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gyf.barlibrary.ImmersionBar;
import com.wang.bilaccount.MainActivity;
import com.wang.bilaccount.R;
import com.wang.bilaccount.base.BaseActivity;
import com.wang.bilaccount.base.MyApp;
import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.cons.SpCons;
import com.wang.bilaccount.register.RegisterActivity;
import com.wang.bilaccount.util.SPUtils;
import com.wang.bilaccount.widget.LoadingDailog;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    LoginPresenter loginPresenter;
    /**
     * 请输入账户名
     */
    private EditText mAccount;
    /**
     * 密码
     */
    private EditText mPassword;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 注册
     */
    private TextView mTxtRegist;

    LoadingDailog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        initView();
    }

    @Override
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    void startAnmi(View view) {
        YoYo.with(Techniques.Shake)
                .duration(500)
                .repeat(1)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(view);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true);
        mImmersionBar.init();
    }

    @Override
    public void setLoginBtnState(boolean flag) {
        mBtnLogin.setClickable(flag);
        mBtnLogin.setEnabled(flag);
    }

    @Override
    public void setLoadingDailog(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        MyApp.userBean = userBean;
        SPUtils.put(this, SpCons.ACCOUNT, userBean.getUuid());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail() {
        mPassword.setText("");
        startAnmi(mPassword);
        startAnmi(mAccount);
        Toast.makeText(this, "账户密码错误", Toast.LENGTH_SHORT).show();
    }


    private void initView() {
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mTxtRegist = (TextView) findViewById(R.id.txt_regist);
        mTxtRegist.setOnClickListener(this);
        mAccount.addTextChangedListener(new OnTextChange());
        mPassword.addTextChangedListener(new OnTextChange());
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("登录中...")
                .setCancelable(false)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:
                loginPresenter.doLogin(mAccount.getText().toString(), mPassword.getText().toString());
                break;
            case R.id.txt_regist:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    class OnTextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            loginPresenter.checkLoginStates(mAccount.getText().toString(), mPassword.getText().toString());
        }
    }
}
