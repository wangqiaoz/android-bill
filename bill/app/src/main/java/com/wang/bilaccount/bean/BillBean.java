package com.wang.bilaccount.bean;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class BillBean extends BaseBean {

    /**
     * 记账的时间
     */
    private String billTime;

    private Long billTimeInt;

    /**
     * 账单类型 1 收入 ，2 支出
     */
    private String type;

    /**
     * 账单分类
     */
    private String billClassName;

    private String billClassId;

    /**
     * 账单分类 图片地址
     */
    private int resourceId;
    private int iconBg;
    /**
     * 钱数
     */
    private String billMouny;

    /**
     * 备注
     */
    private String info;


    /**
     * 用户
     */
    private String userUUid;


    private String yusuan;

    public Long getBillTimeInt() {
        return billTimeInt;
    }

    public void setBillTimeInt(Long billTimeInt) {
        this.billTimeInt = billTimeInt;
    }

    public String getYusuan() {
        return yusuan;
    }

    public void setYusuan(String yusuan) {
        this.yusuan = yusuan;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBillClassName() {
        return billClassName;
    }

    public void setBillClassName(String billClassName) {
        this.billClassName = billClassName;
    }

    public String getBillClassId() {
        return billClassId;
    }

    public void setBillClassId(String billClassId) {
        this.billClassId = billClassId;
    }

    public String getBillMouny() {
        return billMouny;
    }

    public void setBillMouny(String billMouny) {
        this.billMouny = billMouny;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserUUid() {
        return userUUid;
    }

    public void setUserUUid(String userUUid) {
        this.userUUid = userUUid;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getIconBg() {
        return iconBg;
    }

    public void setIconBg(int iconBg) {
        this.iconBg = iconBg;
    }
}
