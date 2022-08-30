package com.free.decision.server.enums;

/**
 * 实名认证银行卡鉴权接口类型枚举
 * @author Xingyf
 */
public enum BankVerifTypeEnum {

    REAL_NAME(1, "实名认证接口"), UDBANK_AUTH(2, "有盾银行卡四要素鉴权"), XYBANK_AUTH(3,"新颜银行卡四要素鉴权");

    private int id;

    private String name;

    BankVerifTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BankVerifTypeEnum getEnum(int id) {
        for (BankVerifTypeEnum udSdkTypeEnum : BankVerifTypeEnum.values()) {
            if (udSdkTypeEnum.getId()==id) {
                return udSdkTypeEnum;
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
