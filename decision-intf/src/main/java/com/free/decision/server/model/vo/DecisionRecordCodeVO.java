package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 决策命中编号
 */
public class DecisionRecordCodeVO implements Serializable {

    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "DecisionRecordCodeVO{" +
                "code='" + code + '\'' +
                '}';
    }
}
