package com.free.decision.server.model;

import java.io.Serializable;

/**
 * 有盾特征值描述信息
 */
public class UdFeaturesDic implements Serializable {

    private Long id;

    //编号
    private String code;

    //名称
    private String name;

    //描述
    private String description;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "UdFeaturesDic{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
