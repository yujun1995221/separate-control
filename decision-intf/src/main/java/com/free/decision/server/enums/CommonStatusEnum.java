package com.free.decision.server.enums;

/**
 *
 * @author Xingyf
 */
public enum  CommonStatusEnum {

    NORMAL(1, "正常"), DELETE(0, "删除");

    private int id;

    private String name;

    CommonStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CommonStatusEnum getEnum(int id) {
        for (CommonStatusEnum statusEnum : CommonStatusEnum.values()) {
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
