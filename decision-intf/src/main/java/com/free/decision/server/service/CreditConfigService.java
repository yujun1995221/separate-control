package com.free.decision.server.service;

import com.free.decision.server.model.CreditConfig;
import com.free.decision.server.model.vo.DecisionCreditConfigVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 多投反欺诈决策参数配置接口类
 * @author yxs
 */
public interface CreditConfigService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public CreditConfig loadById(long id);

    /**
     * 分页查询
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<CreditConfig> getPageData(int type, long companyId, int pageNumber, int pageSize);

    /**
     * 查询列表
     * @return
     */
    public List<CreditConfig> getAllCreditConfig(int type, long companyId);

    /**
     * 查询公司多头与反欺诈配置
     * @param companyId
     * @return
     */
    public List<DecisionCreditConfigVO> getCompanyConfig(long companyId, int type);

}
