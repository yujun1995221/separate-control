package com.free.decision.server.enums;

/**
 * 魔蝎请求记录枚举
 */
public enum MxRecordTypeEnum {

    MO_ZHANG_STANDARD(1, "魔杖分-标准版"),  PEI_QI_PEN(2, "佩琪分");

    private int id;

    private String name;

    MxRecordTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MxRecordTypeEnum getEnum(int id) {
        for (MxRecordTypeEnum mxRecordTypeEnum : MxRecordTypeEnum.values()) {
            if (mxRecordTypeEnum.getId()==id) {
                return mxRecordTypeEnum;
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
