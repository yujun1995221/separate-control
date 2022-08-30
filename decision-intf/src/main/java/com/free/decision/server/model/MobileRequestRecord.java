package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 运营商请求记录
 */
public class MobileRequestRecord implements Serializable {
    //id
    private long id;
    //请求的订单号
    private String orderNumber;
    //公司id
    private long companyId;
    //用户id
    private long userId;
    //状态
    private Integer status;
    //结果
    private String result;
    //请求参数
    private String requestParams;
    //请求类型
    private Integer requestType;
    //请求订单号
    private Integer requestOrderNumber;
    //创建时间
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getRequestOrderNumber() {
        return requestOrderNumber;
    }

    public void setRequestOrderNumber(Integer requestOrderNumber) {
        this.requestOrderNumber = requestOrderNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MobileRequestRecord{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", companyId=" + companyId +
                ", userId=" + userId +
                ", status=" + status +
                ", result='" + result + '\'' +
                ", requestParams='" + requestParams + '\'' +
                ", requestType=" + requestType +
                ", requestOrderNumber=" + requestOrderNumber +
                ", createTime=" + createTime +
                '}';
    }
}
