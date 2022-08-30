package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 用户特征字典
 */
public class UdUserFeatureDicVO implements Serializable {
    //编号
    private String code;

    //名称
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UdUserFeatureDicVO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
