package com.free.decision.server.mapper;

import com.free.decision.server.model.CreditConfig;
import com.free.decision.server.model.vo.DecisionCreditConfigVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 多投反欺诈决策参数配置接口
 * @author yxs
 */
@Mapper
public interface CreditConfigMapper {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public CreditConfig selectByPrimaryKey(@Param("id") long id);

    /**
     * 根据type查询列表
     * @param type 1：首贷、2：复贷
     * @return
     */
    public List<CreditConfig> getAllCreditConfig(@Param("type") int type, @Param("companyId") long companyId);

    /**
     * 查询公司多头与反欺诈配置
     * @param companyId
     * @return
     */
    public List<DecisionCreditConfigVO> getCompanyConfig(@Param("companyId") long companyId, @Param("type") int type);
}