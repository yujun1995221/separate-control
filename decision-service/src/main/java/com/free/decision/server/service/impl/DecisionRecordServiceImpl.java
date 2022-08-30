package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.DecisionRecordMapper;
import com.free.decision.server.model.DecisionRecord;
import com.free.decision.server.service.DecisionRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 决策记录
 * @author Xingyf
 */
@Service
public class DecisionRecordServiceImpl implements DecisionRecordService {

    @Resource
    private DecisionRecordMapper decisionRecordMapper;

    @Override
    public int add(DecisionRecord decisionRecord) {
        return decisionRecordMapper.add(decisionRecord);
    }

    @Override
    public int update(DecisionRecord decisionRecord) {
        return decisionRecordMapper.update(decisionRecord);
    }
}
