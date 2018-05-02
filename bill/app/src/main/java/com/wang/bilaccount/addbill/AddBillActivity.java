package com.wang.bilaccount.addbill;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.wang.bilaccount.MainActivity;
import com.wang.bilaccount.R;
import com.wang.bilaccount.adapter.BillClassViewPageAdapter;
import com.wang.bilaccount.adapter.IconAdapter;
import com.wang.bilaccount.base.BaseActivity;
import com.wang.bilaccount.base.Icon;
import com.wang.bilaccount.bean.BillBean;
import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.cons.SpCons;
import com.wang.bilaccount.util.DateTimeUtil;
import com.wang.bilaccount.util.SPUtils;
import com.wang.bilaccount.util.UUIDUtil;
import com.wang.bilaccount.widget.VirtualKeyboardView;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddBillActivity extends BaseActivity implements View.OnClickListener, IconAdapter.OnIconClickListence {

    /**
     * 支出
     */
    private TextView mTbNoteOutcome;
    /**
     * 收入
     */
    private TextView mTbNoteIncome;
    /**
     * 0.00
     */
    private EditText mTxtBillCount;
    private ViewPager mViewpager;
    private LinearLayout mLlDotContent;
    private List<View> views = new ArrayList<>();
    private BillClassViewPageAdapter pagerAdapter;
    private int lastNum = 0;
    Context context;
    private IconPresenter presenter = new IconPresenter();
    private Animation enterAnim;

    private Animation exitAnim;
    VirtualKeyboardView virtualKeyboardView;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
    /**
     * 2017-04-18
     */
    private TextView mTxtDate;
    private LinearLayout mLinTimePick;
    /**
     * 请输入备注信息（最多140字）
     */
    private EditText mEdtInfo;
    /**
     * 再记一笔
     */
    private Button mBtnSaveTwo;
    /**
     * 保存
     */
    private Button mBtnSave;
    Icon iconSelect;

    /**
     * 账单分类
     */
    int type = 2;
    UserBean userBean;
    private VirtualKeyboardView mVirtualKeyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        context = this;
        initView();
        initViewPager(1);
        initAnim();
        initEditView();
        valueList = virtualKeyboardView.getValueList();
        mTxtDate.setText(DateTimeUtil.date2String(System.currentTimeMillis(), "yyyy年MM月dd日"));
        userBean = DataSupport.where("uuid = ?", (String) SPUtils.get(context, SpCons.ACCOUNT, "")).findFirst(UserBean.class);
    }

    void initEditView() {
        // 设置不调用系统键盘
        if (Build.VERSION.SDK_INT <= 10) {
            mTxtBillCount.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mTxtBillCount, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });
        mTxtBillCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);

                virtualKeyboardView.startAnimation(enterAnim);
                virtualKeyboardView.setVisibility(View.VISIBLE);
            }
        });

        mTxtBillCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false && virtualKeyboardView.getVisibility() == View.VISIBLE) {
                    virtualKeyboardView.startAnimation(exitAnim);
                    virtualKeyboardView.setVisibility(View.GONE);
                }
            }
        });
        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);
    }


    private void initView() {
        mTbNoteOutcome = (TextView) findViewById(R.id.tb_note_outcome);
        mTbNoteIncome = (TextView) findViewById(R.id.tb_note_income);
        mTbNoteOutcome.setSelected(true);
        mTbNoteOutcome.setOnClickListener(this);
        mTbNoteIncome.setOnClickListener(this);
        mTxtBillCount = (EditText) findViewById(R.id.txt_bill_count);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mLlDotContent = (LinearLayout) findViewById(R.id.ll_dot_content);
        mTxtDate = (TextView) findViewById(R.id.txt_date);
        mLinTimePick = (LinearLayout) findViewById(R.id.lin_time_pick);
        mLinTimePick.setOnClickListener(this);
        mEdtInfo = (EditText) findViewById(R.id.edt_info);
        mBtnSaveTwo = (Button) findViewById(R.id.btn_save_two);
        mBtnSaveTwo.setOnClickListener(this);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);
        mVirtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
    }

    private void initViewPager(int flag) {
        iconSelect = null;
        views.clear();
        if (flag == 1) {
            for (int i = 0; i < 3; i++) {
                views.add(createView());
                initRecycleView(views.get(i), i, flag);
            }
        } else {
            for (int i = 0; i < 1; i++) {
                views.add(createView());
                initRecycleView(views.get(i), 3, flag);
            }
        }
        initDot();
        pagerAdapter = new BillClassViewPageAdapter(views);
        mViewpager.setAdapter(pagerAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mLlDotContent.getChildAt(lastNum).setSelected(false);
                mLlDotContent.getChildAt(position).setSelected(true);
                lastNum = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewpager.setOffscreenPageLimit(views.size());

    }

    private void initDot() {
        mLlDotContent.removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(DensityUtil.dp2px(7), DensityUtil.dp2px(7f));
            imageView.setImageResource(R.drawable.dot_select);
            ll.leftMargin = DensityUtil.dp2px(6);
            imageView.setLayoutParams(ll);
            mLlDotContent.addView(imageView);
        }
    }

    private void initRecycleView(View view, int index, int flag) {
        RecyclerView recyclerView = view.findViewById(R.id.rlv_icon_view);
        List<Icon> data = presenter.getData(index);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        IconAdapter mAdapter = new IconAdapter(data, flag);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnIconClickListence(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tb_note_outcome:
                mTbNoteOutcome.setSelected(true);
                mTbNoteIncome.setSelected(false);
                initViewPager(1);
                type = 2;
                break;
            case R.id.tb_note_income:
                mTbNoteOutcome.setSelected(false);
                mTbNoteIncome.setSelected(true);
                initViewPager(2);
                type = 1;
                break;
            case R.id.lin_time_pick:
                showDatePick();
                break;
            case R.id.btn_save_two:
                if (save()) {
                    showToast("记账成功");
                    mTxtBillCount.setText("");
                    mEdtInfo.setText("");
                    mTxtBillCount.performClick();
                    mTxtBillCount.requestFocus();
                    mTxtDate.setText(DateTimeUtil.date2String(System.currentTimeMillis(), "yyyy年MM月dd日"));

                }
                break;
            case R.id.btn_save:
                if (save()) {
                    showToast("记账成功");
                    finish();
                }
                break;
        }
    }

    boolean save() {
        if (TextUtils.isEmpty(mTxtBillCount.getText().toString())) {
            showToast("请输入金额");
            return false;
        }
        if (iconSelect == null) {
            showToast("请选择类别");
            return false;
        }
        BillBean billBean = new BillBean();
        billBean.setUserUUid(userBean.getUuid());
        billBean.setUuid(UUIDUtil.get32UUID());
        billBean.setBillMouny(mTxtBillCount.getText().toString());
        billBean.setBillTime(mTxtDate.getText().toString());
        billBean.setBillTimeInt(DateTimeUtil.getTimeLong(mTxtDate.getText().toString(), "yyyy年MM月dd日"));
        billBean.setBillClassName(iconSelect.icon_name);
        billBean.setIconBg(iconSelect.icon_bg);
        billBean.setResourceId(iconSelect.icon_img);
        billBean.setType(String.valueOf(type));
        billBean.setInfo(mEdtInfo.getText().toString());
        billBean.save();
        Intent intent = new Intent();
        intent.setAction(MainActivity.ARCHIVES_REFRESH);
        sendBroadcast(intent);
        return true;
    }

    void showDatePick() {
        DatePickDialog dialog = new DatePickDialog(this);

        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd");
        //设置选择回调
        dialog.setOnChangeLisener(null);

        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                mTxtDate.setText(DateTimeUtil.date2String(date.getTime(), "yyyy年MM月dd日"));
            }
        });
        dialog.show();
    }

    private View createView() {
        return LayoutInflater.from(context).inflate(R.layout.pen_icon_fragment_layout, mViewpager, false);
    }

    @Override
    public void iconClick(Icon icon) {
        iconSelect = icon;
    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            String text = mTxtBillCount.getText().toString();
            if (TextUtils.isEmpty(text) && valueList.get(position).get("name").equals(".")) {
                return;
            }

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = mTxtBillCount.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                mTxtBillCount.setText(amount);

                Editable ea = mTxtBillCount.getText();
                mTxtBillCount.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount = mTxtBillCount.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        mTxtBillCount.setText(amount);

                        Editable ea = mTxtBillCount.getText();
                        mTxtBillCount.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = mTxtBillCount.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        mTxtBillCount.setText(amount);

                        Editable ea = mTxtBillCount.getText();
                        mTxtBillCount.setSelection(ea.length());
                    }
                }
            }
        }
    };

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true);
        mImmersionBar.init();
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
