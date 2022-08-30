package com.free.decision.server.model.vo;

import java.io.Serializable;

public class UdResultVO implements Serializable {
    private String reqTime;
    private String respTime;
    private String retCode;
    private String retMsg;
    private String version;

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "UdResultVO{" +
                "reqTime='" + reqTime + '\'' +
                ", respTime='" + respTime + '\'' +
                ", retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
