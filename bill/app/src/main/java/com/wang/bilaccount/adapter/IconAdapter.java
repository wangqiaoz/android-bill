package com.wang.bilaccount.adapter;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wang.bilaccount.R;
import com.wang.bilaccount.base.Icon;
import com.wang.bilaccount.util.SelectIconManager;
import com.wang.bilaccount.widget.IconImageView;

import java.util.List;

/**
 * Created by huangjie on 2017/11/6.
 * 类名：
 * 说明：图标适配器
 * 适配器中不应该做数据查询工作，把结果判断好传入进来
 */

public class IconAdapter extends BaseQuickAdapter<Icon, BaseViewHolder> {
    private OnIconClickListence l;
    private IconImageView icon_img;
    private LinearLayout item_root;
    private int type = 1;

    public IconAdapter(@Nullable List<Icon> data, int type) {
        super(R.layout.icon_item, data);
        this.type = type;
    }

    public void setOnIconClickListence(OnIconClickListence L) {
        this.l = L;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Icon item) {
        final IconImageView icon_img = helper.getView(R.id.iv_icon_img);
        final TextView icon_name = helper.getView(R.id.tv_icon_name);
        final LinearLayout item_root = helper.getView(R.id.item_root);
        this.icon_img = icon_img;
        this.item_root = item_root;
        icon_img.setResId(item.icon_img);
        item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.iconType != 3) {
                    SelectIconManager.ChangesIcomUi(icon_img, item);
                }
                if (l != null) {
                    item.position = helper.getAdapterPosition();
                    l.iconClick(item);
                }
            }
        });
        judge(item.index == 0 && helper.getAdapterPosition() == 0);

        icon_name.setText(item.icon_name);
    }


    //选中
    private void select() {
        if (type == 1) {
            icon_img.originSet();
        } else {
            icon_img.greenSet();
        }

        item_root.performClick();
    }

    //未选中
    private void clearSelect() {
        icon_img.ClearSet();
    }

    //判断状态
    private void judge(boolean isSatisfied) {
        if (isSatisfied) {
            select();
        } else {
            clearSelect();
        }
    }

    public interface OnIconClickListence {
        void iconClick(Icon icon);
    }
}
