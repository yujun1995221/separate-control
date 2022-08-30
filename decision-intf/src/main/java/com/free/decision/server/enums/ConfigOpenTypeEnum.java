package com.free.decision.server.enums;

/**
 * 决策配置
 * 是否开启 1：开启 0：关闭
 * @author yxs
 */
public enum ConfigOpenTypeEnum {
    OPEN(1, "开启"), CLOSE(0, "关闭");

    private int id;

    private String name;

    ConfigOpenTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static ConfigOpenTypeEnum getEnum(int id) {
        for (ConfigOpenTypeEnum openTypeEnum : ConfigOpenTypeEnum.values()) {
            if (openTypeEnum.getId()==id) {
                return openTypeEnum;
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
