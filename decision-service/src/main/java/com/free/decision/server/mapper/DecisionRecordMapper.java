package com.free.decision.server.mapper;

import com.free.decision.server.model.DecisionRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 决策记录
 * @author Xingyf
 */
@Mapper
public interface DecisionRecordMapper {

    /**
     * 添加一条决策记录
     * @param decisionRecord
     * @return
     */
    public int add(DecisionRecord decisionRecord);

    /**
     * 修改
     * @param decisionRecord
     * @return
     */
    public int update(DecisionRecord decisionRecord);

}
