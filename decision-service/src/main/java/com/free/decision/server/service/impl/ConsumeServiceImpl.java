package com.free.decision.server.service.impl;


import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.mapper.ConsumeMapper;
import com.free.decision.server.model.Consume;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.ConsumeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 消费记录实现类
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Resource
    private ConsumeMapper consumeMapper;


    @Resource
    private CompanyService companyService;


    /**
     * 消费记录查询
     *
     * @param companyId
     * @param createTimeStart
     * @param createTimeEnd
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Consume> getPageData(long companyId, String createTimeStart, String createTimeEnd, Integer type, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Consume> list = consumeMapper.getPageData(companyId, createTimeStart, createTimeEnd, type);
        PageInfo<Consume> pageInfo = new PageInfo<>(list);
        PageHelper.clearPage();
        return pageInfo;
    }

    @Override
    public int addConsume(Consume consume) {
        return consumeMapper.addConsume(consume);
    }

    @Override
    @Transactional
    public void consume(long companyId, BigDecimal amount, ReportTypeEnum reportType, ConsumeTypeEnum consumeType, String desc) {
        //新增一条消费记录
        Consume consume = new Consume();
        consume.setCompanyId(companyId);
        consume.setReportType(reportType.getId());
        consume.setAmount(amount);
        consume.setType(consumeType.getId());
        consume.setDescription(desc);
        addConsume(consume);
        //更新公司余额
        companyService.updateAmount(companyId, amount, consumeType);
    }

    @Override
    public List<Consume> loadConsumeRecord(long companyId, String createTimeStart, String createTimeEnd, Integer type) {
        return consumeMapper.getPageData(companyId, createTimeStart, createTimeEnd, type);
    }
}
