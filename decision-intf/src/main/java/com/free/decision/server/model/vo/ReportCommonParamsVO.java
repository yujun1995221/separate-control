package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 报告接口 公共入参
 * @author Xingyf
 */
public class ReportCommonParamsVO implements Serializable {
    /**
     * api key
     */
    private String apiKey;

    /**
     * 签名
     */
    private String sign;

    /**
     * 时间戳
     */
    private String timestamp;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ReportCommonParamsVO{" +
                "apiKey='" + apiKey + '\'' +
                ", sign='" + sign + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
