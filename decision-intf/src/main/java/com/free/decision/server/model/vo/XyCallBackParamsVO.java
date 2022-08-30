package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 新颜回调参数
 */
public class XyCallBackParamsVO implements Serializable {

    private String msg;

    private String taskId;

    private String token;

    private String apiUser;

    private String apiEnc;

    private String apiName;

    private boolean success;

    private String type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiEnc() {
        return apiEnc;
    }

    public void setApiEnc(String apiEnc) {
        this.apiEnc = apiEnc;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "XyCallBackParamsVO{" +
                "msg='" + msg + '\'' +
                ", taskId='" + taskId + '\'' +
                ", token='" + token + '\'' +
                ", apiUser='" + apiUser + '\'' +
                ", apiEnc='" + apiEnc + '\'' +
                ", apiName='" + apiName + '\'' +
                ", success=" + success +
                ", type='" + type + '\'' +
                '}';
    }
}
