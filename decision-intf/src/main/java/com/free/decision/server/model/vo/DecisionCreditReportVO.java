package com.free.decision.server.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 决策 查询多头 数据-行为雷达
 * @author Xingyf
 */
public class DecisionCreditReportVO implements Serializable {

    /**
     * 逾期订单数
     */
    private Integer loansOverdueCount;

    /**
     * 近1个月贷款机构失败扣款笔数
     */
    private Integer latestOneMonthFail;

    /**
     * 近1个月贷款机构成功扣款笔数
     */
    private Integer latestOneMonthSuc;

    /**
     * 历史贷款机构成功扣款笔数
     */
    private Integer historySucFee;

    /**
     * 历史贷款机构失败扣款笔数
     */
    private Integer historyFailFee;

    /**
     * 贷款行为分
     */
    private Integer loansScore;

    /**
     * 贷款置信度
     */
    private Integer loansCredibility;

    private Date createTime;

    /**
     * 贷款放款总订单数
     */
    private Integer loansCount;

    /**
     * 贷款已结清订单数
     */
    private Integer loansSettleCount;

    /**
     * 近1个月贷款笔数
     */
    private Integer latestOneMonth;

    /**
     * 最近一次贷款时间
     * @return
     */
    private Date loansLatestTime;

    public Integer getLoansOverdueCount() {
        return loansOverdueCount;
    }

    public void setLoansOverdueCount(Integer loansOverdueCount) {
        this.loansOverdueCount = loansOverdueCount;
    }

    public Integer getLatestOneMonthFail() {
        return latestOneMonthFail;
    }

    public void setLatestOneMonthFail(Integer latestOneMonthFail) {
        this.latestOneMonthFail = latestOneMonthFail;
    }

    public Integer getHistorySucFee() {
        return historySucFee;
    }

    public void setHistorySucFee(Integer historySucFee) {
        this.historySucFee = historySucFee;
    }

    public Integer getHistoryFailFee() {
        return historyFailFee;
    }

    public void setHistoryFailFee(Integer historyFailFee) {
        this.historyFailFee = historyFailFee;
    }

    public Integer getLoansScore() {
        return loansScore;
    }

    public void setLoansScore(Integer loansScore) {
        this.loansScore = loansScore;
    }

    public Integer getLoansCredibility() {
        return loansCredibility;
    }

    public void setLoansCredibility(Integer loansCredibility) {
        this.loansCredibility = loansCredibility;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLatestOneMonthSuc() {
        return latestOneMonthSuc;
    }

    public void setLatestOneMonthSuc(Integer latestOneMonthSuc) {
        this.latestOneMonthSuc = latestOneMonthSuc;
    }

    public Integer getLoansCount() {
        return loansCount;
    }

    public void setLoansCount(Integer loansCount) {
        this.loansCount = loansCount;
    }

    public Integer getLoansSettleCount() {
        return loansSettleCount;
    }

    public void setLoansSettleCount(Integer loansSettleCount) {
        this.loansSettleCount = loansSettleCount;
    }

    public Integer getLatestOneMonth() {
        return latestOneMonth;
    }

    public void setLatestOneMonth(Integer latestOneMonth) {
        this.latestOneMonth = latestOneMonth;
    }

    public Date getLoansLatestTime() {
        return loansLatestTime;
    }

    public void setLoansLatestTime(Date loansLatestTime) {
        this.loansLatestTime = loansLatestTime;
    }

    @Override
    public String toString() {
        return "DecisionCreditReportVO{" +
                "loansOverdueCount=" + loansOverdueCount +
                ", latestOneMonthFail=" + latestOneMonthFail +
                ", latestOneMonthSuc=" + latestOneMonthSuc +
                ", historySucFee=" + historySucFee +
                ", historyFailFee=" + historyFailFee +
                ", loansScore=" + loansScore +
                ", loansCredibility=" + loansCredibility +
                ", createTime=" + createTime +
                ", loansCount=" + loansCount +
                ", loansSettleCount=" + loansSettleCount +
                ", latestOneMonth=" + latestOneMonth +
                ", loansLatestTime=" + loansLatestTime +
                '}';
    }
}
