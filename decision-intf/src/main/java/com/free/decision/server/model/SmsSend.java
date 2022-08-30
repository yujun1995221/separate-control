package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信发送记录
 * @author Xingyf
 */
public class SmsSend implements Serializable {

    private Long id;

    private Integer status;

    private String mobile;

    private String content;

    private Integer count;

    private String params;

    private Integer channel;

    private String ret;

    private String ip;

    private Integer type;

    private Date createTime;

    private String smsUid;

    private String benchId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSmsUid() {
        return smsUid;
    }

    public void setSmsUid(String smsUid) {
        this.smsUid = smsUid;
    }

    public String getBenchId() {
        return benchId;
    }

    public void setBenchId(String benchId) {
        this.benchId = benchId;
    }

    @Override
    public String toString() {
        return "SmsSend{" +
                "id=" + id +
                ", status=" + status +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", params='" + params + '\'' +
                ", channel=" + channel +
                ", ret='" + ret + '\'' +
                ", ip='" + ip + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", smsUid='" + smsUid + '\'' +
                ", benchId='" + benchId + '\'' +
                '}';
    }
}
