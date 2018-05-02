package com.wang.bilaccount;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wang.bilaccount.base.BaseActivity;
import com.wang.bilaccount.base.MyApp;
import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.cons.SpCons;
import com.wang.bilaccount.login.LoginActivity;
import com.wang.bilaccount.util.SPUtils;

import org.litepal.crud.DataSupport;

public class SplashActivity extends BaseActivity {

    /**
     * 小小记账本
     */
    private TextView mTxtAppName;
    private TextView mTxtVersion;

    boolean frist = true;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
//        TextView textview = (TextView)findViewById(R.id.like);
//        textview.setTypeface(iconfont);
        initView();
        checkLogin();
    }

    private void initView() {
        mTxtAppName = (TextView) findViewById(R.id.txt_app_name);
        mTxtVersion = (TextView) findViewById(R.id.txt_version);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "wwfoot.ttf");
        mTxtAppName.setTypeface(typeface);
        startPropertyAnim(mTxtAppName, 0f, 1f, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                frist = false;
                startPropertyAnim(mTxtAppName, 1f, 0f, 2000);
            }
        }, 2000);
    }


    void checkLogin() {
        String account = (String) SPUtils.get(this, SpCons.ACCOUNT, "");
        if (TextUtils.isEmpty(account)) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
            MyApp.userBean = DataSupport.where("uuid = ?", (String) SPUtils.get(this, SpCons.ACCOUNT, "")).findFirst(UserBean.class);
        }
    }

    // 核心关键点，此处将实现属性动画的动画实际执行
    private void startPropertyAnim(View view, Float start, Float end, int duration) {
        // 将直接把TextView这个view对象的透明度渐变。
        // 注意第二个参数："alpha"，指明了是透明度渐变属性动画
        // 透明度变化从1—>0.1—>1—>0.5—>1，TextView对象经历4次透明度渐变
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", start, end);

        anim.setDuration(duration);// 动画持续时间

        // 这里是一个回调监听，获取属性动画在执行期间的具体值
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                if (frist == false && value == 0) {
                    startActivity(intent);
                    finish();
                }

            }

        });

        anim.start();
    }
}
