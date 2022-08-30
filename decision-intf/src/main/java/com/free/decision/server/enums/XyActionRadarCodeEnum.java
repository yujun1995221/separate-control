package com.free.decision.server.enums;

/**
 * 行为报告返回结果枚举
 */
public enum XyActionRadarCodeEnum {

    SUCCESS(0,"成功"),LOSE(1,"失败"),FALSE(3,"失败"),OTHER(9,"其他异常");

    private int id;

    private String name;

    XyActionRadarCodeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static XyActionRadarCodeEnum getEnum(int id) {
        for (XyActionRadarCodeEnum xyActionRadarCodeEnum : XyActionRadarCodeEnum.values()) {
            if (xyActionRadarCodeEnum.getId()==id) {
                return xyActionRadarCodeEnum;
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
