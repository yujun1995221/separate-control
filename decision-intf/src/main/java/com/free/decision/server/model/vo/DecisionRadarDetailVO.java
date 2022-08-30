package com.free.decision.server.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 行为报告数据
 */
public class DecisionRadarDetailVO implements Serializable {


    private Integer loansScore;

    private Integer loansCredibility;

    private Integer loansCount;

    private Integer loansSettleCount;

    private Integer loansOverdueCount;

    private Integer loansOrgCount;

    private Integer consfinOrgCount;

    private Integer loansCashCount;

    private Integer latestOneMonth;

    private Integer latestThreeMonth;

    private Integer latestSixMonth;

    private Integer historySucFee;

    private Integer historyFailFee;

    private Integer latestOneMonthSuc;

    private Integer latestOneMonthFail;

    /**
     * 时间
     */
        private Integer loansLongTime;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date loansLatestTime;


    public Integer getLoansScore() {
        return loansScore;
    }

    @JSONField(name="loans_score")
    public void setLoansScore(Integer loansScore) {
        this.loansScore = loansScore;
    }

    public Integer getLoansCredibility() {
        return loansCredibility;
    }

    @JSONField(name="loans_credibility")
    public void setLoansCredibility(Integer loansCredibility) {
        this.loansCredibility = loansCredibility;
    }

    public Integer getLoansCount() {
        return loansCount;
    }

    @JSONField(name="loans_count")
    public void setLoansCount(Integer loansCount) {
        this.loansCount = loansCount;
    }

    public Integer getLoansSettleCount() {
        return loansSettleCount;
    }

    @JSONField(name="loans_settle_count")
    public void setLoansSettleCount(Integer loansSettleCount) {
        this.loansSettleCount = loansSettleCount;
    }

    public Integer getLoansOverdueCount() {
        return loansOverdueCount;
    }

    @JSONField(name="loans_overdue_count")
    public void setLoansOverdueCount(Integer loansOverdueCount) {
        this.loansOverdueCount = loansOverdueCount;
    }

    public Integer getLoansOrgCount() {
        return loansOrgCount;
    }

    @JSONField(name="loans_org_count")
    public void setLoansOrgCount(Integer loansOrgCount) {
        this.loansOrgCount = loansOrgCount;
    }

    public Integer getConsfinOrgCount() {
        return consfinOrgCount;
    }

    @JSONField(name="consfin_org_count")
    public void setConsfinOrgCount(Integer consfinOrgCount) {
        this.consfinOrgCount = consfinOrgCount;
    }

    public Integer getLoansCashCount() {
        return loansCashCount;
    }

    @JSONField(name="loans_cash_count")
    public void setLoansCashCount(Integer loansCashCount) {
        this.loansCashCount = loansCashCount;
    }

    public Integer getLatestOneMonth() {
        return latestOneMonth;
    }

    @JSONField(name="latest_one_month")
    public void setLatestOneMonth(Integer latestOneMonth) {
        this.latestOneMonth = latestOneMonth;
    }

    public Integer getLatestThreeMonth() {
        return latestThreeMonth;
    }

    @JSONField(name="latest_three_month")
    public void setLatestThreeMonth(Integer latestThreeMonth) {
        this.latestThreeMonth = latestThreeMonth;
    }

    public Integer getLatestSixMonth() {
        return latestSixMonth;
    }

    @JSONField(name="latest_six_month")
    public void setLatestSixMonth(Integer latestSixMonth) {
        this.latestSixMonth = latestSixMonth;
    }

    public Integer getHistorySucFee() {
        return historySucFee;
    }

    @JSONField(name="history_suc_fee")
    public void setHistorySucFee(Integer historySucFee) {
        this.historySucFee = historySucFee;
    }

    public Integer getHistoryFailFee() {
        return historyFailFee;
    }

    @JSONField(name="history_fail_fee")
    public void setHistoryFailFee(Integer historyFailFee) {
        this.historyFailFee = historyFailFee;
    }

    public Integer getLatestOneMonthSuc() {
        return latestOneMonthSuc;
    }

    @JSONField(name="latest_one_month_suc")
    public void setLatestOneMonthSuc(Integer latestOneMonthSuc) {
        this.latestOneMonthSuc = latestOneMonthSuc;
    }

    public Integer getLatestOneMonthFail() {
        return latestOneMonthFail;
    }

    @JSONField(name="latest_one_month_fail")
    public void setLatestOneMonthFail(Integer latestOneMonthFail) {
        this.latestOneMonthFail = latestOneMonthFail;
    }

    public Integer getLoansLongTime() {
        return loansLongTime;
    }

    public void setLoansLongTime(Integer loansLongTime) {
        this.loansLongTime = loansLongTime;
    }

    public Date getLoansLatestTime() {
        return loansLatestTime;
    }

    @JSONField(name="loans_latest_time")
    public void setLoansLatestTime(Date loansLatestTime) {
        this.loansLatestTime = loansLatestTime;
    }

    @Override
    public String toString() {
        return "DecisionRadarDetailVO{" +
                "loansScore=" + loansScore +
                ", loansCredibility=" + loansCredibility +
                ", loansCount=" + loansCount +
                ", loansSettleCount=" + loansSettleCount +
                ", loansOverdueCount=" + loansOverdueCount +
                ", loansOrgCount=" + loansOrgCount +
                ", consfinOrgCount=" + consfinOrgCount +
                ", loansCashCount=" + loansCashCount +
                ", latestOneMonth=" + latestOneMonth +
                ", latestThreeMonth=" + latestThreeMonth +
                ", latestSixMonth=" + latestSixMonth +
                ", historySucFee=" + historySucFee +
                ", historyFailFee=" + historyFailFee +
                ", latestOneMonthSuc=" + latestOneMonthSuc +
                ", latestOneMonthFail=" + latestOneMonthFail +
                ", loansLongTime=" + loansLongTime +
                ", loansLatestTime=" + loansLatestTime +
                '}';
    }
}
