package com.free.decision.server.sms;

import com.free.decision.server.enums.SmsChannelEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * 短信发送bean适配
 * @author Xingyf
 */
@Service
public class SmsSendFactory implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() throws BeansException {
        return beanFactory;
    }

    public SmsSendStrategy getStrategy(int type) {
        return (SmsSendStrategy) beanFactory.getBean(SmsChannelEnum.getEnum(type).getBeanName());
    }
}
