package com.free.decision.server.enums;

/**
 * 短信类型枚举
 * @author Xingyf
 */
public enum SmsTypeEnum {

    CODE(1, "验证码"),
    OVERDUE(2,"逾期"),
    NOTICE(3,"通知");

    private int id;

    private String name;

    SmsTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SmsTypeEnum getEnum(int id) {
        for (SmsTypeEnum smsChannelEnum : SmsTypeEnum.values()) {
            if (smsChannelEnum.getId()==id) {
                return smsChannelEnum;
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
