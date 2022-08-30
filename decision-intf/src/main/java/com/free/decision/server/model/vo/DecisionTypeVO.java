package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 决策类型
 */
public class DecisionTypeVO implements Serializable {

    private Integer count;

    private Integer group;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "DecisionTypeVO{" +
                "count=" + count +
                ", group=" + group +
                '}';
    }
}
