package com.free.decision.server.enums;

/**
 * 报告类型枚举
 */
public enum ReportTypeEnum {

    NOTHING(0, "无"), MOBILE(1, "运营商"), CREDIT(2, "信贷"), ANTI_FRAUD(3, "反欺诈"), DECISION(4, "决策"), TAO_BAO(5,"淘宝"), REAL_NAME(6,"实名认证"),BANK_AUTH(7,"银行卡四要素鉴权"),SMS_CODE(8,"验证码短信"),BATCH_SMS(9,"通知短信"),LIABILITY(10, "负债共享数据");

    private int id;

    private String name;

    ReportTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ReportTypeEnum getEnum(int id) {
        for (ReportTypeEnum reportTypeEnum : ReportTypeEnum.values()) {
            if (reportTypeEnum.getId()==id) {
                return reportTypeEnum;
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
