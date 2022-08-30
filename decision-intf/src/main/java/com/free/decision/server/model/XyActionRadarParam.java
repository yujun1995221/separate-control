package com.free.decision.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 行为雷达
 */
public class XyActionRadarParam implements Serializable {


    private Long id;

    /**
     * 报告编号
     */
    private String code; //查看报告时异步生成


    private  Long userId;

    /**
     * 身份证号
     */
    private String  idNo;

    /**
     * 姓名
     */
    private String idName;

    /**
     * 业务版本号
     */
    private String versions;

    /**
     * 贷款行为分:1-1000分表示新颜征信对于该用户的贷款行为的评估
     */
    private Integer loansScore;

    /**
     * 贷款行为置信度:1-100表示新颜征信计算出来的贷款行为分的置信度
     */
    private Integer loansCredibility;

    /**
     * 贷款放款总订单数:1-N表示命中查询主体的总贷款放款订单数(12个月内)
     */
    private Integer loansCount;

    /**
     * 贷款已结清订单数:1-N表示命中查询主体的已结清贷款订单数(12个月内)
     */
    private Integer loansSettleCount;
    /**
     * 网络贷款类机构数:1-N表示命中查询主体贷款放款的网络贷款类机构数(12个月内)
     */
    private Integer loansOverdueCount;

    /**
     * 消费金融类机构数:1-N表示命中查询主体贷款放款的消费金融类机构数(12个月内)
     */
    private Integer loansOrgCount;


    private Integer consfinOrgCount;

    /**
     * 网络贷款类机构数:1-N表示命中查询主体贷款放款的网络贷款类机构数(12个月内)
     */
    private Integer loansCashCount;


    /**
     * 近1个月贷款笔数:1-N表示命中查询主体的近1个月的贷款放款笔数
     */
    private Integer latestOneMonth;

    /**
     * 近3个月贷款笔数:1-N表示命中查询主体的近3个月的贷款放款笔数
     */
    private Integer latestThreeMonth;

    /**
     * 近6个月贷款笔数：1-N表示命中查询主体的近6个月的贷款放款笔数
     */
    private Integer latestSixMonth;

    /**
     * 历史贷款机构成功扣款笔数：1-N表示命中查询主体的贷款机构历史成功扣款笔数(12个月内)
     */
    private Integer historySucFee;

    /**
     * 历史贷款机构失败扣款笔数：1-N表示命中查询主体的贷款机构历史失败扣款笔数(12个月内)
     */
    private Integer historyFailFee;

    /**
     * 近1个月贷款机构成功扣款笔数：1-N表示命中查询主体的近一个月的贷款机构成功扣款笔数
     */
    private Integer latestOneMonthSuc;


    private Integer latestOneMonthFail;

    /**
     * 信用贷款时长:1-N表示命中查询主体的第一次贷款放款记录至今的天数
     */
    private Integer loansLongTime;

    /**
     * 最近一次贷款时间:yyyy-mm-dd表示主体的最后一次贷款放款记录时间(12个月内)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date loansLatestTime;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
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

    public Integer getLoansOverdueCount() {
        return loansOverdueCount;
    }

    public void setLoansOverdueCount(Integer loansOverdueCount) {
        this.loansOverdueCount = loansOverdueCount;
    }

    public Integer getLoansOrgCount() {
        return loansOrgCount;
    }

    public void setLoansOrgCount(Integer loansOrgCount) {
        this.loansOrgCount = loansOrgCount;
    }

    public Integer getConsfinOrgCount() {
        return consfinOrgCount;
    }

    public void setConsfinOrgCount(Integer consfinOrgCount) {
        this.consfinOrgCount = consfinOrgCount;
    }

    public Integer getLoansCashCount() {
        return loansCashCount;
    }

    public void setLoansCashCount(Integer loansCashCount) {
        this.loansCashCount = loansCashCount;
    }

    public Integer getLatestOneMonth() {
        return latestOneMonth;
    }

    public void setLatestOneMonth(Integer latestOneMonth) {
        this.latestOneMonth = latestOneMonth;
    }

    public Integer getLatestThreeMonth() {
        return latestThreeMonth;
    }

    public void setLatestThreeMonth(Integer latestThreeMonth) {
        this.latestThreeMonth = latestThreeMonth;
    }

    public Integer getLatestSixMonth() {
        return latestSixMonth;
    }

    public void setLatestSixMonth(Integer latestSixMonth) {
        this.latestSixMonth = latestSixMonth;
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

    public Integer getLatestOneMonthSuc() {
        return latestOneMonthSuc;
    }

    public void setLatestOneMonthSuc(Integer latestOneMonthSuc) {
        this.latestOneMonthSuc = latestOneMonthSuc;
    }

    public Integer getLatestOneMonthFail() {
        return latestOneMonthFail;
    }

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

    public void setLoansLatestTime(Date loansLatestTime) {
        this.loansLatestTime = loansLatestTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "XyActionRadarParam{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", userId=" + userId +
                ", idNo='" + idNo + '\'' +
                ", idName='" + idName + '\'' +
                ", versions='" + versions + '\'' +
                ", loansScore=" + loansScore +
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
                ", createTime=" + createTime +
                '}';
    }
}
