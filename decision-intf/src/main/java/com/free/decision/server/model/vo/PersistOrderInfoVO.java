package com.free.decision.server.model.vo;

import java.util.Date;

/**
 * 续期订单信息
 * @author Xingyf
 */
public class PersistOrderInfoVO extends ReportCommonParamsVO {

    /**
     * 公司ID
     */
    private Long companyId;

    private String idNumber;

    private String orderNumber;

    private String persistNumber;

    private Date mainOrderOverdueTime;

    private Date persistTime;

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPersistNumber() {
        return persistNumber;
    }

    public void setPersistNumber(String persistNumber) {
        this.persistNumber = persistNumber;
    }

    public Date getMainOrderOverdueTime() {
        return mainOrderOverdueTime;
    }

    public void setMainOrderOverdueTime(Date mainOrderOverdueTime) {
        this.mainOrderOverdueTime = mainOrderOverdueTime;
    }

    public Date getPersistTime() {
        return persistTime;
    }

    public void setPersistTime(Date persistTime) {
        this.persistTime = persistTime;
    }

    @Override
    public String toString() {
        return "PersistOrderInfoVO{" +
                "companyId=" + companyId +
                ", idNumber='" + idNumber + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", persistNumber='" + persistNumber + '\'' +
                ", mainOrderOverdueTime=" + mainOrderOverdueTime +
                ", persistTime=" + persistTime +
                '}';
    }
}
