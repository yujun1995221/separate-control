package com.free.decision.server.enums;

/**
 * 淘宝报告渠道枚举
 */
public enum TaoBaoChannelEnum {

    XIN_YAN(1, "新颜"), MO_XIE(2, "魔蝎");

    private int id;

    private String name;

    private String beanName;

    TaoBaoChannelEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TaoBaoChannelEnum getEnum(int id) {
        for (TaoBaoChannelEnum typeEnum : TaoBaoChannelEnum.values()) {
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
