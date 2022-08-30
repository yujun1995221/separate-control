package com.free.decision.server.enums;

/**
 * 决策所属组枚举
 * @author Xingyf
 */
public enum DecisionGroupEnum {

    INTERNAL(1, "internal", "内置"), ZMXY(2, "zmxy", "芝麻信用"), YYS(3, "YYS", "运营商");

    private int id;

    private String code;

    private String name;

    DecisionGroupEnum(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public static DecisionGroupEnum getEnum(int id) {
        for (DecisionGroupEnum decisionGroupEnum : DecisionGroupEnum.values()) {
            if (decisionGroupEnum.getId()==id) {
                return decisionGroupEnum;
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

    public String getCode() {
        return code;
    }
}
