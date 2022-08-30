package com.free.decision.server.enums;

/**
 * 短信渠道枚举
 * @author Xingyf
 */
public enum SmsChannelEnum {

    YUN_TONG_XUN_SMS(3, "253云通讯","yunTongXunSmsSendStrategy"),YUN_DUAN_XING_SMS(4, "云短信","yunDuanXinSmsSendStrategy");

    private int id;

    private String name;

    private String beanName;

    SmsChannelEnum(int id, String name,String beanName) {
        this.id = id;
        this.name = name;
        this.beanName = beanName;
    }

    public static SmsChannelEnum getEnum(int id) {
        for (SmsChannelEnum smsChannelEnum : SmsChannelEnum.values()) {
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

    public String getBeanName() {return beanName; }
}
