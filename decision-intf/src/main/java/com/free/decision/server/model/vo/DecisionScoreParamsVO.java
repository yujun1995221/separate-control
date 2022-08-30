package com.free.decision.server.model.vo;

/**
 * 决策报告接口入参
 * @author Xingyf
 */
public class DecisionScoreParamsVO extends ReportCommonParamsVO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 淘宝认证状态 1：已认证  0：未认证
     * @return
     */
    private Integer taoBaoStatus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getTaoBaoStatus() {
        return taoBaoStatus;
    }

    public void setTaoBaoStatus(Integer taoBaoStatus) {
        this.taoBaoStatus = taoBaoStatus;
    }

    @Override
    public String toString() {
        return "DecisionScoreParamsVO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", taoBaoStatus=" + taoBaoStatus +
                '}';
    }
}
