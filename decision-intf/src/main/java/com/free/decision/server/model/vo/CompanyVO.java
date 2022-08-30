package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 用户基本信息实体类
 */
public class CompanyVO implements Serializable {

    /** id */
    private Long id;

    /** 公司名称 */
    private String companyName;

    /** 联系人 */
    private String contactName;

    /** 联系方式 */
    private String contactMobile;

    /**公司id**/
    private Long companyId;

    /**用户名**/
    private String loginName;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    @Override
    public String toString() {
        return "CompanyVO{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactMobile='" + contactMobile + '\'' +
                ", companyId=" + companyId +
                ", loginName='" + loginName + '\'' +
                '}';
    }
}
