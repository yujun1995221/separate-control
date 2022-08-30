package com.free.decision.server.model.vo;

/**
 * 银行卡鉴权传参
 */
public class BankCardAuthVO extends ReportCommonParamsVO {

    private String accNo;

    private String idCard;

    private String idType;

    private String idHolder;

    private String cardType;

    private String mobile;

    private String validDate;

    private String verifyElement;

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdHolder() {
        return idHolder;
    }

    public void setIdHolder(String idHolder) {
        this.idHolder = idHolder;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getVerifyElement() {
        return verifyElement;
    }

    public void setVerifyElement(String verifyElement) {
        this.verifyElement = verifyElement;
    }

    @Override
    public String toString() {
        return "BankCardAuthVO{" +
                "accNo='" + accNo + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idType='" + idType + '\'' +
                ", idHolder='" + idHolder + '\'' +
                ", cardType='" + cardType + '\'' +
                ", mobile='" + mobile + '\'' +
                ", validDate='" + validDate + '\'' +
                ", verifyElement='" + verifyElement + '\'' +
                '}';
    }
}
