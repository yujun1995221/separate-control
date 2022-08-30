package com.free.decision.server.model;

import java.io.Serializable;

/**
 * 借款还款信息 loan_industry
 */
public class UdCreditLoanIndustry implements Serializable {

    private Long id;

    //有盾报告ID
    private Long udCreditId;

    //实际借款平台数
    private Integer actualLoanPlatformCount;

    //行业名称
    private String name;

    //申请借款平台数
    private Integer loanPlatformCount;

    //还款次数
    private Integer repaymentTimesCount;

    //还款平台数
    private Integer repaymentPlatformCount;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUdCreditId() { return udCreditId; }

    public void setUdCreditId(Long udCreditId) { this.udCreditId = udCreditId; }

    public Integer getActualLoanPlatformCount() { return actualLoanPlatformCount; }

    public void setActualLoanPlatformCount(Integer actualLoanPlatformCount) { this.actualLoanPlatformCount = actualLoanPlatformCount; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getLoanPlatformCount() { return loanPlatformCount; }

    public void setLoanPlatformCount(Integer loanPlatformCount) { this.loanPlatformCount = loanPlatformCount; }

    public Integer getRepaymentTimesCount() { return repaymentTimesCount; }

    public void setRepaymentTimesCount(Integer repaymentTimesCount) { this.repaymentTimesCount = repaymentTimesCount; }

    public Integer getRepaymentPlatformCount() { return repaymentPlatformCount; }

    public void setRepaymentPlatformCount(Integer repaymentPlatformCount) { this.repaymentPlatformCount = repaymentPlatformCount; }


    @Override
    public String toString() {
        return "UdCreditLoanIndustry{" +
                "id=" + id +
                ", udCreditId=" + udCreditId +
                ", actualLoanPlatformCount=" + actualLoanPlatformCount +
                ", name='" + name + '\'' +
                ", loanPlatformCount=" + loanPlatformCount +
                ", repaymentTimesCount=" + repaymentTimesCount +
                ", repaymentPlatformCount=" + repaymentPlatformCount +
                '}';
    }
}
