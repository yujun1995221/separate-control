package com.free.decision.server.mapper;

import com.free.decision.server.model.SmsSend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 短信发送记录
 * @author Xingyf
 */
@Mapper
public interface SmsSendMapper {

    /**
     * 新增
     * @param smsSend
     * @return
     */
    public int insert(SmsSend smsSend);

    /**
     * 过去一天短信发送量
     * @param date
     * @return
     */
    public long getLastDayMsg(@Param("date") Date date, @Param("mobile") String mobile,@Param("channel") Integer channel);

    /**
     * 过去一小时短信发送量
     * @param date
     * @return
     */
    public long getLastHourMsg(@Param("date") Date date, @Param("mobile") String mobile,@Param("channel") Integer channel);

}
