package com.free.decision.server.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司表
 */
public class Company implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * app名称
     */
    private String appName;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系方式
     */
    private String contactMobile;

    /**
     * 余额
     */
    private BigDecimal amount;

    /**
     * 运营商报告价格
     */
    private BigDecimal mobilePrice;

    /**
     * 信贷报告价格
     */
    private BigDecimal creditPrice;

    /**
     * 决策报告价格
     */
    private BigDecimal decisionPrice;

    /**
     * 反欺诈报告价格
     */
    private BigDecimal antiFraudPrice;

    /**
     * 淘宝报告价格
     */
    private BigDecimal taoBaoPrice;

    /**
     * 负债共享价格
     */
    private BigDecimal liabilityPrice;

    /**
     * 实名认证价格
     */
    private BigDecimal realNamePrice;

    /**
     * 验证码短信发送价格
     */
    private BigDecimal smsCodePrice;

    /**
     * 通知短信发送价格
     */
    private BigDecimal smsNoticePrice;

    /**
     * 银行卡鉴权价格
     */
    private BigDecimal bankAuthPrice;

    /**
     * 宝莲灯黑名单价格
     */
    private BigDecimal bldBlackPrice;

    private String apiKey;

    private String apiSecret;

    private Integer blackFlag;

    private Integer decisionChannel;

    private Integer forceRejectFlag;

    private Integer creditFlag;

    private Integer mozhangFlag;

    private Integer mozhangScore;

    private BigDecimal creditAmount;

    private Integer bldBlackFlag;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMobilePrice() {
        return mobilePrice;
    }

    public void setMobilePrice(BigDecimal mobilePrice) {
        this.mobilePrice = mobilePrice;
    }

    public BigDecimal getCreditPrice() {
        return creditPrice;
    }

    public void setCreditPrice(BigDecimal creditPrice) {
        this.creditPrice = creditPrice;
    }

    public BigDecimal getDecisionPrice() {
        return decisionPrice;
    }

    public void setDecisionPrice(BigDecimal decisionPrice) {
        this.decisionPrice = decisionPrice;
    }

    public BigDecimal getAntiFraudPrice() {
        return antiFraudPrice;
    }

    public void setAntiFraudPrice(BigDecimal antiFraudPrice) {
        this.antiFraudPrice = antiFraudPrice;
    }

    public BigDecimal getTaoBaoPrice() {
        return taoBaoPrice;
    }

    public void setTaoBaoPrice(BigDecimal taoBaoPrice) {
        this.taoBaoPrice = taoBaoPrice;
    }

    public BigDecimal getLiabilityPrice() {
        return liabilityPrice;
    }

    public void setLiabilityPrice(BigDecimal liabilityPrice) {
        this.liabilityPrice = liabilityPrice;
    }

    public BigDecimal getRealNamePrice() {
        return realNamePrice;
    }

    public void setRealNamePrice(BigDecimal realNamePrice) {
        this.realNamePrice = realNamePrice;
    }

    public BigDecimal getSmsCodePrice() {
        return smsCodePrice;
    }

    public void setSmsCodePrice(BigDecimal smsCodePrice) {
        this.smsCodePrice = smsCodePrice;
    }

    public BigDecimal getSmsNoticePrice() {
        return smsNoticePrice;
    }

    public void setSmsNoticePrice(BigDecimal smsNoticePrice) {
        this.smsNoticePrice = smsNoticePrice;
    }

    public BigDecimal getBankAuthPrice() {
        return bankAuthPrice;
    }

    public void setBankAuthPrice(BigDecimal bankAuthPrice) {
        this.bankAuthPrice = bankAuthPrice;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public Integer getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(Integer blackFlag) {
        this.blackFlag = blackFlag;
    }

    public Integer getDecisionChannel() {
        return decisionChannel;
    }

    public void setDecisionChannel(Integer decisionChannel) {
        this.decisionChannel = decisionChannel;
    }

    public Integer getForceRejectFlag() {
        return forceRejectFlag;
    }

    public void setForceRejectFlag(Integer forceRejectFlag) {
        this.forceRejectFlag = forceRejectFlag;
    }

    public Integer getCreditFlag() {
        return creditFlag;
    }

    public void setCreditFlag(Integer creditFlag) {
        this.creditFlag = creditFlag;
    }

    public Integer getMozhangFlag() {
        return mozhangFlag;
    }

    public void setMozhangFlag(Integer mozhangFlag) {
        this.mozhangFlag = mozhangFlag;
    }

    public Integer getMozhangScore() {
        return mozhangScore;
    }

    public void setMozhangScore(Integer mozhangScore) {
        this.mozhangScore = mozhangScore;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
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

    public Integer getBldBlackFlag() {
        return bldBlackFlag;
    }

    public void setBldBlackFlag(Integer bldBlackFlag) {
        this.bldBlackFlag = bldBlackFlag;
    }

    public BigDecimal getBldBlackPrice() {
        return bldBlackPrice;
    }

    public void setBldBlackPrice(BigDecimal bldBlackPrice) {
        this.bldBlackPrice = bldBlackPrice;
    }
}