package com.wang.bilaccount.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.wang.bilaccount.R;
import com.wang.bilaccount.adapter.BillAdapter;
import com.wang.bilaccount.base.BaseFragment;
import com.wang.bilaccount.base.MyApp;
import com.wang.bilaccount.bean.BillBean;
import com.wang.bilaccount.bean.ChartBean;
import com.wang.bilaccount.dbutil.DataBaseUtil;
import com.wang.bilaccount.util.DateTimeUtil;
import com.wang.bilaccount.util.PieChartUtil;
import com.wang.bilaccount.util.ResourceUtil;
import com.wang.bilaccount.widget.IconImageView;
import com.wang.bilaccount.widget.LineChartView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class BillFragment extends BaseFragment implements View.OnClickListener {


    private View view;
    /**
     * 2017年
     */
    private TextView mTvMainYear;
    //    /**
//     * 11月
//     */
//    private TextView mTvMainMonth;
    private LinearLayout mLlChooseDate;
    private PieChart mChart;
    private View mViewTriangle;
    private IconImageView mClassReportIcon;
    /**
     * 购物
     */
    private TextView mTvClassReportName;
    private TextView mTvClassReportPersent;
    private ImageView mIvClassReportBack;
    private TextView mTvClassReportMoney;

    private RecyclerView mRecyclerRanking;
    private LinearLayout mLlBottomDetail;

    private boolean isLoad;
    private List<ChartBean> filtes;
    private PieChart mPieCharts;
    /**
     * 总支出
     */
    private TextView mCenterTitle;
    /**
     * 0.00
     */
    private TextView mCenterMoney;
    private ImageView mCenterImg;
    BillAdapter billAdapter;
    LinearLayoutManager linearLayoutManager;
    String selectDate;
    String selectYear;
    private LinearLayout mLayoutCenter;


    private RelativeLayout mRenPieChart;
    /**
     * 年
     */
    private TextView mTbNoteYear;
    /**
     * 月
     */
    private TextView mTbNoteMouth;
    private LineChartView mLineChart;
    private LinearLayout mLinLineChart;
    private TextView mSelectMouth;
    private TextView mTxtYearOut;
    private TextView mTxtYearIn;
//    /**
//     * 年
//     */
//    private TextView mTbNoteOutcome;
//    /**
//     * 月
//     */
//    private TextView mTbNoteIncome;

    public BillFragment() {

    }

    /**
     * 账单类型 1 总收入 2总支出
     */
    int type = 2;

    /**
     * 1 月 ，2年
     */
    int dateType = 1;

    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        initView(view);
//        refresh();
        initData();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void refresh() {
        initData();
    }

    private void initView(View view) {

        mTvMainYear = (TextView) view.findViewById(R.id.tv_main_year);
//        mTvMainMonth = (TextView) view.findViewById(R.id.tv_main_month);
        mLlChooseDate = (LinearLayout) view.findViewById(R.id.ll_choose_date);
        mChart = (PieChart) view.findViewById(R.id.pie_charts);
        mViewTriangle = (View) view.findViewById(R.id.view_triangle);
        mClassReportIcon = (IconImageView) view.findViewById(R.id.class_report_icon);
        mTvClassReportName = (TextView) view.findViewById(R.id.tv_class_report_name);
        mTvClassReportPersent = (TextView) view.findViewById(R.id.tv_class_report_persent);
        mIvClassReportBack = (ImageView) view.findViewById(R.id.iv_class_report_back);
        mTvClassReportMoney = (TextView) view.findViewById(R.id.tv_class_report_money);

        mRecyclerRanking = (RecyclerView) view.findViewById(R.id.recycler_ranking);
        billAdapter = new BillAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerRanking.setLayoutManager(linearLayoutManager);
        mRecyclerRanking.setAdapter(billAdapter);
        mRecyclerRanking.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mLlBottomDetail = (LinearLayout) view.findViewById(R.id.ll_bottom_detail);
        mLlChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePick();
            }
        });

        Calendar c = Calendar.getInstance();//
        Date date = new Date(System.currentTimeMillis());
        c.setTime(date);
        mTvMainYear.setText(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月"); // 获取当前年份
        selectDate = DateTimeUtil.date2String(date.getTime(), "yyyy-MM");
        selectYear = DateTimeUtil.date2String(date.getTime(), "yyyy");
        PieChartUtil.initPieChart(mChart);
        mCenterTitle = (TextView) view.findViewById(R.id.center_title);
        mCenterMoney = (TextView) view.findViewById(R.id.center_money);
        mCenterImg = (ImageView) view.findViewById(R.id.center_img);
        mLayoutCenter = (LinearLayout) view.findViewById(R.id.layout_center);
        mLayoutCenter.setOnClickListener(this);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                float[] mDrawAngles = mChart.getDrawAngles();
                float[] mAbsoluteAngles = mChart.getAbsoluteAngles();
                float start = mChart.getRotationAngle();
                int i = (int) h.getX();
                float offset = mDrawAngles[i] / 2;
                float end = 90 - (mAbsoluteAngles[i] - offset);

                spin(start, end, i);
            }

            @Override
            public void onNothingSelected() {

            }
        });


        mRenPieChart = (RelativeLayout) view.findViewById(R.id.ren_pie_chart);
        mTbNoteYear = (TextView) view.findViewById(R.id.tb_note_year);
        mTbNoteMouth = (TextView) view.findViewById(R.id.tb_note_mouth);
