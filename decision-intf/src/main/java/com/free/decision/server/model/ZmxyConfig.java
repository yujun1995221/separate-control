package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

public class ZmxyConfig implements Serializable {
    private Long id;

    private String code;

    private Integer status;

    private Integer zmxyScore;

    private Integer antiFraudScore;

    private Integer areaId;

    private Integer isOpen;

    private Date createTime;

    private Date updateTime;

    private String area;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", status=").append(status);
        sb.append(", zmxyScore=").append(zmxyScore);
        sb.append(", antiFraudScore=").append(antiFraudScore);
        sb.append(", areaId=").append(areaId);
        sb.append(", area=").append(area);
        sb.append(", isOpen=").append(isOpen);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}