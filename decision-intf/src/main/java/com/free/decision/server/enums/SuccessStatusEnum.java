package com.free.decision.server.enums;

/**
 * 成功状态枚举
 * @author Xingyf
 */
public enum SuccessStatusEnum {

    SUCCESS(1, "成功"), FAIL(0, "失败"),SENDING(2,"正在发送");

    private int id;

    private String name;

    SuccessStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SuccessStatusEnum getEnum(int id) {
        for (SuccessStatusEnum successStatusEnum : SuccessStatusEnum.values()) {
            if (successStatusEnum.getId()==id) {
                return successStatusEnum;
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
