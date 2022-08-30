package com.free.decision.server.model.vo;

import java.util.Date;

/**
 * 客户信息
 * @author Xingyf
 */
public class CustomerInfoVO extends ReportCommonParamsVO{

    private Long companyId;

    private String idNumber;

    private String phone;

    private String name;

    private String idAddress;

    private Date registerTime;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return "CustomerInfoVO{" +
                "companyId=" + companyId +
                ", idNumber='" + idNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", idAddress='" + idAddress + '\'' +
                ", registerTime=" + registerTime +
                '}';
    }
}
