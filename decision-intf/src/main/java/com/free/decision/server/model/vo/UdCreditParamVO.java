package com.free.decision.server.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * 反欺诈报告参数
 */
public class UdCreditParamVO implements Serializable {

    private Long companyId;

    private String name;

    private String idNumber;

    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private String startTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private String endTime;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "UdCreditParamVO{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", mobile='" + mobile + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
