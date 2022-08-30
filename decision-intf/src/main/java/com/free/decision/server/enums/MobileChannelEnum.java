package com.free.decision.server.enums;

/**
 * 运营商渠道枚举
 */
public enum MobileChannelEnum {

    XIN_YAN(1, "新颜"), MO_XIE(2, "魔蝎");

    private int id;

    private String name;

    MobileChannelEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MobileChannelEnum getEnum(int id) {
        for (MobileChannelEnum typeEnum : MobileChannelEnum.values()) {
            if (typeEnum.getId()==id) {
                return typeEnum;
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
