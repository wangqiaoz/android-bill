package com.wang.bilaccount.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;


import com.wang.bilaccount.R;
import com.wang.bilaccount.base.Icon;
import com.wang.bilaccount.widget.IconImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjie on 2017/11/7.
 * 类名：
 * 说明：图标icon的管理类
 * 采用 页数（index）+下标(position) 定位到图片
 */

public class SelectIconManager {
    private final static int[] icon0 = new int[]{
            R.drawable.icon1, R.drawable.icon2, R.drawable.icon3,
            R.drawable.icon4, R.drawable.icon5, R.drawable.icon6,
            R.drawable.icon7, R.drawable.icon8, R.drawable.icon9,
            R.drawable.icon10
    };

    private final static int[] iconbg0 = new int[]{
            R.color.color_ffb073, R.color.color_BBDEFB, R.color.color_9C27B0,
            R.color.color_d32f2f, R.color.color_546E7A, R.color.color_DCEDC8,
            R.color.color_FFEB3B, R.color.color_BCAAA4, R.color.color_00695C,
            R.color.color_00838F
    };

    private final static int[] icon1 = new int[]{
            R.drawable.icon11, R.drawable.icon12,
            R.drawable.icon13, R.drawable.icon14, R.drawable.icon15,
            R.drawable.icon16, R.drawable.icon17, R.drawable.icon18,
            R.drawable.icon19, R.drawable.icon20
    };
    private final static int[] iconbg1 = new int[]{
            R.color.color_18FFFF, R.color.color_2ecc71,
            R.color.color_27ae60, R.color.color_f1c40f, R.color.color_f39c12,
            R.color.color_c0392b, R.color.color_546E7A, R.color.color_BBDEFB,
            R.color.color_ffb073, R.color.color_DCEDC8
    };

    private final static int[] icon2 = new int[]{
            R.drawable.icon21, R.drawable.icon22, R.drawable.icon23, R.drawable.icon24,
            R.drawable.icon25, R.drawable.icon26, R.drawable.icon27,
            R.drawable.icon28, R.drawable.icon29, R.drawable.icon30
    };

    private final static int[] iconbg2 = new int[]{
            R.color.color_f1c40f, R.color.color_f39c12, R.color.color_c0392b, R.color.color_aa00ff,
            R.color.color_f472d0, R.color.color_d80073, R.color.color_a20025,
            R.color.color_e51400, R.color.color_fa6800, R.color.color_f0a30a
    };

    private final static int[] icon3 = new int[]{
            R.drawable.icon31, R.drawable.icon32
    };

    private final static int[] iconbg3 = new int[]{
            R.color.color_fa6800, R.color.color_a20025
    };
    private final static int[] icon4 = new int[]{
            R.drawable.icon33, R.drawable.icon34, R.drawable.icon35, R.drawable.icon36,
            R.drawable.icon37, R.drawable.icon13, R.drawable.icon24, R.drawable.icon30, R.drawable.icon28,
            R.drawable.icon32
    };

    private final static int[] iconbg4 = new int[]{
            R.color.color_e3c800, R.color.color_76608a, R.color.color_1ba1e2, R.color.color_a20025,
            R.color.color_0050ef, R.color.color_6a00ff, R.color.color_a4c400, R.color.color_008a00, R.color.color_399cff,
            R.color.color_4CAF50
    };

    /*
    * 获取单个图标
    * */
    @DrawableRes
    public static int getIconRes(int index, int position) {
        int[] icons = getIcons(index);
        if (icons != null && icons.length > 0) {
            if (position >= 0 && position < icons.length) {
                return icons[position];
            }
        }

        return 0;
    }

    /*
       * 获取单个图标
       * */
    @DrawableRes
    public static int getIconResColor(int index, int position) {
        int[] icons = getIconsColor(index);
        if (icons != null && icons.length > 0) {
            if (position >= 0 && position < icons.length) {
                return icons[position];
            } else {
                return icons[0];
            }
        }

        return R.color.color_4CAF50;
    }

    /*
    * 获取单个图标
    * */
    public static Drawable getIconDrawable(Context context, int index, int position) {
        return getIconDrawable(context, 0, index, position);
    }

    /*
   * 获取单个图标
   * */
    public static Drawable getIconDrawable(Context context, int color, int index, int position) {
        int[] icons = getIcons(index);
        if (icons != null && icons.length > 0) {
            if (position >= 0 && position < icons.length) {
                if (color == 0) {
                    return ResourceUtil.getDrawable(context, icons[position]);
                } else {
                    return SVGUtil.getSvgColorTint(icons[position], color);
                }
            }
        }
        return null;
    }


    /*
    * 获取某页的图标数组
    * */
    public static int[] getIcons(int index) {
        if (index == 0) {
            return icon0;
        } else if (index == 1) {
            return icon1;
        } else if (index == 2) {
            return icon2;
        } else if (index == 3) {
            return icon3;
        } else if (index == 4) {
            return icon4;
        }
        return icon0;
    }

    /*
   * 获取某页的图标数组
   * */
    public static int[] getIconsColor(int index) {
        if (index == 0) {
            return iconbg0;
        } else if (index == 1) {
            return iconbg1;
        } else if (index == 2) {
            return iconbg2;
        } else if (index == 3) {
            return iconbg3;
        } else if (index == 4) {
            return iconbg4;
        }
        return iconbg0;
    }

    //临时保存下控件，使用集合是为了避免内存泄露
    public static List<IconImageView> iconImageViews = new ArrayList<>(); //支出状态下的控件
    public static Icon lastSelectIcon;  //最后一次选中的图标信息

    //选中状态的UI改变
    public static void ChangesIcomUi(IconImageView icon_img, Icon icon) {
        if (iconImageViews.size() > 0 && lastSelectIcon != null) {  //把上一个选择的图标还原
            iconImageViews.get(0).ClearSet(lastSelectIcon.icon_img);
        }
        icon_img.originSet();
        iconImageViews.clear();
        iconImageViews.add(icon_img);
        lastSelectIcon = icon;
    }

    public static Icon getLastSelectIcon() {
        return lastSelectIcon;
    }

    public static void clearAll() {
        iconImageViews.clear();
        lastSelectIcon = null;
    }
}
