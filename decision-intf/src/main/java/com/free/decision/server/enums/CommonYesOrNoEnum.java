package com.free.decision.server.enums;

/**
 * 状态为“是”或者“否”公共枚举
 * @author Xingyf
 */
public enum CommonYesOrNoEnum {

    YES(1, "是"), NO(0, "否");

    private int id;

    private String name;

    CommonYesOrNoEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CommonYesOrNoEnum getEnum(int id) {
        for (CommonYesOrNoEnum commonStatusEnum : CommonYesOrNoEnum.values()) {
            if (commonStatusEnum.getId()==id) {
                return commonStatusEnum;
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
