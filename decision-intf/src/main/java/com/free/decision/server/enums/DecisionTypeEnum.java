package com.free.decision.server.enums;

/**
 * 决策引擎接口类型枚举
 * @author Xingyf
 */
public enum DecisionTypeEnum {

    CREDIT(1, "信贷接口"), MOBILE(2, "运营商"), MOBILE_CALLBACK(3, "运营商异步回调"), DECISION_NEW(4, "新增用户决策接口"), DECISION_OLD(5, "复贷用户决策接口"), TAO_BAO(6, "淘宝报告");

    private int id;

    private String name;

    DecisionTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DecisionTypeEnum getEnum(int id) {
        for (DecisionTypeEnum decisionTypeEnum : DecisionTypeEnum.values()) {
            if (decisionTypeEnum.getId()==id) {
                return decisionTypeEnum;
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
