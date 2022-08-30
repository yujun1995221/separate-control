package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 决策分数记录
 */
public class ScoreRecord implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户条件
     */
    private String userCondition;

    /**
     * 题目
     */
    private String subject;

    /**
     * 条件
     */
    private String condition;

    /**
     * 得分/扣分
     */
    private Integer score;

    /**
     * 创建时间
     */
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

    public String getUserCondition() {
        return userCondition;
    }

    public void setUserCondition(String userCondition) {
        this.userCondition = userCondition;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ScoreRecord{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", userId=" + userId +
                ", userCondition='" + userCondition + '\'' +
                ", subject='" + subject + '\'' +
                ", condition='" + condition + '\'' +
                ", score=" + score +
                ", createTime=" + createTime +
                '}';
    }
}
