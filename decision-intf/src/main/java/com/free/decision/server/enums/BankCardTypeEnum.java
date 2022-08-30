package com.free.decision.server.enums;

/**
 * 银行卡鉴权类型
 * @author pj
 */
public enum BankCardTypeEnum {

    UDBANK_AUTH(1, "有盾"), XYBANK_AUTH(2, "新颜");

    private int id;

    private String name;

    BankCardTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BankCardTypeEnum getEnum(int id) {
        for (BankCardTypeEnum bankCardTypeEnum : BankCardTypeEnum.values()) {
            if (bankCardTypeEnum.getId()==id) {
                return bankCardTypeEnum;
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
