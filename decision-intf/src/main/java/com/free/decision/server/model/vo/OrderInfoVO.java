package com.free.decision.server.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息 - 负债共享
 */
public class OrderInfoVO extends ReportCommonParamsVO{

    /**
     * 公司ID
     */
    private Long companyId;

    private String idNumber;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 出售价格
     */
    private BigDecimal sellPrice;

    /**
     * 租期
     */
    private Integer rentPeriod;

    /**
     * 放款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loanDate;

    /**
     * 到期日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date overdueDate;

    /**
     * 实际还款金额
     */
    private BigDecimal cutRealMoney;

    /**
     * 还款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date repaymentDate;

    /**
     * 财务状态
     */
    private Integer financeStatus;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 是否续期
     */
    private Integer persistFlag;

    /**
     * 续期次数
     */
    private Integer persistCount;

   /**
     * 设备名称
     */
    private String equipmentName;

    /**
     * 设备串码
     */
    private String equipmentNumber;

    /**
     * 借款状态（0,未借款,1首贷,2复贷）
     */
    private Integer lendStatus;

    /**
     * 订单申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;

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

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(Integer rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(Date overdueDate) {
        this.overdueDate = overdueDate;
    }

    public BigDecimal getCutRealMoney() {
        return cutRealMoney;
    }

    public void setCutRealMoney(BigDecimal cutRealMoney) {
        this.cutRealMoney = cutRealMoney;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Integer getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(Integer financeStatus) {
        this.financeStatus = financeStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPersistFlag() {
        return persistFlag;
    }

    public void setPersistFlag(Integer persistFlag) {
        this.persistFlag = persistFlag;
    }

    public Integer getPersistCount() {
        return persistCount;
    }

    public void setPersistCount(Integer persistCount) {
        this.persistCount = persistCount;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public Integer getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(Integer lendStatus) {
        this.lendStatus = lendStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
}
