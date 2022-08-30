package com.free.decision.server.mapper;

import com.free.decision.server.model.XyActionRadar;
import com.free.decision.server.model.XyActionRadarParam;
import com.free.decision.server.model.XyRecord;
import com.free.decision.server.model.vo.DecisionCreditReportVO;
import com.free.decision.server.model.vo.XyActionRadarVO;
import com.free.decision.server.model.vo.XyRadarActionParamVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface XyMapper {

    /**
     * 查询所有多投报告列表
     * @param xyRadarActionParamVO
     * @return
     */
    List<XyActionRadarVO> getPageData(XyRadarActionParamVO xyRadarActionParamVO);

    /**
     * 查询多投信贷报告
     * @param id
     * @return
     */
    public XyActionRadar getReportById(@Param("id") long id);

    /**
     * 查询用户最新的一条决策数据
     * @param userId
     * @return
     */
    public DecisionCreditReportVO getLastXyDecisionData(@Param("userId") long userId);

    /**
     * 插入行为雷达数据
     * @param xyActionRadarParam
     * @return
     */
    public int insertRadar(XyActionRadarParam xyActionRadarParam);


    /**
     * 插入行为雷达请求接口调用记录
     * @param xyRecord
     * @return
     */
    public int insertRecord(XyRecord xyRecord);


    /**
     * 查询行为雷达请求接口调用记录
     * @param id
     * @return
     */
    public String getXyRecord(@Param("id") long id);
}
