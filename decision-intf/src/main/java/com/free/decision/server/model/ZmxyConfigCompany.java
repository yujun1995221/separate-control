package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

public class ZmxyConfigCompany implements Serializable {
    private Long id;

    private Long configId;

    private Long companyId;

    private Integer status;

    private Integer zmxyScore;

    private Integer antiFraudScore;

    private Integer isOpen;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getZmxyScore() {
        return zmxyScore;
    }

    public void setZmxyScore(Integer zmxyScore) {
        this.zmxyScore = zmxyScore;
    }

    public Integer getAntiFraudScore() {
        return antiFraudScore;
    }

    public void setAntiFraudScore(Integer antiFraudScore) {
        this.antiFraudScore = antiFraudScore;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", configId=").append(configId);
        sb.append(", companyId=").append(companyId);
        sb.append(", status=").append(status);
        sb.append(", zmxyScore=").append(zmxyScore);
        sb.append(", antiFraudScore=").append(antiFraudScore);
        sb.append(", isOpen=").append(isOpen);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}