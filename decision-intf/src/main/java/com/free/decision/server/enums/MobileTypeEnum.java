package com.free.decision.server.enums;

/**
 * 运营商报告类型
 * @author qiantao
 */
public enum MobileTypeEnum {

    XIN_YAN(1, "新颜"), MO_XIE(2, "魔蝎");

    private int id;

    private String name;

    MobileTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static MobileTypeEnum getEnum(int id) {
        for (MobileTypeEnum newsTypeEnum : MobileTypeEnum.values()) {
            if (newsTypeEnum.getId()==id) {
                return newsTypeEnum;
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
