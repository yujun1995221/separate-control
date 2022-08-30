package com.free.decision.server.enums;

/**
 * 决策报告状态枚举
 * @author Xingyf
 */
public enum DecisionRecordStatusEnum {

    GENERATING(1, "生成中"), GENERATING_SUCCESS(2, "生成成功"),  GENERATING_FAIL(3, "生成失败");

    private int id;

    private String name;

    DecisionRecordStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DecisionRecordStatusEnum getEnum(int id) {
        for (DecisionRecordStatusEnum statusEnum : DecisionRecordStatusEnum.values()) {
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
