package com.free.decision.server.service;

import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.SmsSend;
import com.free.decision.server.model.vo.SmsSendParamVO;

import java.util.Date;

/**
 * 短信发送记录
 * @author Xingyf
 */
public interface SmsSendService {

    /**
     * 新增
     * @param smsSend
     * @return
     */
    public int add(SmsSend smsSend);

    /**
     * 发送短信
     * @param smsSendParamVO
     * @param company
     * @return
     */
    public Result send(SmsSendParamVO smsSendParamVO, Company company);

    /**
     * 批量发送短信
     * @param smsSendParamVO
     * @param company
     * @return
     */
    public Result batchSend(SmsSendParamVO smsSendParamVO, Company company);

    /**
     * 查询一天的短信发送量
     * @param date
     * @param mobile
     * @return
     */
    public long getLastDayMsg(Date date, String mobile, Integer channel);

    /**
     * 查询一小时的发送量
     * @param date
     * @param mobile
     * @return
     */
    public long getLastHourMsg(Date date, String mobile, Integer channel);

}
