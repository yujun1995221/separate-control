package com.free.decision.server.model.vo;

import java.io.Serializable;

/**
 * 即将逾期订单短信提醒参数
 */
public class OrderSmsVO implements Serializable {
    private String phone;
    private String content;
    private String extend;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "OrderSmsVO{" +
                ", phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                ", extend='" + extend + '\'' +
                '}';
    }
}