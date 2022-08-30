package com.free.decision.server.service;

import com.free.decision.server.model.CreditConfigCompany;
import com.free.decision.server.model.Result;

/**
 * 多投反欺诈商户决策参数配置接口类
 * @author yxs
 */
public interface CreditConfigCompanyService {

    /**
     * 根据id查询
     * @param id
     * @param companyId
     * @return
     */
    public CreditConfigCompany loadById(long id, long companyId);

    /**
     * 根据configId查询
     * @param configId
     * @return
     */
    public CreditConfigCompany loadByConfigId(long configId, long companyId);

    /**
     * 新增方法
     * @param creditConfigCompany
     * @return
     */
    public Result addCreditConfigCompany(CreditConfigCompany creditConfigCompany);

    /**
     * 修改方法
     * @param val
     * @param configId
     * @param companyId
     * @return
     */
    public Result editCreditConfigCompany(String val, long configId, long companyId);

    /**
     * 修改开启/关闭状态
     * @param isOpen
     * @param configId
     * @param companyId
     * @return
     */
    public Result updateOpenType(int isOpen, long configId, long companyId);

}
