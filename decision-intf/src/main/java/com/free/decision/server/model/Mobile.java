package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

public class Mobile implements Serializable {
    private Long id;
    //用户id
    private Long userId;
    //运营商报告回调地址
    private String callBackUrl;

    /**
     * 用户通讯录
     * @return
     */
    private String phoneList;
    //用户姓名
    private String name;
    //额外参数
    private String extraInfo;
    //运营商报告状态
    private Integer reportStatus;
    //原始数据状态
    private Integer originalStatus;
    //手机号
    private String mobile;
    //身份证号
    private String idNumber;
    //运营商报告
    private String reportData;
    //运营商原始数据
    private String originalData;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    /**
     * 决策运营商返回的唯一标识
     */
    private String extend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getOriginalStatus() {
        return originalStatus;
    }

    public void setOriginalStatus(Integer originalStatus) {
        this.originalStatus = originalStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
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

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(String phoneList) {
        this.phoneList = phoneList;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "id=" + id +
                ", userId=" + userId +
                ", callBackUrl='" + callBackUrl + '\'' +
                ", phoneList='" + phoneList + '\'' +
                ", name='" + name + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                ", reportStatus=" + reportStatus +
                ", originalStatus=" + originalStatus +
                ", mobile='" + mobile + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", reportData='" + reportData + '\'' +
                ", originalData='" + originalData + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", extend='" + extend + '\'' +
                '}';
    }
}
