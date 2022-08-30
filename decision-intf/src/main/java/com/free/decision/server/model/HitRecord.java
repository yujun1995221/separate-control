package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 命中记录
 * @author Xingyf
 */
public class HitRecord implements Serializable {

    private Long id;

    private Long decisionReportId;

    private Long companyId;

    private Integer group;

    private String code;

    private Long userId;

    private Integer type;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDecisionReportId() {
        return decisionReportId;
    }

    public void setDecisionReportId(Long decisionReportId) {
        this.decisionReportId = decisionReportId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "HitRecord{" +
                "id=" + id +
                ", decisionReportId=" + decisionReportId +
                ", companyId=" + companyId +
                ", group=" + group +
                ", code='" + code + '\'' +
                ", userId=" + userId +
                ", type=" + type +
                ", createTime=" + createTime +
                '}';
    }
}
