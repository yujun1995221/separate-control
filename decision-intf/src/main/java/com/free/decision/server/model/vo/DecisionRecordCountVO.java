package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 决策数量
 */
public class DecisionRecordCountVO implements Serializable {

    private Integer count;

    private Integer type;

    private Integer resultType;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }


    @Override
    public String toString() {
        return "DecisionRecordCountVO{" +
                "count=" + count +
                ", type=" + type +
                ", resultType=" + resultType +
                '}';
    }
}
