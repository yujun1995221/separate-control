package com.free.decision.server.sms;

import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.SmsSendParamVO;

/**
 * 短信发送工厂
 * @author Xingyf
 */
public abstract class SmsSendStrategy {

	public abstract Result send(SmsSendParamVO smsSendParamVO, Company company);

	public abstract Result batchSend(SmsSendParamVO smsSendParamVO, Company company);

}