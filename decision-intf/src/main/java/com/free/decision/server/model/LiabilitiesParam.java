package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

public class LiabilitiesParam implements Serializable {

    //黑名单状态0:不是，1:是
    private String blacklistStatus = "否";

    //当前是否逾期
    private String overdueLessTwo = "否";

    //总注册数
    private Long totalRegistration = 0l;

    //总申请数
    private Long totalApplications = 0l;

    //总首贷申请数
    private Long totalFirApplications = 0l;

    //总复贷申请数
    private Long totalReApplications = 0l;

    //总拒绝数
    private Long totalRejectNumber = 0l;

    //总通过数
    private Long totalPassNumber = 0l;

    //总结清数
    private Long totalSuccessNumber = 0l;

    //未还款数
    private Long noRepaymentNumber = 0l;

    //历史逾期数
    private Long overdueNumber = 0l;



    //一周注册数
    private Long weekRegistration = 0l ;

    //一周通过数
    private Long weekPassNumber = 0l;

    //一周拒绝数
    private Long weekRejectNumber = 0l;

    //一周申请数
    private Long weekApplications = 0l;


    //一周结清数
    private Long weekSuccessNumber = 0l;
    //一周首贷申请数
    private Long weekFirApplications = 0l;

    //一周复贷申请数
    private Long weekReApplications = 0l;


    //第一次申请时间
    private Date firApplicationsTime;

    //最后一次申请时间
    private Date lastApplicationsTime;

    //新增修改参数
    /************************************************************************************/
    //近一周首贷通过数
    private Long weekFirPassNumber = 0l;

    //近一周复贷通过数
    private Long weekRePassNumber = 0l;

    //近一周首贷拒绝数
    private Long weekFirRejectNumber = 0l;

    //近一周复贷拒绝数
    private Long weekReRejectNumber = 0l;


    //首贷通过数
    private Long totalFirPassNnmber = 0l;

    //复贷通过数
    private Long totalRePassNumber = 0l;

    //首贷拒绝数
    private Long totalFirRejectNumber = 0l;

    //复贷拒绝数
    private Long totalReRejectNumber = 0l;



    //近一周逾期次数
    private Long weekOverdueNumber = 0l;


    //一天到期3笔次数
    private Long  dayExpireThreeNumber = 0l;
    //一天到期4笔次数
    private Long  dayExpireFourNumber = 0l;
    //一天到期5笔次数
    private Long  dayExpireFiveNumber = 0l;
    //一天到期6笔次数
    private Long  dayExpireSixNumber = 0l;
    //一天到期7笔次数
    private Long  dayExpireSevenNumber = 0l;

    public Long getWeekFirPassNumber() {
        return weekFirPassNumber;
    }

    public void setWeekFirPassNumber(Long weekFirPassNumber) {
        this.weekFirPassNumber = weekFirPassNumber;
    }

    public Long getWeekRePassNumber() {
        return weekRePassNumber;
    }

    public void setWeekRePassNumber(Long weekRePassNumber) {
        this.weekRePassNumber = weekRePassNumber;
    }

    public Long getWeekFirRejectNumber() {
        return weekFirRejectNumber;
    }

    public void setWeekFirRejectNumber(Long weekFirRejectNumber) {
        this.weekFirRejectNumber = weekFirRejectNumber;
    }

    public Long getWeekReRejectNumber() {
        return weekReRejectNumber;
    }

    public void setWeekReRejectNumber(Long weekReRejectNumber) {
        this.weekReRejectNumber = weekReRejectNumber;
    }

    public Long getTotalFirPassNnmber() {
        return totalFirPassNnmber;
    }

    public void setTotalFirPassNnmber(Long totalFirPassNnmber) {
        this.totalFirPassNnmber = totalFirPassNnmber;
    }

    public Long getTotalRePassNumber() {
        return totalRePassNumber;
    }

    public void setTotalRePassNumber(Long totalRePassNumber) {
        this.totalRePassNumber = totalRePassNumber;
    }

    public Long getTotalFirRejectNumber() {
        return totalFirRejectNumber;
    }

    public void setTotalFirRejectNumber(Long totalFirRejectNumber) {
        this.totalFirRejectNumber = totalFirRejectNumber;
    }

    public Long getTotalReRejectNumber() {
        return totalReRejectNumber;
    }

    public void setTotalReRejectNumber(Long totalReRejectNumber) {
        this.totalReRejectNumber = totalReRejectNumber;
    }

    public Long getWeekOverdueNumber() {
        return weekOverdueNumber;
    }

    public void setWeekOverdueNumber(Long weekOverdueNumber) {
        this.weekOverdueNumber = weekOverdueNumber;
    }

    public Long getDayExpireThreeNumber() {
        return dayExpireThreeNumber;
    }

    public void setDayExpireThreeNumber(Long dayExpireThreeNumber) {
        this.dayExpireThreeNumber = dayExpireThreeNumber;
    }

    public Long getDayExpireFourNumber() {
        return dayExpireFourNumber;
    }

    public void setDayExpireFourNumber(Long dayExpireFourNumber) {
        this.dayExpireFourNumber = dayExpireFourNumber;
    }

    public Long getDayExpireFiveNumber() {
        return dayExpireFiveNumber;
    }

    public void setDayExpireFiveNumber(Long dayExpireFiveNumber) {
        this.dayExpireFiveNumber = dayExpireFiveNumber;
    }

    public Long getDayExpireSixNumber() {
        return dayExpireSixNumber;
    }

