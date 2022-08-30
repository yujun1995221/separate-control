package com.free.decision.server.enums;

/**
 * 决策报告结果枚举
 * @author Xingyf
 */
public enum DecisionRecordResultTypeEnum {

    PASS(1, "通过"), REJECT(2, "拒绝"), REVIEW(3, "人工审核");

    private int id;

    private String name;

    DecisionRecordResultTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DecisionRecordResultTypeEnum getEnum(int id) {
        for (DecisionRecordResultTypeEnum statusEnum : DecisionRecordResultTypeEnum.values()) {
            if (statusEnum.getId()==id) {
                return statusEnum;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
