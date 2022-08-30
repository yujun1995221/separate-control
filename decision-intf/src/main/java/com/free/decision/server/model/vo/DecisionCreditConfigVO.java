package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 决策 多头与反欺诈等参数配置
 * @author Xingyf
 */
public class DecisionCreditConfigVO implements Serializable {

    /**
     * 编号
     */
    private String code;

    /**
     * 值
     */
    private String val;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "DecisionCreditVO{" +
                "code='" + code + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
