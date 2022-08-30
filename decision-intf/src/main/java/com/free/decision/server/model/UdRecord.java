package com.free.decision.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 记录信息
 *
 */
public class UdRecord implements Serializable {

    private Long id;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 类型 1:云慧眼接口
     */
    private Integer type;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 用户ID
     */
    private Long userId;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getCompanyId() { return companyId; }

    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getRequestParams() { return requestParams; }

    public void setRequestParams(String requestParams) { this.requestParams = requestParams; }

    public Integer getType() { return type; }

    public void setType(Integer type) { this.type = type; }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UdRecord{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", requestParams='" + requestParams + '\'' +
                ", type=" + type +
                ", result='" + result + '\'' +
                ", createTime=" + createTime +
                ", userId=" + userId +
                '}';
    }
}
