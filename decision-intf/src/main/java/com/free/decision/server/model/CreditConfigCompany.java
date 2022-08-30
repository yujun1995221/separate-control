package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

public class CreditConfigCompany implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否开启 1：开启 0：关闭
     */
    private Integer isOpen;

    private Date createTime;

    private Date updateTime;

    /**
     * code对应的值
     */
    private String val;

    private String val1;

    private String val2;

    private String val3;

    private String code;

    private String name;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreditConfigCompany{" +
                "id=" + id +
                ", configId=" + configId +
                ", companyId=" + companyId +
                ", status=" + status +
                ", isOpen=" + isOpen +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", val='" + val + '\'' +
                ", val1='" + val1 + '\'' +
                ", val2='" + val2 + '\'' +
                ", val3='" + val3 + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}