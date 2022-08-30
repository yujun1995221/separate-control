package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 续期订单信息
 * @author Xingyf
 */
public class PersistOrderInfo implements Serializable {

    private Long id;

    private String orderNumber;

    private String persistNumber;

    private Long customerId;

    private Integer status;

    private Date mainOrderOverdueTime;

    private Date persistTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPersistNumber() {
        return persistNumber;
    }

    public void setPersistNumber(String persistNumber) {
        this.persistNumber = persistNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getMainOrderOverdueTime() {
        return mainOrderOverdueTime;
    }

    public void setMainOrderOverdueTime(Date mainOrderOverdueTime) {
        this.mainOrderOverdueTime = mainOrderOverdueTime;
    }

    public Date getPersistTime() {
        return persistTime;
    }

    public void setPersistTime(Date persistTime) {
        this.persistTime = persistTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PersistOrderInfo{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", persistNumber='" + persistNumber + '\'' +
                ", customerId=" + customerId +
                ", status=" + status +
                ", mainOrderOverdueTime=" + mainOrderOverdueTime +
                ", persistTime=" + persistTime +
                ", createTime=" + createTime +
                '}';
    }
}
