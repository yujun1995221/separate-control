package com.free.decision.server.model.vo;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 公司信息VO
 */
public class CompanyDataVO implements Serializable {
    /** id */
    private Long id;
    /** 公司名称 */
    private String companyName;

    private String appName;
    /** 联系人 */
    private String contactName;
    /** 联系方式 */
    private String contactMobile;
    /** 余额 */
    private BigDecimal amount;
    /** 运营商报告价格 */
    private BigDecimal mobilePrice;
    /** 信贷报告价格 */
    private BigDecimal creditPrice;
    /** 决策报告价格 */
    private BigDecimal decisionPrice;
    /**
     * 淘宝报告价格
     */
    private BigDecimal taoBaoPrice;
    /**
     * 反欺诈报告价格
     */
    private BigDecimal antiFraudPrice;
    /**
     * 负债共享查询价格
     */
    private BigDecimal liabilityPrice;
    /**
     * 有盾云相价格
     */
    private BigDecimal cloudPhasePrice;
    /**
     * 决策分数价格
     */
    private BigDecimal decisionScorePrice;
    /**
     * 运营商权限
     */
    private Integer mobileStatus;
    /**
     * 多头报告权限
     */
    private Integer creditStatus;
    /**
     * 反欺诈报告权限
     */
    private Integer antiFraudStatus;
    /**
     * 决策报告权限
     */
    private Integer decisionStatus;
    /**
     * 淘宝报告权限
     */
    private Integer taoBaoStatus;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 实名认证权限
     */
    private Integer  realNameStatus;
    /**
     * 实名认证价格
     */
    private BigDecimal realNamePrice;
    /**
     * 银卡鉴权权限
     */
    private Integer bankAuthStatus;
    /**
     * 银卡鉴权价格
     */
    private BigDecimal bankAuthPrice;
    /**
     * 验证码短信发送权限
     */
    private Integer  smsCodeStatus;
    /**
     * 验证码短信发送价格
     */
    private BigDecimal smsCodePrice;
    /**
     * 通知短信发送权限
     */
    private Integer  smsNoticeStatus;
    /**
     * 通知短信发送价格
     */
    private BigDecimal smsNoticePrice;

    private BigDecimal autoDecisionPrice;

    private Integer score;

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

    public BigDecimal getTaoBaoPrice() {
        return taoBaoPrice;
    }

    public void setTaoBaoPrice(BigDecimal taoBaoPrice) {
        this.taoBaoPrice = taoBaoPrice;
    }

    public BigDecimal getAntiFraudPrice() {
        return antiFraudPrice;
    }

    public void setAntiFraudPrice(BigDecimal antiFraudPrice) {
        this.antiFraudPrice = antiFraudPrice;
    }

    public BigDecimal getLiabilityPrice() {
        return liabilityPrice;
    }

    public void setLiabilityPrice(BigDecimal liabilityPrice) {
        this.liabilityPrice = liabilityPrice;
    }

    public BigDecimal getCloudPhasePrice() {
        return cloudPhasePrice;
    }

    public void setCloudPhasePrice(BigDecimal cloudPhasePrice) {
        this.cloudPhasePrice = cloudPhasePrice;
    }

    public BigDecimal getDecisionScorePrice() {
        return decisionScorePrice;
    }

    public void setDecisionScorePrice(BigDecimal decisionScorePrice) {
        this.decisionScorePrice = decisionScorePrice;
    }

    public Integer getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(Integer mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
    }

    public Integer getAntiFraudStatus() {
        return antiFraudStatus;
    }

    public void setAntiFraudStatus(Integer antiFraudStatus) {
        this.antiFraudStatus = antiFraudStatus;
    }

    public Integer getDecisionStatus() {
        return decisionStatus;
    }

    public void setDecisionStatus(Integer decisionStatus) {
        this.decisionStatus = decisionStatus;
    }

    public Integer getTaoBaoStatus() {
        return taoBaoStatus;
    }

    public void setTaoBaoStatus(Integer taoBaoStatus) {
        this.taoBaoStatus = taoBaoStatus;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getRealNameStatus() {
        return realNameStatus;
    }

    public void setRealNameStatus(Integer realNameStatus) {
        this.realNameStatus = realNameStatus;
    }

    public BigDecimal getRealNamePrice() {
        return realNamePrice;
    }

    public void setRealNamePrice(BigDecimal realNamePrice) {
        this.realNamePrice = realNamePrice;
    }

    public Integer getBankAuthStatus() {
        return bankAuthStatus;
    }

    public void setBankAuthStatus(Integer bankAuthStatus) {
        this.bankAuthStatus = bankAuthStatus;
    }

    public BigDecimal getBankAuthPrice() {
        return bankAuthPrice;
    }

    public void setBankAuthPrice(BigDecimal bankAuthPrice) {
        this.bankAuthPrice = bankAuthPrice;
    }

    public Integer getSmsCodeStatus() {
        return smsCodeStatus;
    }

    public void setSmsCodeStatus(Integer smsCodeStatus) {
        this.smsCodeStatus = smsCodeStatus;
    }

    public BigDecimal getSmsCodePrice() {
        return smsCodePrice;
    }

    public void setSmsCodePrice(BigDecimal smsCodePrice) {
        this.smsCodePrice = smsCodePrice;
    }

    public Integer getSmsNoticeStatus() {
        return smsNoticeStatus;
    }

    public void setSmsNoticeStatus(Integer smsNoticeStatus) {
        this.smsNoticeStatus = smsNoticeStatus;
    }

    public BigDecimal getSmsNoticePrice() {
        return smsNoticePrice;
    }

    public void setSmsNoticePrice(BigDecimal smsNoticePrice) {
        this.smsNoticePrice = smsNoticePrice;
    }

    public BigDecimal getAutoDecisionPrice() {
        return autoDecisionPrice;
    }

    public void setAutoDecisionPrice(BigDecimal autoDecisionPrice) {
        this.autoDecisionPrice = autoDecisionPrice;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
