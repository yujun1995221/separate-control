package com.free.decision.server.model.vo;

/**
 * 短信发送参数
 * 
 * @author xiechengkai
 *
 */
public class SmsSendParamVO extends ReportCommonParamsVO {
	/**
	 * 手机号
	 */
	public String mobile;

	/**
	 * 短信内容模板
	 */
	public String content;

	/**
	 * 短信类型
	 */
	public int smsType;

	/**
	 * 短信签名
	 */
	public String smsSign;

	/**
	 * IP
	 */
	public String ip;

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

	public int getSmsType() {
		return smsType;
	}

	public void setSmsType(int smsType) {
		this.smsType = smsType;
	}

	public String getSmsSign() {
		return smsSign;
	}

	public void setSmsSign(String smsSign) {
		this.smsSign = smsSign;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "SmsSendParamVO{" +
				"mobile='" + mobile + '\'' +
				", content='" + content + '\'' +
				", smsType=" + smsType +
				", smsSign='" + smsSign + '\'' +
				", ip='" + ip + '\'' +
				'}';
	}
}
