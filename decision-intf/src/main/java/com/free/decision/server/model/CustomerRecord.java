package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 负债分析订单流水实体类
 */
public class CustomerRecord implements Serializable {

    private Long id;

    private String idNumber;

    private Date customerCreateTime;

    private Date createTime;

    private Integer companyFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getCustomerCreateTime() {
        return customerCreateTime;
    }

    public void setCustomerCreateTime(Date customerCreateTime) {
        this.customerCreateTime = customerCreateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCompanyFlag() {
        return companyFlag;
    }

    public void setCompanyFlag(Integer companyFlag) {
        this.companyFlag = companyFlag;
    }

    @Override
    public String toString() {
        return "CustomerRecord{" +
                "id=" + id +
                ", idNumber='" + idNumber + '\'' +
                ", customerCreateTime=" + customerCreateTime +
                ", createTime=" + createTime +
                ", companyFlag=" + companyFlag +
                '}';
    }
}