    public void setDayExpireSixNumber(Long dayExpireSixNumber) {
        this.dayExpireSixNumber = dayExpireSixNumber;
    }

    public Long getDayExpireSevenNumber() {
        return dayExpireSevenNumber;
    }

    public void setDayExpireSevenNumber(Long dayExpireSevenNumber) {
        this.dayExpireSevenNumber = dayExpireSevenNumber;
    }

    public String getBlacklistStatus() {
        return blacklistStatus;
    }

    public void setBlacklistStatus(String blacklistStatus) {
        this.blacklistStatus = blacklistStatus;
    }

    public String getOverdueLessTwo() {
        return overdueLessTwo;
    }

    public void setOverdueLessTwo(String overdueLessTwo) {
        this.overdueLessTwo = overdueLessTwo;
    }

    public Long getTotalRegistration() {
        return totalRegistration;
    }

    public void setTotalRegistration(Long totalRegistration) {
        this.totalRegistration = totalRegistration;
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Long getTotalFirApplications() {
        return totalFirApplications;
    }

    public void setTotalFirApplications(Long totalFirApplications) {
        this.totalFirApplications = totalFirApplications;
    }

    public Long getTotalReApplications() {
        return totalReApplications;
    }

    public void setTotalReApplications(Long totalReApplications) {
        this.totalReApplications = totalReApplications;
    }

    public Long getTotalRejectNumber() {
        return totalRejectNumber;
    }

    public void setTotalRejectNumber(Long totalRejectNumber) {
        this.totalRejectNumber = totalRejectNumber;
    }

    public Long getTotalPassNumber() {
        return totalPassNumber;
    }

    public void setTotalPassNumber(Long totalPassNumber) {
        this.totalPassNumber = totalPassNumber;
    }

    public Long getTotalSuccessNumber() {
        return totalSuccessNumber;
    }

    public void setTotalSuccessNumber(Long totalSuccessNumber) {
        this.totalSuccessNumber = totalSuccessNumber;
    }

    public Long getNoRepaymentNumber() {
        return noRepaymentNumber;
    }

    public void setNoRepaymentNumber(Long noRepaymentNumber) {
        this.noRepaymentNumber = noRepaymentNumber;
    }

    public Long getOverdueNumber() {
        return overdueNumber;
    }

    public void setOverdueNumber(Long overdueNumber) {
        this.overdueNumber = overdueNumber;
    }

    public Long getWeekRegistration() {
        return weekRegistration;
    }

    public void setWeekRegistration(Long weekRegistration) {
        this.weekRegistration = weekRegistration;
    }

    public Long getWeekPassNumber() {
        return weekPassNumber;
    }

    public void setWeekPassNumber(Long weekPassNumber) {
        this.weekPassNumber = weekPassNumber;
    }

    public Long getWeekRejectNumber() {
        return weekRejectNumber;
    }

    public void setWeekRejectNumber(Long weekRejectNumber) {
        this.weekRejectNumber = weekRejectNumber;
    }

    public Long getWeekApplications() {
        return weekApplications;
    }

    public void setWeekApplications(Long weekApplications) {
        this.weekApplications = weekApplications;
    }

    public Long getWeekSuccessNumber() {
        return weekSuccessNumber;
    }

    public void setWeekSuccessNumber(Long weekSuccessNumber) {
        this.weekSuccessNumber = weekSuccessNumber;
    }

    public Long getWeekFirApplications() {
        return weekFirApplications;
    }

    public void setWeekFirApplications(Long weekFirApplications) {
        this.weekFirApplications = weekFirApplications;
    }

    public Long getWeekReApplications() {
        return weekReApplications;
    }

    public void setWeekReApplications(Long weekReApplications) {
        this.weekReApplications = weekReApplications;
    }

    public Date getFirApplicationsTime() {
        return firApplicationsTime;
    }

    public void setFirApplicationsTime(Date firApplicationsTime) {
        this.firApplicationsTime = firApplicationsTime;
    }

    public Date getLastApplicationsTime() {
        return lastApplicationsTime;
    }

    public void setLastApplicationsTime(Date lastApplicationsTime) {
        this.lastApplicationsTime = lastApplicationsTime;
    }


    @Override
    public String toString() {
        return "LiabilitiesParam{" +
                "blacklistStatus='" + blacklistStatus + '\'' +
                ", overdueLessTwo='" + overdueLessTwo + '\'' +
                ", totalRegistration=" + totalRegistration +
                ", totalApplications=" + totalApplications +
                ", totalFirApplications=" + totalFirApplications +
                ", totalReApplications=" + totalReApplications +
                ", totalRejectNumber=" + totalRejectNumber +
                ", totalPassNumber=" + totalPassNumber +
                ", totalSuccessNumber=" + totalSuccessNumber +
                ", noRepaymentNumber=" + noRepaymentNumber +
                ", overdueNumber=" + overdueNumber +
                ", weekRegistration=" + weekRegistration +
                ", weekPassNumber=" + weekPassNumber +
                ", weekRejectNumber=" + weekRejectNumber +
                ", weekApplications=" + weekApplications +
                ", weekSuccessNumber=" + weekSuccessNumber +
                ", weekFirApplications=" + weekFirApplications +
                ", weekReApplications=" + weekReApplications +
                ", firApplicationsTime=" + firApplicationsTime +
                ", lastApplicationsTime=" + lastApplicationsTime +
                '}';
    }
}
