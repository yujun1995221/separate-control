package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 负债分析订单流水实体类
 */
public class OrderRecord implements Serializable {

    private Long id;

    private Integer financeStatus;

    private Date createTime;

    private Integer orderStatus;

    private Date cutTime;

    private Date overdueTime;

    private String idNumber;

    private Date orderCreateTime;

    private Integer companyFlag;

    private String name;

    private String phone;

    private Integer firstLend;

    public Integer getFirstLend() {
        return firstLend;
    }

    public void setFirstLend(Integer firstLend) {
        this.firstLend = firstLend;
    }

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

    public Integer getCompanyFlag() {
        return companyFlag;
    }

    public void setCompanyFlag(Integer companyFlag) {
        this.companyFlag = companyFlag;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(Integer financeStatus) {
        this.financeStatus = financeStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCutTime() {
        return cutTime;
    }

    public void setCutTime(Date cutTime) {
        this.cutTime = cutTime;
    }

    public Date getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "OrderRecord{" +
                "id=" + id +
                ", financeStatus=" + financeStatus +
                ", createTime=" + createTime +
                ", orderStatus=" + orderStatus +
                ", cutTime=" + cutTime +
                ", overdueTime=" + overdueTime +
                ", idNumber='" + idNumber + '\'' +
                ", orderCreateTime=" + orderCreateTime +
                ", companyFlag=" + companyFlag +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", firstLend=" + firstLend +
                '}';
    }
}
