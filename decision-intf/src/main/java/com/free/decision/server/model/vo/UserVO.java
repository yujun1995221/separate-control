package com.free.decision.server.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息实体类
 */
public class UserVO implements Serializable {

    /**id**/
    private Long id;

    /**名字**/
    private String name;

    /**公司id**/
    private Long companyId;

    /**手机号**/
    private String mobile;

    /**身份证号**/
    private String idNumber;

    /**状态 1：生成中 2：生成成功  3：生成失败**/
    private Integer status;

    /**报告结果**/
    private Integer resultType;

    /**创建时间**/
    private Date createTime;

    /**更新时间**/
    private Date updateTime;

    /**
     * 省份
     */
    private String province;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
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
        return "UserVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", mobile='" + mobile + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", status=" + status +
                ", resultType=" + resultType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", province='" + province + '\'' +
                '}';
    }
}
