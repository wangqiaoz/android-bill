package com.wang.bilaccount.fragment.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.bilaccount.R;
import com.wang.bilaccount.adapter.BillAdapter;
import com.wang.bilaccount.addbill.AddBillActivity;
import com.wang.bilaccount.base.BaseFragment;
import com.wang.bilaccount.base.MyApp;
import com.wang.bilaccount.bean.BillBean;
import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.cons.SpCons;
import com.wang.bilaccount.util.SPUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeView {


    private View view;
    /**
     * 暂无支出
     */
    private TextView mTxtBillOut;
    private ImageView mImgSee;
    /**
     * 暂无收入
     */
    private TextView mTxtBillIn;
    /**
     * 设置预算
     */
    private TextView mTxtSetAccount;
    /**
     * 记一笔
     */
    private Button mBtnBillAdd;
    /**
     * 0笔
     */
    private TextView mTxtBillCount;
    private RecyclerView mRecViewBill;
    HomePresenter homePresenter;

    private SmartRefreshLayout mSmartRefreshLayout;
    BillAdapter billAdapter;
    LinearLayoutManager linearLayoutManager;
    private ScrollView mScrollView;
    private LinearLayout mLinSetMu;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
//    private VirtualKeyboardView mVirtualKeyboardView;

    UserBean userBean;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        userBean = DataSupport.where("uuid = ?", (String) SPUtils.get(getContext(), SpCons.ACCOUNT, "")).findFirst(UserBean.class);
        initAnim();
        initView(view);
        homePresenter = new HomePresenter(this);
        refresh();
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

    private void initView(View view) {
        mTxtBillOut = (TextView) view.findViewById(R.id.txt_bill_out);
        mImgSee = (ImageView) view.findViewById(R.id.img_see);
        mTxtBillIn = (TextView) view.findViewById(R.id.txt_bill_in);
        mTxtSetAccount = (TextView) view.findViewById(R.id.txt_set_account);
        mBtnBillAdd = (Button) view.findViewById(R.id.btn_bill_add);

        mTxtBillCount = (TextView) view.findViewById(R.id.txt_bill_count);
        mRecViewBill = (RecyclerView) view.findViewById(R.id.rec_view_bill);
        billAdapter = new BillAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecViewBill.setLayoutManager(linearLayoutManager);
        mRecViewBill.setAdapter(billAdapter);
        mRecViewBill.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        mTxtSetAccount.setOnClickListener(this);
        mBtnBillAdd.setOnClickListener(this);

        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refresh();
                mSmartRefreshLayout.finishRefresh(true);
            }
        });
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        mLinSetMu = (LinearLayout) view.findViewById(R.id.lin_set_mu);
        mLinSetMu.setOnClickListener(this);
        if (TextUtils.isEmpty(userBean.getYusuan())) {
            mTxtSetAccount.setText("设置预算");
        } else {
            mTxtSetAccount.setText("预算 ￥" + userBean.getYusuan());
        }

//        mVirtualKeyboardView = (VirtualKeyboardView) view.findViewById(R.id.virtualKeyboardView);
//        mVirtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mVirtualKeyboardView.startAnimation(exitAnim);
//                mVirtualKeyboardView.setVisibility(View.GONE);
//            }
//        });
//        mTxtBillCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mVirtualKeyboardView.setFocusable(true);
//                mVirtualKeyboardView.setFocusableInTouchMode(true);
//
//                mVirtualKeyboardView.startAnimation(enterAnim);
//                mVirtualKeyboardView.setVisibility(View.VISIBLE);
//            }
//        });
//
//        mTxtBillCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus == false && mVirtualKeyboardView.getVisibility() == View.VISIBLE) {
//                    mVirtualKeyboardView.startAnimation(exitAnim);
//                    mVirtualKeyboardView.setVisibility(View.GONE);
//                }
//            }
//        });
//        gridView = mVirtualKeyboardView.getGridView();
//        gridView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_set_mu:
                final EditText inputServer = new EditText(getContext());
                inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputServer.setFocusable(true);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("输入金额").setView(inputServer).setNegativeButton(
                        "取消", null);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String inputName = inputServer.getText().toString();
                                mTxtSetAccount.setText("预算 ￥" + inputName);
                                userBean.setYusuan(inputName);
                                userBean.update(userBean.getId());

                            }
                        });
                builder.show();
                break;
            case R.id.btn_bill_add:
                Intent intent = new Intent(getContext(), AddBillActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Animation enterAnim;

    private Animation exitAnim;

    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_bottom_out);
    }

    public void refresh() {
        homePresenter.getData(userBean);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mScrollView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
//                    }
//                });
//            }
//        },10);

    }

    @Override
    public void show(String shouru, String zhichu, List<BillBean> lst) {
        mTxtBillIn.setText(shouru);
        mTxtBillOut.setText(zhichu);
        billAdapter.setData(lst);
        mTxtBillCount.setText(lst.size() + "笔");
    }


}
