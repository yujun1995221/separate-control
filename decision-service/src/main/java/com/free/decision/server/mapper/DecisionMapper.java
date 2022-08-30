package com.free.decision.server.mapper;

import com.free.decision.server.model.vo.DecisionRecordCountVO;
import com.free.decision.server.model.vo.DecisionTypeVO;
import com.free.decision.server.model.vo.HitRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 决策信息
 */
@Mapper
public interface DecisionMapper {

    /**
     * 获取今日总量
     * @param companyId
     * @return
     */
    public Integer loadByDecisionCount(@Param("companyId") long companyId);


    /**
     * 查询决策首贷记录数量
     * @param companyId
     * @return
     */
    public List<DecisionTypeVO> loadByDecisionHitNewCount(@Param("companyId") long companyId, @Param("createTimeStart") String createTimeStart, @Param("createTimeEnd") String createTimeEnd);

    /**
     * 查询决策复贷记录数量
     * @param companyId
     * @return
     */
    public List<DecisionTypeVO> loadByDecisionHitReCount(@Param("companyId") long companyId, @Param("createTimeStart") String createTimeStart, @Param("createTimeEnd") String createTimeEnd);


    /**
     * 查询决策信用类型code数量
     * @param companyId
     * @return
     */
    public List<HitRecordVO> loadByDecisionAllCount(@Param("companyId") long companyId, @Param("group") Integer group, @Param("type") Integer type, @Param("createTimeStart") String createTimeStart, @Param("createTimeEnd") String createTimeEnd);


    /**
     * 查询决策结果数量
     * @param companyId
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    public List<DecisionRecordCountVO> loadByCount(@Param("companyId") long companyId, @Param("createTimeStart") String createTimeStart, @Param("createTimeEnd") String createTimeEnd);


}
