package com.free.decision.server.mapper;

import com.free.decision.server.model.CreditConfigCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 多投反欺诈商户决策参数配置接口
 * @author yxs
 */
@Mapper
public interface CreditConfigCompanyMapper {

    /**
     * 新增方法
     * @param creditConfigCompany
     * @return
     */
    public int insert(CreditConfigCompany creditConfigCompany);

    /**
     * 根据ID和公司ID查询
     * @param id
     * @param companyId
     * @return
     */
    public CreditConfigCompany selectByPrimaryKey(@Param("id") long id, @Param("companyId") long companyId);

    /**
     * 根据configId和公司ID查询
     * @param configId
     * @param companyId
     * @return
     */
    public CreditConfigCompany loadByConfigId(@Param("configId") long configId, @Param("companyId") long companyId);

    /**
     * 修改方法
     * @param creditConfigCompany
     * @return
     */
    public int updateByPrimaryKey(CreditConfigCompany creditConfigCompany);

    /**
     * 修改开启/关闭状态
     * @param isOpen
     * @param configId
     * @param companyId
     * @return
     */
    public int updateOpenType(@Param("isOpen") int isOpen, @Param("configId") long configId, @Param("companyId") long companyId);
}