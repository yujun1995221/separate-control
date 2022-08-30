package com.free.decision.server.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 决策 查询运营商 数据
 * @author Xingyf
 */
public class DecisionMobileReportVO implements Serializable {

    /**
     * 运营商报告数据
     */
    private String reportData;

    /**
     * 原始数据
     */
    private String originalData;

    private Date createTime;

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

    @Override
    public String toString() {
        return "DecisionMobileReportVO{" +
                "reportData='" + reportData + '\'' +
                ", originalData='" + originalData + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
