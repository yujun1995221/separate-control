package com.free.decision.server.enums;

/**
 * 宝莲灯请求记录枚举
 */
public enum BldRecordTypeEnum {

    BLACK(1, "黑名单");

    private int id;

    private String name;

    BldRecordTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BldRecordTypeEnum getEnum(int id) {
        for (BldRecordTypeEnum bldRecordTypeEnum : BldRecordTypeEnum.values()) {
            if (bldRecordTypeEnum.getId()==id) {
                return bldRecordTypeEnum;
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
