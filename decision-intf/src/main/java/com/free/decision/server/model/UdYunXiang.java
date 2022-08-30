package com.free.decision.server.model;

import java.time.LocalDateTime;

/**
 * 有盾云相
 *
 * @author xuechenye
 * @create 2019-04-02 17:24
 **/
public class UdYunXiang {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 报告编号
     */
    private String code;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 综合行为评分
     */
    private Integer score;
    /**
     * 返回结果
     */
    private String result;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

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
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
