package com.wang.bilaccount.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wang.bilaccount.R;
import com.wang.bilaccount.base.BaseFragment;
import com.wang.bilaccount.bean.UserBean;
import com.wang.bilaccount.cons.SpCons;
import com.wang.bilaccount.login.LoginActivity;
import com.wang.bilaccount.util.SPUtils;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {


    private View view;
    /**
     * wang
     */
    private TextView mTxtName;
    private TextView mTxtPhone;
    /**
     * 退出登录
     */
    private Button mBtnLogin;
    private SmartRefreshLayout mSmartRefreshLayout;
    /**
     * wang
     */
    private TextView mTxtAccount;

    public MyFragment() {

    }


    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    UserBean userBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);

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

        mTxtName = (TextView) view.findViewById(R.id.txt_name);
        mTxtPhone = (TextView) view.findViewById(R.id.txt_phone);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
        mTxtAccount = (TextView) view.findViewById(R.id.txt_account);
        mBtnLogin.setOnClickListener(this);
        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        userBean = DataSupport.where("uuid = ?", (String) SPUtils.get(getContext(), SpCons.ACCOUNT, "")).findFirst(UserBean.class);
        mTxtName.setText(userBean.getName());
        mTxtPhone.setText(userBean.getPhone());
        mTxtAccount.setText(userBean.getAccount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:
                SPUtils.clear(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
