package com.free.decision.server.service;

import com.free.decision.server.model.Result;
import com.free.decision.server.model.ZmxyConfigCompany;

/**
 * 芝麻信用商户决策参数配置接口类
 * @author yxs
 */
public interface ZmxyConfigCompanyService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ZmxyConfigCompany loadById(long id, long companyId);

    /**
     * 根据configId查询
     * @param configId
     * @return
     */
    public ZmxyConfigCompany loadByConfigId(long configId, long companyId);

    /**
     * 新增方法
     * @param zmxyConfigCompany
     * @return
     */
    public int addZmxyConfigCompany(ZmxyConfigCompany zmxyConfigCompany);

    /**
     * 修改方法
     * @param zmxyScore
     * @param configId
     * @param companyId
     * @return
     */
    public Result editZmxyConfigCompany(Integer zmxyScore, long configId, long companyId);

    /**
     * 修改开启/关闭状态
     * @param isOpen
     * @param configId
     * @param companyId
     * @return
     */
    public Result updateOpenType(int isOpen, long configId, long companyId);

}
