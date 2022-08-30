package com.free.decision.server.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 淘宝订单参数
 * @author yxs
 */
public class MobileParamsVO extends ReportCommonParamsVO{

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 通讯录
     */
    private List<Contact> contactList;

    /**
     * 紧急联系人
     */
    private List<Contact> emergContactList;

    private String callback;

    /**
     * 认证渠道 1：新颜 2：魔蝎
     */
    private Integer channel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public List<Contact> getEmergContactList() {
        return emergContactList;
    }

    public void setEmergContactList(List<Contact> emergContactList) {
        this.emergContactList = emergContactList;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public static class Contact implements Serializable {
        private String name;

        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
