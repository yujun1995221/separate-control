package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.CreditConfigMapper;
import com.free.decision.server.model.CreditConfig;
import com.free.decision.server.model.vo.DecisionCreditConfigVO;
import com.free.decision.server.service.CreditConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多投反欺诈决策参数配置实现类
 * @author yxs
 */
@Service
public class CreditConfigServiceImpl implements CreditConfigService {

    @Resource
    private CreditConfigMapper creditConfigMapper;

    /**
     * 根据ID查询
     * @param id
     * @return CreditConfig
     */
    @Override
    public CreditConfig loadById(long id) {
        return creditConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<CreditConfig> getPageData(int type, long companyId, int pageNumber, int pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        List<CreditConfig> list = getAllCreditConfig(type,companyId);
        PageInfo<CreditConfig> pageInfo = new PageInfo<>(list);
        PageHelper.clearPage();
        return pageInfo;
    }

    /**
     * 查询列表
     * @param type
     * @return List<CreditConfig>
     */
    @Override
    public List<CreditConfig> getAllCreditConfig(int type, long companyId) {
        return creditConfigMapper.getAllCreditConfig(type,companyId);
    }

    @Override
    public List<DecisionCreditConfigVO> getCompanyConfig(long companyId, int type) {
        return creditConfigMapper.getCompanyConfig(companyId, type);
    }

}
