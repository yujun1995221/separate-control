package com.free.decision.server.enums;

/**
 *
 * 请求接口类型枚举
 *
 */
public enum UdRecordTypeEnum {

    YUNHUIYAN(1,"云慧眼接口"),REAL_NAME(2, "实名认证接口"), BANK_AUTH(3, "银行卡四要素鉴权"),
    YUNXIANG(4,"云相接口");

    private int id;

    private String name;

    UdRecordTypeEnum(int id, String name){
        this.id=id;
        this.name=name;
    }

    public static UdRecordTypeEnum getEnum(int id){
        for (UdRecordTypeEnum enums : UdRecordTypeEnum.values()) {
            if (enums.getId()==id){
                return enums;
            }
        }
        return null;
    }

    public int getId() { return id; }

    public String getName() { return name; }
}
