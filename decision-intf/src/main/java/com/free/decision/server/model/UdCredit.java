package com.free.decision.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 有盾云慧眼关联信息
 */
public class UdCredit implements Serializable {

    private Long id;

    //报告编号
    private String code;

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date lastModifiedTime;

    //出生日期
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone="GMT+8")
    private Date birthday;

    //性别
    private String gender;

   //民族
    private String nation;

    //出生省市
    private String birthPlace;

    //住址
    private String address;

    //身份证掩码
    private String idNumberMask;

    //使用手机号个数
    private Integer mobileCount;

    //关联用户数
    private Integer linkUserCount;

    //其他关联设备数
    private Integer otherLinkDeviceCount;

    //商户标记个数
    private Integer partnerMarkCount;

    //法院失信
    private Integer courtDishonestCount;

    //网贷失信
    private Integer onlineDishonestCount;

    //活体攻击行为
    private Integer livingAttackCount;

    //名下银行卡数
    private Integer userHaveBankcardCount;

   //可疑设备
    private Integer otherFraudDeviceCount;

    //活体攻击设备
    private Integer otherLivingAttackDeviceCount;

    //本商户内用户数
    private Integer partnerUserCount;

    //关联银行卡数
    private Integer bankcardCount;

    //可疑设备
    private Integer fraudDeviceCount;

    //活体攻击设备
    private Integer livingAttackDeviceCount;

    //使用设备数
    private Integer linkDeviceCount;

    //评估模型得分  1- 99分，分值越高风险等级越大 若score=0，表示缺乏足够信息判断风险
    private Integer score;

    //风险等级
    private String riskEvaluation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    private String names;

    private String province;

    private String city;

    private String issuingAgency;

    private String nameCredible;

    private Integer actualLoanPlatformCount;

    private Integer actualLoanPlatformCount1m;

    private Integer actualLoanPlatformCount3m;

    private Integer actualLoanPlatformCount6m;

    private Integer loanPlatformCount;

    private Integer repaymentTimesCount;

    private Integer repaymentPlatformCount;

    private Integer loanPlatformCount1m;

    private Integer loanPlatformCount3m;

    private Integer loanPlatformCount6m;

    private Integer repaymentPlatformCount1m;

    private Integer repaymentPlatformCount3m;

    private Integer repaymentPlatformCount6m;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumberMask() {
        return idNumberMask;
    }

    public void setIdNumberMask(String idNumberMask) {
        this.idNumberMask = idNumberMask;
    }

    public Integer getMobileCount() {
        return mobileCount;
    }

    public void setMobileCount(Integer mobileCount) { this.mobileCount = mobileCount; }

    public Integer getLinkUserCount() {
        return linkUserCount;
    }

    public void setLinkUserCount(Integer linkUserCount) { this.linkUserCount = linkUserCount; }

    public Integer getOtherLinkDeviceCount() {
        return otherLinkDeviceCount;
    }

    public void setOtherLinkDeviceCount(Integer otherLinkDeviceCount) { this.otherLinkDeviceCount = otherLinkDeviceCount; }

    public Integer getPartnerMarkCount() {
        return partnerMarkCount;
    }

    public void setPartnerMarkCount(Integer partnerMarkCount) { this.partnerMarkCount = partnerMarkCount; }

    public Integer getCourtDishonestCount() {
        return courtDishonestCount;
    }

    public void setCourtDishonestCount(Integer courtDishonestCount) {
        this.courtDishonestCount = courtDishonestCount;
    }

    public Integer getOnlineDishonestCount() {
        return onlineDishonestCount;
    }

    public void setOnlineDishonestCount(Integer onlineDishonestCount) { this.onlineDishonestCount = onlineDishonestCount; }

    public Integer getLivingAttackCount() {
        return livingAttackCount;
    }

    public void setLivingAttackCount(Integer livingAttackCount) {
        this.livingAttackCount = livingAttackCount;
    }

    public Integer getUserHaveBankcardCount() {
        return userHaveBankcardCount;
    }

    public void setUserHaveBankcardCount(Integer userHaveBankcardCount) { this.userHaveBankcardCount = userHaveBankcardCount; }

    public Integer getOtherFraudDeviceCount() {
        return otherFraudDeviceCount;
    }

    public void setOtherFraudDeviceCount(Integer otherFraudDeviceCount) { this.otherFraudDeviceCount = otherFraudDeviceCount; }

    public Integer getOtherLivingAttackDeviceCount() {
        return otherLivingAttackDeviceCount;
    }

    public void setOtherLivingAttackDeviceCount(Integer otherLivingAttackDeviceCount) { this.otherLivingAttackDeviceCount = otherLivingAttackDeviceCount; }

    public Integer getPartnerUserCount() {
        return partnerUserCount;
    }

    public void setPartnerUserCount(Integer partnerUserCount) {
        this.partnerUserCount = partnerUserCount;
    }

    public Integer getBankcardCount() { return bankcardCount; }

    public void setBankcardCount(Integer bankcardCount) {
        this.bankcardCount = bankcardCount;
    }

    public Integer getFraudDeviceCount() {
        return fraudDeviceCount;
    }

    public void setFraudDeviceCount(Integer fraudDeviceCount) {
        this.fraudDeviceCount = fraudDeviceCount;
    }

    public Integer getLivingAttackDeviceCount() {
        return livingAttackDeviceCount;
    }

    public void setLivingAttackDeviceCount(Integer livingAttackDeviceCount) { this.livingAttackDeviceCount = livingAttackDeviceCount; }

    public Integer getLinkDeviceCount() {
        return linkDeviceCount;
    }

    public void setLinkDeviceCount(Integer linkDeviceCount) {
        this.linkDeviceCount = linkDeviceCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRiskEvaluation() {
        return riskEvaluation;
    }

    public void setRiskEvaluation(String riskEvaluation) {
        this.riskEvaluation = riskEvaluation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIssuingAgency() {
        return issuingAgency;
    }

    public void setIssuingAgency(String issuingAgency) {
        this.issuingAgency = issuingAgency;
    }

    public String getNameCredible() {
        return nameCredible;
    }

    public void setNameCredible(String nameCredible) {
        this.nameCredible = nameCredible;
    }

    public Integer getActualLoanPlatformCount() {
        return actualLoanPlatformCount;
    }

    public void setActualLoanPlatformCount(Integer actualLoanPlatformCount) { this.actualLoanPlatformCount = actualLoanPlatformCount; }

    public Integer getActualLoanPlatformCount1m() {
        return actualLoanPlatformCount1m;
    }

    public void setActualLoanPlatformCount1m(Integer actualLoanPlatformCount1m) { this.actualLoanPlatformCount1m = actualLoanPlatformCount1m; }

    public Integer getActualLoanPlatformCount3m() {
        return actualLoanPlatformCount3m;
    }

    public void setActualLoanPlatformCount3m(Integer actualLoanPlatformCount3m) { this.actualLoanPlatformCount3m = actualLoanPlatformCount3m; }

    public Integer getActualLoanPlatformCount6m() {
        return actualLoanPlatformCount6m;
    }

    public void setActualLoanPlatformCount6m(Integer actualLoanPlatformCount6m) { this.actualLoanPlatformCount6m = actualLoanPlatformCount6m; }

    public Integer getLoanPlatformCount() {
        return loanPlatformCount;
    }

    public void setLoanPlatformCount(Integer loanPlatformCount) {
        this.loanPlatformCount = loanPlatformCount;
    }

    public Integer getRepaymentTimesCount() { return repaymentTimesCount; }

    public void setRepaymentTimesCount(Integer repaymentTimesCount) {
        this.repaymentTimesCount = repaymentTimesCount;
    }

    public Integer getRepaymentPlatformCount() {
        return repaymentPlatformCount;
    }

    public void setRepaymentPlatformCount(Integer repaymentPlatformCount) { this.repaymentPlatformCount = repaymentPlatformCount; }

    public Integer getLoanPlatformCount1m() {
        return loanPlatformCount1m;
    }

    public void setLoanPlatformCount1m(Integer loanPlatformCount1m) {
        this.loanPlatformCount1m = loanPlatformCount1m;
    }

    public Integer getLoanPlatformCount3m() { return loanPlatformCount3m; }

    public void setLoanPlatformCount3m(Integer loanPlatformCount3m) {
        this.loanPlatformCount3m = loanPlatformCount3m;
    }

    public Integer getLoanPlatformCount6m() {
        return loanPlatformCount6m;
    }

    public void setLoanPlatformCount6m(Integer loanPlatformCount6m) {
        this.loanPlatformCount6m = loanPlatformCount6m;
    }

    public Integer getRepaymentPlatformCount1m() {
        return repaymentPlatformCount1m;
    }

    public void setRepaymentPlatformCount1m(Integer repaymentPlatformCount1m) { this.repaymentPlatformCount1m = repaymentPlatformCount1m; }

    public Integer getRepaymentPlatformCount3m() {
        return repaymentPlatformCount3m;
    }

    public void setRepaymentPlatformCount3m(Integer repaymentPlatformCount3m) { this.repaymentPlatformCount3m = repaymentPlatformCount3m; }

    public Integer getRepaymentPlatformCount6m() {
        return repaymentPlatformCount6m;
    }

    public void setRepaymentPlatformCount6m(Integer repaymentPlatformCount6m) { this.repaymentPlatformCount6m = repaymentPlatformCount6m; }

    @Override
    public String toString() {
        return "UdCredit{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", userId=" + userId +
                ", lastModifiedTime=" + lastModifiedTime +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", nation='" + nation + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", address='" + address + '\'' +
                ", idNumberMask='" + idNumberMask + '\'' +
                ", mobileCount=" + mobileCount +
                ", linkUserCount=" + linkUserCount +
                ", otherLinkDeviceCount=" + otherLinkDeviceCount +
                ", partnerMarkCount=" + partnerMarkCount +
                ", courtDishonestCount=" + courtDishonestCount +
                ", onlineDishonestCount=" + onlineDishonestCount +
                ", livingAttackCount=" + livingAttackCount +
                ", userHaveBankcardCount=" + userHaveBankcardCount +
                ", otherFraudDeviceCount=" + otherFraudDeviceCount +
                ", otherLivingAttackDeviceCount=" + otherLivingAttackDeviceCount +
                ", partnerUserCount=" + partnerUserCount +
                ", bankcardCount=" + bankcardCount +
                ", fraudDeviceCount=" + fraudDeviceCount +
                ", livingAttackDeviceCount=" + livingAttackDeviceCount +
                ", linkDeviceCount=" + linkDeviceCount +
                ", score=" + score +
                ", riskEvaluation='" + riskEvaluation + '\'' +
                ", createTime=" + createTime +
                ", names='" + names + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", issuingAgency='" + issuingAgency + '\'' +
                ", nameCredible='" + nameCredible + '\'' +
                ", actualLoanPlatformCount=" + actualLoanPlatformCount +
                ", actualLoanPlatformCount1m=" + actualLoanPlatformCount1m +
                ", actualLoanPlatformCount3m=" + actualLoanPlatformCount3m +
                ", actualLoanPlatformCount6m=" + actualLoanPlatformCount6m +
                ", loanPlatformCount=" + loanPlatformCount +
                ", repaymentTimesCount=" + repaymentTimesCount +
                ", repaymentPlatformCount=" + repaymentPlatformCount +
                ", loanPlatformCount1m=" + loanPlatformCount1m +
                ", loanPlatformCount3m=" + loanPlatformCount3m +
                ", loanPlatformCount6m=" + loanPlatformCount6m +
                ", repaymentPlatformCount1m=" + repaymentPlatformCount1m +
                ", repaymentPlatformCount3m=" + repaymentPlatformCount3m +
                ", repaymentPlatformCount6m=" + repaymentPlatformCount6m +
                '}';
    }
}
