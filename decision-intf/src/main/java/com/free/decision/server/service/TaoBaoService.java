package com.free.decision.server.service;

import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.TaoBaoParamsVO;

/**
 * 淘宝认证
 */
public interface TaoBaoService {

    /**
     * 创建淘宝订单
     * @param taoBaoParamsVO
     * @return
     */
    public Result createOrder(TaoBaoParamsVO taoBaoParamsVO, Company company);

    /**
     * 查询数据和报告
     */
    public void queryData();
}
