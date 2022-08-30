package com.free.decision.server.enums;

/**
 * 淘宝订单状态枚举
 */
public enum TaoBaoOrderStatusEnum {

    UN_KNOW(0, "未知"), SUCCESS(1, "成功"), FAIL(2, "失败"), DOING(3, "进行中");

    private int id;

    private String name;

    TaoBaoOrderStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TaoBaoOrderStatusEnum getEnum(int id) {
        for (TaoBaoOrderStatusEnum statusEnum : TaoBaoOrderStatusEnum.values()) {
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
