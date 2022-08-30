package com.free.decision.server.enums;

/**
 * 决策配置
 * 是否显示 1：显示 0：不显示
 * @author yxs
 */
public enum ConfigShowTypeEnum {
    SHOW(1, "显示"), HIDE(0, "不显示");

    private int id;

    private String name;

    ConfigShowTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static ConfigShowTypeEnum getEnum(int id) {
        for (ConfigShowTypeEnum showTypeEnum : ConfigShowTypeEnum.values()) {
            if (showTypeEnum.getId()==id) {
                return showTypeEnum;
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
