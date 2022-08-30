package com.free.decision.server.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 负债共享数据
 * @author Xingyf
 */
public class LiabilityDataVO implements Serializable {

    private String name;

    private String idNumber;

    private String phone;

    private String idAddress;

    private Boolean blackFlag;

    private List<LiabilityOrderInfo> orderInfoList;

    public static class LiabilityOrderInfo implements Serializable{

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
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date loanDate;

        /**
         * 到期日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date overdueDate;

        /**
         * 实际还款金额
         */
        private BigDecimal cutRealMoney;

        /**
         * 还款日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date repaymentDate;

        /**
         * 财务状态
         */
        private Integer financeStatus;


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
         * 订单申请时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date applyTime;

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

        public Date getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(Date applyTime) {
            this.applyTime = applyTime;
        }
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

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public List<LiabilityOrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<LiabilityOrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }

    public Boolean getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(Boolean blackFlag) {
        this.blackFlag = blackFlag;
    }
}
