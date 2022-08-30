package com.free.decision.server.model.vo;

/**
 * 实名认证接口入参
 */
public class RealNameParamVO extends ReportCommonParamsVO {

    /**
     * 姓名
     */
    private String idName;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * ud的json串
     */
    private String udResult;

    /**
     * 结果
     */
    private String result;


    public String getIdName() {
        return idName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getUdResult() {
        return udResult;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUdResult(String udResult) {
        this.udResult = udResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
