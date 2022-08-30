package com.free.decision.server.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 决策报告接口入参
 */
public class DecisionParamsVO extends ReportCommonParamsVO{

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 芝麻分
     */
    private Integer sesameScore;

    /**
     * 省份
     */
    private String province;

    /**
     * 通讯录
     */
    private List<Phone> phoneList;

    /**
     * 联系人
     */
    private List<Phone> contactList;

    /**
     * 类型 1：首贷  2：复贷
     */
    private Integer type;

    /**
     * 运营商渠道 1：新颜 2：魔蝎
     */
    private Integer mobileChannel;

    /**
     * 是否需要前置决策
     */
    private Boolean frontDecisionFlag;

    /**
     * 回调
     */
    private String callback;

    /**
     * 扩展参数
     */
    private String extend;

    /**
     * 透传参数
     */
    private String other;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSesameScore() {
        return sesameScore;
    }

    public void setSesameScore(Integer sesameScore) {
        this.sesameScore = sesameScore;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public List<Phone> getContactList() {
        return contactList;
    }

    public void setContactList(List<Phone> contactList) {
        this.contactList = contactList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMobileChannel() {
        return mobileChannel;
    }

    public void setMobileChannel(Integer mobileChannel) {
        this.mobileChannel = mobileChannel;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Boolean getFrontDecisionFlag() {
        return frontDecisionFlag;
    }

    public void setFrontDecisionFlag(Boolean frontDecisionFlag) {
        this.frontDecisionFlag = frontDecisionFlag;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public static class Phone implements Serializable {
        private String name;

        private String phone;

        private String updateTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
