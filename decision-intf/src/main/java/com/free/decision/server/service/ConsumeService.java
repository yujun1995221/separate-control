package com.free.decision.server.service;

import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.model.Consume;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 消费记录
 */
public interface ConsumeService {


    /**
     * 财务信息分页数据查询
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<Consume> getPageData(long companyId, String createTimeStart, String createTimeEnd, Integer type, int pageNumber, int pageSize);

    /**
     * 新增一条消费记录
     *
     * @param consume
     * @return
     */
    public int addConsume(Consume consume);

    /**
     * 消费或充值
     *
     * @param companyId
     * @param amount
     * @param reportType
     * @param consumeType
     * @param desc
     * @return
     */
    public void consume(long companyId, BigDecimal amount, ReportTypeEnum reportType, ConsumeTypeEnum consumeType, String desc);

    /**
     * 查询消费记录
     * @param companyId
     * @return
     */
    public List<Consume> loadConsumeRecord(long companyId, String createTimeStart, String createTimeEnd, Integer type);
}
