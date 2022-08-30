package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 决策报告 查询芝麻分和code
 * @author Xingyf
 */
public class DecisonZmxyConfigVO implements Serializable {

    /**
     * 编号
     */
    private String code;

    /**
     * 芝麻分
     */
    private Integer score;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DecisonZmxyVO{" +
                "code='" + code + '\'' +
                ", score=" + score +
                '}';
    }
}
