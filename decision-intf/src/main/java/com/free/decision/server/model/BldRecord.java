package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 宝莲灯请求记录
 */
public class BldRecord implements Serializable {

    private Long id;

    private Long companyId;

    private Long userId;

    private String idNumber;

    private String phone;

    private String name;

    private String requestParams;

    private Integer phoneHitBlack;

    private Integer idNumberHitBlack;

    private String result;

    private Integer type;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public Integer getPhoneHitBlack() {
        return phoneHitBlack;
    }

    public Integer getIdNumberHitBlack() {
        return idNumberHitBlack;
    }

    public void setPhoneHitBlack(Integer phoneHitBlack) {
        this.phoneHitBlack = phoneHitBlack;
    }

    public void setIdNumberHitBlack(Integer idNumberHitBlack) {
        this.idNumberHitBlack = idNumberHitBlack;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
