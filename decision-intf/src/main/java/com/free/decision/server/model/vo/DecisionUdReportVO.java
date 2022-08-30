package com.free.decision.server.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 决策 查询有盾反欺诈 数据
 * @author Xingyf
 */
public class DecisionUdReportVO implements Serializable {

    private Long id;

    /**
     * 风险分
     */
    private Integer riskScore;

    /**
     * 借款申请数
     */
    private Integer loanPlatformCount;

    /**
     * 还款次数
     */
    private Integer repaymentTimesCount;

    /**
     * 实际借款平台数
     */
    private Integer actualLoanPlatformCount;

    private Date createTime;

    /**
     * 近1月实际借款平台数
     */
    private Integer actualLoanPlatformCount1m;

    /**
     * 近1月申请借款平台数
     */
    private Integer loanPlatformCount1m;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public Integer getLoanPlatformCount() {
        return loanPlatformCount;
    }

    public void setLoanPlatformCount(Integer loanPlatformCount) {
        this.loanPlatformCount = loanPlatformCount;
    }

    public Integer getRepaymentTimesCount() {
        return repaymentTimesCount;
    }

    public void setRepaymentTimesCount(Integer repaymentTimesCount) {
        this.repaymentTimesCount = repaymentTimesCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getActualLoanPlatformCount() {
        return actualLoanPlatformCount;
    }

    public void setActualLoanPlatformCount(Integer actualLoanPlatformCount) {
        this.actualLoanPlatformCount = actualLoanPlatformCount;
    }

    public Integer getActualLoanPlatformCount1m() {
        return actualLoanPlatformCount1m;
    }

    public void setActualLoanPlatformCount1m(Integer actualLoanPlatformCount1m) {
        this.actualLoanPlatformCount1m = actualLoanPlatformCount1m;
    }

    public Integer getLoanPlatformCount1m() {
        return loanPlatformCount1m;
    }

    public void setLoanPlatformCount1m(Integer loanPlatformCount1m) {
        this.loanPlatformCount1m = loanPlatformCount1m;
    }

    @Override
    public String toString() {
        return "DecisionUdReportVO{" +
                "id=" + id +
                ", riskScore=" + riskScore +
                ", loanPlatformCount=" + loanPlatformCount +
                ", repaymentTimesCount=" + repaymentTimesCount +
                ", actualLoanPlatformCount=" + actualLoanPlatformCount +
                ", createTime=" + createTime +
                ", actualLoanPlatformCount1m=" + actualLoanPlatformCount1m +
                ", loanPlatformCount1m=" + loanPlatformCount1m +
                '}';
    }
}
