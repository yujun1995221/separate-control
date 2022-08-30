package com.free.decision.server.service.impl;

import com.free.decision.server.enums.SmsContentEnum;
import com.free.decision.server.mapper.SmsSendMapper;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.SmsSend;
import com.free.decision.server.model.vo.SmsSendParamVO;
import com.free.decision.server.service.SmsSendService;
import com.free.decision.server.sms.SmsSendFactory;
import com.free.decision.server.sms.SmsSendStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 短信发送记录
 * @author Xingyf
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Resource
    private SmsSendMapper smsSendMapper;

    @Resource
    private SmsSendFactory smsSendFactory;

    @Value("${sms_channel}")
    private int smsChannel;

    @Value("${today_sms_channel}")
    private int todaySmsChannel;

    @Override
    public int add(SmsSend smsSend) {
        return smsSendMapper.insert(smsSend);
    }

    /**
     * 发送单条短信
     * @param smsSendParamVO
     * @param company
     * @return
     */
    @Override
    public Result send(SmsSendParamVO smsSendParamVO, Company company) {
        SmsSendStrategy smsSendStrategy = smsSendFactory.getStrategy(smsChannel);
        return smsSendStrategy.send(smsSendParamVO, company);
    }

    /**
     * 发送批量短信
     * @param smsSendParamVO
     * @param company
     * @return
     */
    @Override
    public Result batchSend(SmsSendParamVO smsSendParamVO, Company company) {
        if(SmsContentEnum.TODAY.getId() == smsSendParamVO.getSmsType()){
            // 根据配置文件中短信渠道 调用实现批量发送短信接口的方法
            return smsSendFactory.getStrategy(todaySmsChannel).batchSend(smsSendParamVO, company);
        }
        // 调用实现批量发送短信接口的方法
        return smsSendFactory.getStrategy(smsChannel).batchSend(smsSendParamVO, company);
    }

    @Override
    public long getLastDayMsg(Date date, String mobile, Integer channel) {
        return smsSendMapper.getLastDayMsg(date, mobile, channel);
    }

    @Override
    public long getLastHourMsg(Date date, String mobile, Integer channel) {
        return smsSendMapper.getLastHourMsg(date, mobile, channel);
    }

}
