package com.free.decision.server.enums;

/**
 * 运营商订单状态枚举
 */
public enum MobileOrderStatusEnum {

    UN_KNOW(0, "未知"), SUCCESS(1, "成功"), FAIL(2, "失败"), DOING(3, "进行中");

    private int id;

    private String name;

    MobileOrderStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MobileOrderStatusEnum getEnum(int id) {
        for (MobileOrderStatusEnum statusEnum : MobileOrderStatusEnum.values()) {
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
