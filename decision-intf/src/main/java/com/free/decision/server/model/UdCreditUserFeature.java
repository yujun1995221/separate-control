package com.free.decision.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 反欺诈报告用户特征信息
 */
public class UdCreditUserFeature implements Serializable {

    private  Long id;

    //有盾报告ID
    private Long udCreditId;

    //特征值
    private String featureType;

    //最后命中特征日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date lastModifiedDate;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUdCreditId() { return udCreditId; }

    public void setUdCreditId(Long udCreditId) { this.udCreditId = udCreditId; }

    public String getFeatureType() { return featureType; }

    public void setFeatureType(String featureType) { this.featureType = featureType; }

    public Date getLastModifiedDate() { return lastModifiedDate; }

    public void setLastModifiedDate(Date lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }

    @Override
    public String toString() {
        return "UdCreditUserFeature{" +
                "id=" + id +
                ", udCreditId=" + udCreditId +
                ", featureType='" + featureType + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
