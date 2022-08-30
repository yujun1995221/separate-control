package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 决策信用类型数量实体类
 */
public class HitRecordVO implements Serializable {

    /**数量**/
    private Integer count;
    /**1.首贷 2.复贷**/
    private Integer type;
    /**信用类型**/
    private Integer group;
    /**编号**/
    private String code;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "HitRecordVO{" +
                "count=" + count +
                ", type=" + type +
                ", group=" + group +
                ", code='" + code + '\'' +
                '}';
    }
}
