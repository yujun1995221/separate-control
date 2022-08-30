package com.free.decision.server.enums;

/**
 * 是否通用枚举
 * @author Xingyf
 */
public enum YesAndNoStatusEnum {

    YES(1, "是"), NO(0, "否");

    private int id;

    private String name;

    YesAndNoStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static YesAndNoStatusEnum getEnum(int id) {
        for (YesAndNoStatusEnum statusEnum : YesAndNoStatusEnum.values()) {
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
