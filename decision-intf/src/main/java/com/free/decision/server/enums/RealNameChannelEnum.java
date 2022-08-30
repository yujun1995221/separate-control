package com.free.decision.server.enums;

/**
 * 实名认证渠道
 */
public enum RealNameChannelEnum {

    ALIYUN(1, "阿里云");

    private int id;

    private String name;

    RealNameChannelEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RealNameChannelEnum getEnum(int id) {
        for (RealNameChannelEnum realNameChannelEnum : RealNameChannelEnum.values()) {
            if (realNameChannelEnum.getId()==id) {
                return realNameChannelEnum;
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