//        mTbNoteOutcome = (TextView) view.findViewById(R.id.tb_note_outcome);
//        mTbNoteIncome = (TextView) view.findViewById(R.id.tb_note_income);
        mTbNoteYear.setOnClickListener(this);
        mTbNoteMouth.setOnClickListener(this);
//        mTbNoteOutcome.setOnClickListener(this);
//        mTbNoteIncome.setOnClickListener(this);
//        mTbNoteOutcome.setSelected(true);
        mTbNoteMouth.setSelected(true);
        mLineChart = (LineChartView) view.findViewById(R.id.line_chart);
        mLineChart.setLineClick(new LineChartView.LineClick() {
            @Override
            public void onClick(Double in, Double out) {
                mSelectMouth.setText("收入：" + MyApp.DoubleFormat(in) + "         支出：" + MyApp.DoubleFormat(out));
            }
        });
        mLinLineChart = (LinearLayout) view.findViewById(R.id.lin_line_chart);
        mSelectMouth = (TextView) view.findViewById(R.id.select_mouth);
        mTxtYearOut = (TextView) view.findViewById(R.id.txt_year_out);
        mTxtYearIn = (TextView) view.findViewById(R.id.txt_year_in);
    }

    private void spin(float fromangle, float toangle, final int i) {
        mChart.setRotationAngle(fromangle);
        ObjectAnimator spinAnimator = ObjectAnimator.ofFloat(mChart, "rotationAngle", fromangle, toangle);
        spinAnimator.setDuration(1500);
        spinAnimator.setInterpolator(Easing.getEasingFunctionFromOption(Easing.EasingOption.EaseInOutQuad));
        spinAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChart.postInvalidate();
            }
        });
        spinAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                initList(i);
                showIndicatorAnim();
            }
        });
        spinAnimator.start();
    }

    void initData() {

        mLlBottomDetail.setVisibility(View.GONE);
        mViewTriangle.setVisibility(View.GONE);
        if (dateType == 1) {
            if (type == 1) {
                mCenterTitle.setText("总收入");
                mCenterImg.setImageResource(R.mipmap.tallybook_output);
            } else {
                mCenterTitle.setText("总支出");
                mCenterImg.setImageResource(R.mipmap.tallybook_input);
            }
            filtes = DataBaseUtil.getInstance().getChart(selectDate, type);
            ArrayList<PieEntry> entries1 = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            int count = filtes.size();
            if (count == 0) {
                entries1.add(new PieEntry(1f));
                colors.add(0xffAAAAAA);
            }
            float sumMoney = 0f;
            for (int i = 0; i < count; i++) {
                ChartBean value = filtes.get(i);
                sumMoney = sumMoney + value.getSumMonery();
                Drawable drawable = ResourceUtil.getDrawable(getContext(), value.getResourceId());
                entries1.add(new PieEntry(value.getSumMonery(), "", drawable));
                colors.add(getResources().getColor(value.getIconBg()));
            }
            mCenterMoney.setText(MyApp.DoubleFormat(sumMoney));
            PieChartUtil.setPieChartData(mChart, entries1, colors);
            List<BillBean> lst = new ArrayList<>();
            for (ChartBean item : filtes) {
                lst.addAll(item.getLst());
            }
            if (lst.size() > 0) {
                mLlBottomDetail.setVisibility(View.VISIBLE);
                mViewTriangle.setVisibility(View.VISIBLE);
                billAdapter.setData(lst);
                showIndicatorAnim();
            }
        }
        if (dateType == 2) {
            List<Double> lstIn = DataBaseUtil.getInstance().getYearIn(selectYear);
            List<Double> lstOut = DataBaseUtil.getInstance().getYearOut(selectYear);
            Double doubeIn = 0d;
            for (Double item : lstIn) {
                doubeIn = doubeIn + item;
            }
            mTxtYearIn.setText("￥" + MyApp.DoubleFormat(doubeIn));

            Double doubeOut = 0d;
            for (Double item : lstOut) {
                doubeOut = doubeOut + item;
            }
            mTxtYearOut.setText("￥" + MyApp.DoubleFormat(doubeOut));

            mLineChart.setInAndOut(lstIn, lstOut);
        }
    }

    void showDatePick() {

        DatePickDialog dialog = new DatePickDialog(getContext());
        //设置标题
        dialog.setTitle("选择时间");


        if (dateType == 1) {

            dialog.setType(DateType.TYPE_YM);
            dialog.setMessageFormat("yyyy-MM");
        } else {
            dialog.setType(DateType.TYPE_Y);
            dialog.setMessageFormat("yyyy");
        }

        //设置选择回调
        dialog.setOnChangeLisener(null);

        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                Calendar c = Calendar.getInstance();//
                c.setTime(date);
                if (dateType == 1) {
                    mTvMainYear.setText(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月"); // 获取当前年份
//                    mTvMainMonth.setText();
//                    mTvMainMonth.setVisibility(View.VISIBLE);
                    selectDate = DateTimeUtil.date2String(date.getTime(), "yyyy-MM");
                } else {
                    mTvMainYear.setText(c.get(Calendar.YEAR) + "年"); // 获取当前年份
//                    mTvMainMonth.setText((c.get(Calendar.MONTH) + 1) + "月");
//                    mTvMainMonth.setVisibility(View.GONE);
                    selectYear = DateTimeUtil.date2String(date.getTime(), "yyyy");
                }

                initData();
            }
        });
        dialog.show();
    }

    void initList(int index) {
        if (filtes.size() > index) {
            mLlBottomDetail.setVisibility(View.VISIBLE);
            mViewTriangle.setVisibility(View.VISIBLE);
            ChartBean chartBean = filtes.get(index);
            mClassReportIcon.setResId(chartBean.getResourceId());
            mClassReportIcon.originSet();
            mTvClassReportMoney.setText(MyApp.DoubleFormat(chartBean.getSumMonery()));
            mTvClassReportName.setText(chartBean.getTypeName());
            billAdapter.setData(chartBean.getLst());
        }

    }

    private void showIndicatorAnim() {
        ValueAnimator animator = ObjectAnimator.ofFloat(mLlBottomDetail, "alpha", 0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                mViewTriangle.setAlpha(alpha);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.layout_center:
                if (type == 1) {
                    type = 2;
                } else {
                    type = 1;
                }
                initData();
                break;
//            case R.id.tb_note_income:
//                mTbNoteOutcome.setSelected(false);
//                mTbNoteIncome.setSelected(true);
//                type = 1;
//                initData();
//                break;
            case R.id.tb_note_year://年
                mTbNoteYear.setSelected(true);
                mTbNoteMouth.setSelected(false);
                mRenPieChart.setVisibility(View.GONE);
                mLinLineChart.setVisibility(View.VISIBLE);
                mLlBottomDetail.setVisibility(View.GONE);
                mViewTriangle.setVisibility(View.GONE);
                dateType = 2;
                mTvMainYear.setText(selectYear + "年");
                initData();
                break;
            case R.id.tb_note_mouth://月
                mTbNoteYear.setSelected(false);
                mTbNoteMouth.setSelected(true);
                mRenPieChart.setVisibility(View.VISIBLE);
                mLinLineChart.setVisibility(View.GONE);
                mLlBottomDetail.setVisibility(View.GONE);
                mViewTriangle.setVisibility(View.GONE);
                mTvMainYear.setText(DateTimeUtil.stringFromat(selectDate, "yyyy-MM", "yyyy年MM月"));
                dateType = 1;
                initData();
                break;
        }
    }
}
