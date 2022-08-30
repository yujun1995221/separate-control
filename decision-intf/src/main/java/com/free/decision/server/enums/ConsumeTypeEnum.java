package com.free.decision.server.enums;

/**
 * 消费类型
 * @author Xingyf
 */
public enum ConsumeTypeEnum {

    CONSUME(1, "消费"), RECHARGE(2, "充值");

    private int id;

    private String name;

    ConsumeTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ConsumeTypeEnum getEnum(int id) {
        for (ConsumeTypeEnum consumeTypeStatusEnum : ConsumeTypeEnum.values()) {
            if (consumeTypeStatusEnum.getId()==id) {
                return consumeTypeStatusEnum;
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
