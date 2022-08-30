package com.free.decision.server.service;

import com.free.decision.server.model.DecisionRecord;

/**
 * 决策记录
 */
public interface DecisionRecordService {

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
