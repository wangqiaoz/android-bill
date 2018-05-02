package com.wang.bilaccount.bean;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/17/017.
 */

public class BaseBean extends DataSupport {

    /**
     * id
     */
    protected Integer id;

    protected String uuid;

    /**
     * 创建时间       db_column: create_time
     */
    private Date createTime;
    /**
     * 修改时间       db_column: modify_time
     */
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
