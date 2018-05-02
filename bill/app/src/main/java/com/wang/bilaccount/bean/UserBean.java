package com.wang.bilaccount.bean;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class UserBean extends BaseBean {

    /**
     * 姓名       db_column: name
     */
    private String name;
    /**
     * 电话       db_column: phone
     */
    private String phone;

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 预算
     */
    private String yusuan;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getYusuan() {
        return yusuan;
    }

    public void setYusuan(String yusuan) {
        this.yusuan = yusuan;
    }
}
