package com.free.decision.server.enums;

/**
 * 决策报告类型枚举
 * @author Xingyf
 */
public enum DecisionRecordTypeEnum {

    FIRST_LEND(1, "首贷"), SECOND_LEND(2, "复贷");

    private int id;

    private String name;

    DecisionRecordTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DecisionRecordTypeEnum getEnum(int id) {
        for (DecisionRecordTypeEnum statusEnum : DecisionRecordTypeEnum.values()) {
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
