package com.free.decision.server.enums;

/**
 * 信贷类型枚举
 * @author Xingyf
 */
public enum CreditTypeEnum {

    FIRST_LEND(1, "首贷"), SECOND_LEND(2, "复贷");

    private int id;

    private String name;

    CreditTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CreditTypeEnum getEnum(int id) {
        for (CreditTypeEnum creditTypeEnum : CreditTypeEnum.values()) {
            if (creditTypeEnum.getId()==id) {
                return creditTypeEnum;
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
