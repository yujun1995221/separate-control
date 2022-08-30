package com.free.decision.server.enums;

/**
 * 返回值枚举
 * @author Xingyf
 */
public enum ResultCodeEnum {

    SUCCESS(200, "成功"), EXCEPTION(500, "异常"), FAIL(201, "失败"), LOGIN(999, "需要登录"), NO_USER(998, "用户不存在")
    ,FAST(301, "请求太频繁,请稍后再试");

    private int id;

    private String name;

    ResultCodeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ResultCodeEnum getEnum(int id) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.getId()==id) {
                return resultCodeEnum;
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
