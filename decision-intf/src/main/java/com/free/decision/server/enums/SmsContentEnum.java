package com.free.decision.server.enums;

/**
 * 短信内容类型枚举
 * @author Xingyf
 */
public enum SmsContentEnum {

    CODE(1, "验证码"),
    LOAN(2, "放款"),
    TODAY(3, "今日到期"),
    TOMORROW(4, "明日到期"),
    BALANCE(5, "余额不足提醒");

    private int id;

    private String name;

    SmsContentEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SmsContentEnum getEnum(int id) {
        for (SmsContentEnum smsChannelEnum : SmsContentEnum.values()) {
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
