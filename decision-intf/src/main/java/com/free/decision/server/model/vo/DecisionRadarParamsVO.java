package com.free.decision.server.model.vo;

/**
 * 行为雷达
 */
public class DecisionRadarParamsVO extends ReportCommonParamsVO{

    /**
     * 姓名
     */
    private String idName;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 银行卡号
     */
    private String bankcardNo;


    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBankcardNo() {
        return bankcardNo;
    }

    public void setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
    }

    @Override
    public String toString() {
        return "DecisionRadarParamsVO{" +
                "idName='" + idName + '\'' +
                ", idNo='" + idNo + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", bankcardNo='" + bankcardNo + '\'' +
                '}';
    }
}
