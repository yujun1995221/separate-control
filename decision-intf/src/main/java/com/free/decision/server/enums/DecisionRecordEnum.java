package com.free.decision.server.enums;

/**
 * 决策类型枚举
 */
public enum DecisionRecordEnum {

   NEWINCRASED(1, "新增"), RELOAN(2, "复贷"),PASS(1,"通过"),REJECT(2,"拒绝");

    private int id;

    private String name;

    DecisionRecordEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DecisionRecordEnum getEnum(int id) {
        for (DecisionRecordEnum decisionRecordEnum : DecisionRecordEnum.values()) {
            if (decisionRecordEnum.getId()==id) {
                return decisionRecordEnum;
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
