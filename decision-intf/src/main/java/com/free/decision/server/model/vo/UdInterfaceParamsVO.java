package com.free.decision.server.model.vo;

/**
 * 有盾接口入参
 * @author yxs
 */
public class UdInterfaceParamsVO extends ReportCommonParamsVO {
    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 身份证号
     */
    private String idNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Override
    public String toString() {
        return "UdCreditParamVO{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", idNo='" + idNo + '\'' +
                '}';
    }
}
