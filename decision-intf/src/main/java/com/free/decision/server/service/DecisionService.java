package com.free.decision.server.service;

import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.DecisionParamsVO;

/**
 * 决策
 * @author Xingyf
 */
public interface DecisionService {

    /**
     * 全自动决策
     * @param decisionParamsVO
     * @param company
     * @return
     */
    public Result decision(DecisionParamsVO decisionParamsVO, Company company);
}
