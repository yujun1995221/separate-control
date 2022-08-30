package com.free.decision.server.enums;

/**
 * 报告类型枚举
 */
public enum NoticeRecordTypeEnum {

    OPERATOR(1,"运营商"),  MULTIINVEST(2,"多投"), ANTIFRAUD(3,"反欺诈"), DECISION(4,"决策"), TAOBAO(5, "淘宝");

    private int id;

    private String name;

    NoticeRecordTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static NoticeRecordTypeEnum getEnum(int id){
        for (NoticeRecordTypeEnum recordTypeEnum : NoticeRecordTypeEnum.values()) {
            if (recordTypeEnum.getId() == id){
                return recordTypeEnum;
            }

        }
        return null;
    }

    public int getId() { return id; }

    public String getName() { return name; }
}
