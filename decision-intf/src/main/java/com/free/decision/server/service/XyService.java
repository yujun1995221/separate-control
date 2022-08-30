package com.free.decision.server.service;

import com.free.decision.server.model.*;
import com.free.decision.server.model.vo.DecisionCreditReportVO;
import com.free.decision.server.model.vo.DecisionRadarParamsVO;
import com.free.decision.server.model.vo.XyActionRadarVO;
import com.free.decision.server.model.vo.XyRadarActionParamVO;
import com.github.pagehelper.PageInfo;


public interface XyService {

    public PageInfo<XyActionRadarVO> getPageData(XyRadarActionParamVO xyRadarActionParamVO, int pageNumber, int pageSize);

    /**
     * 获取行为雷达报告
     * @param decisionRadarParamsVO
     * @param company
     * @return
     */
    public Result getRadarReport(DecisionRadarParamsVO decisionRadarParamsVO, Company company);

    /**
     * 查询多投信贷报告
     *
     * @param id
     * @return
     */
    public XyActionRadar getReportById(long id);

    /**
     * 查询用户最新的一条行为雷达决策数据
     *
     * @param userId
     * @return
     */
    public DecisionCreditReportVO getLastXyDecisionData(long userId);

    /**
     * 插入行为雷达数据
     *
     * @param xyActionRadarParam
     * @return
     */
    public int insertRadar(XyActionRadarParam xyActionRadarParam);

    /**
     * 插入记录表数据
     *
     * @param xyRecord
     * @return
     */
    public long insertRecord(XyRecord xyRecord);

    /**
     * 查询行为雷达请求接口调用记录
     *
     * @param id
     * @return
     */
    public String getXyRecord(long id);
}
