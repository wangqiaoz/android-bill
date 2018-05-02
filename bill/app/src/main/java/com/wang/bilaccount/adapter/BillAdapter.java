package com.wang.bilaccount.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.bilaccount.BillDetailActivity;
import com.wang.bilaccount.R;
import com.wang.bilaccount.bean.BillBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {


    List<BillBean> lst;
    Context context;
    LayoutInflater inflater;

    public BillAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        lst = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.bill_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BillBean billBean = lst.get(position);
        holder.mTxtClass.setText(billBean.getBillClassName());
        holder.mImgIcon.setImageResource(billBean.getResourceId());
        holder.mTxtCount.setText("￥" + billBean.getBillMouny());
        if ("1".equals(billBean.getType())) {
            holder.mTxtCount.setTextColor(Color.parseColor("#43C39E"));
        } else {
            holder.mTxtCount.setTextColor(Color.parseColor("#FF6B6B"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillDetailActivity.class);
                intent.putExtra("uuid", billBean.getUuid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgIcon;
        TextView mTxtClass;
        TextView mTxtCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImgIcon = (ImageView) itemView.findViewById(R.id.img_icon);
            mTxtClass = (TextView) itemView.findViewById(R.id.txt_class);
            mTxtCount = (TextView) itemView.findViewById(R.id.txt_count);
        }
    }

    public void setData(List<BillBean> lstTemp) {
        lst.clear();
        if (lstTemp == null) {
            return;
        }
        lst.addAll(lstTemp);
        notifyDataSetChanged();
    }
}
