package com.free.decision.server.enums;

/**
 *
 * 请求接口类型枚举
 *
 */
public enum XyRecordTypeEnum {

    RADAR(1,"行为雷达"), BANK_AUTH(2, "银行卡四要素鉴权"),NEW_RADAR(3, "多头雷达") ;

    private int id;

    private String name;

    XyRecordTypeEnum(int id, String name){
        this.id=id;
        this.name=name;
    }

    public static XyRecordTypeEnum getEnum(int id){
        for (XyRecordTypeEnum enums : XyRecordTypeEnum.values()) {
            if (enums.getId()==id){
                return enums;
            }
        }
        return null;
    }

    public int getId() { return id; }

    public String getName() { return name; }
}
