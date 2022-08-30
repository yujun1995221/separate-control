package com.free.decision.server.service.impl;

import com.free.decision.server.apis.XyApi;
import com.free.decision.server.mapper.XyMapper;
import com.free.decision.server.model.*;
import com.free.decision.server.model.vo.DecisionCreditReportVO;
import com.free.decision.server.model.vo.DecisionRadarParamsVO;
import com.free.decision.server.model.vo.XyActionRadarVO;
import com.free.decision.server.model.vo.XyRadarActionParamVO;
import com.free.decision.server.service.XyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class XyServiceImpl implements XyService {

    @Resource
    private XyMapper xyMapper;

    @Resource
    private XyApi xyApi;

    /**
     * 查询多投信贷报告分页数据
     * @param xyRadarActionParamVO
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<XyActionRadarVO> getPageData(XyRadarActionParamVO xyRadarActionParamVO, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<XyActionRadarVO> list = xyMapper.getPageData(xyRadarActionParamVO);
        PageInfo<XyActionRadarVO> pageInfo = new PageInfo<>(list);
        PageHelper.clearPage();
        return pageInfo;
    }

    @Override
    public Result getRadarReport(DecisionRadarParamsVO decisionRadarParamsVO, Company company) {
        return xyApi.getRadar(decisionRadarParamsVO, company);
    }

    @Override
    public XyActionRadar getReportById(long id) {
        return xyMapper.getReportById(id);
    }

    @Override
    public DecisionCreditReportVO getLastXyDecisionData(long userId) {
        return xyMapper.getLastXyDecisionData(userId);
    }

    @Override
    public int insertRadar(XyActionRadarParam xyActionRadarParam) {
        return xyMapper.insertRadar(xyActionRadarParam);
    }

    @Override
    public long insertRecord(XyRecord xyRecord) {
        return xyMapper.insertRecord(xyRecord);
    }

    @Override
    public String getXyRecord(long id) {
        return xyMapper.getXyRecord(id);
    }

}
