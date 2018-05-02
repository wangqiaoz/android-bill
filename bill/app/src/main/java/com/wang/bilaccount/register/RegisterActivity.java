package com.wang.bilaccount.register;

import android.animation.Animator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wang.bilaccount.R;
import com.wang.bilaccount.base.BaseActivity;
import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.util.AndroidDes3Util;
import com.wang.bilaccount.util.UUIDUtil;

import java.util.Date;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入账户名
     */
    private EditText mAccount;
    /**
     * 密码
     */
    private EditText mPassword;
    /**
     * 重新输入密码
     */
    private EditText mEdtConfimPassword;
    /**
     * 注册
     */
    private Button mBtnLogin;
    /**
     * 请输入姓名
     */
    private EditText mEdtUserName;
    /**
     * 手机号
     */
    private EditText mEdtUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        toolbar.setTitle("注册");
        mEdtUserName = (EditText) findViewById(R.id.edt_user_name);
        mEdtUserPhone = (EditText) findViewById(R.id.edt_user_phone);
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mEdtConfimPassword = (EditText) findViewById(R.id.edt_confim_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(this);
        mAccount.addTextChangedListener(new OnTextChange());
        mPassword.addTextChangedListener(new OnTextChange());
        mEdtConfimPassword.addTextChangedListener(new OnTextChange());
        mEdtUserName.addTextChangedListener(new OnTextChange());
        mEdtUserPhone.addTextChangedListener(new OnTextChange());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:
                if (mEdtUserPhone.getText().toString().length() != 11) {
                    showToast("请输入正确的手机号");
                    startAnmi(mEdtUserPhone);
                    return;
                }
                if (mPassword.getText().toString().length() < 6) {
                    showToast("密码长度太短了，不能少于6个字符");
                    startAnmi(mPassword);
                    return;
                }
                if (mPassword.getText().toString().equals(mEdtConfimPassword.getText().toString())==false) {
                    startAnmi(mPassword);
                    startAnmi(mEdtConfimPassword);
                    showToast("两次输入的密码不同，请重新输入");
                    return;
                }

                UserBean userBean = new UserBean();
                userBean.setUuid(UUIDUtil.get32UUID());
                userBean.setAccount(mAccount.getText().toString());
                userBean.setName(mEdtUserName.getText().toString());
                userBean.setPwd(AndroidDes3Util.encode(mPassword.getText().toString()));
                userBean.setPhone(mEdtUserPhone.getText().toString());
                userBean.setCreateTime(new Date(System.currentTimeMillis()));
                userBean.save();
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }


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

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
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
            if (TextUtils.isEmpty(mEdtUserPhone.getText().toString()) || TextUtils.isEmpty(mEdtUserName.getText().toString()) || TextUtils.isEmpty(mAccount.getText().toString()) || TextUtils.isEmpty(mPassword.getText().toString()) || TextUtils.isEmpty(mEdtConfimPassword.getText().toString())) {
                mBtnLogin.setEnabled(false);
            } else {
                mBtnLogin.setEnabled(true);
            }
        }
    }
}
