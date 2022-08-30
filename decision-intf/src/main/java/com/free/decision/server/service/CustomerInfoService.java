package com.free.decision.server.service;

import com.free.decision.server.model.CustomerInfo;
import com.free.decision.server.model.vo.CustomerInfoVO;

import java.util.List;

/**
 * 客户信息
 * @author Xingyf
 */
public interface CustomerInfoService {

    public int push(CustomerInfoVO customerInfoVO);

    /**
     * 根据身份证查询id
     * @param idNumber
     * @param companyId
     * @return
     */
    public Long loadIdByIdNumber(String idNumber, long companyId);

    /**
     * 删除客户
     * @param idNumber
     * @param companyId
     * @return
     */
    public void deleteCustomer(String idNumber, long companyId);

    /**
     * 根据身份证查询客户信息
     * @param idNumber
     * @param companyId
     * @return
     */
    public CustomerInfo loadByIdNumber(String idNumber, long companyId);

    /**
     * 根据身份证号查询 用户ID集合
     * @param idNumber
     * @return
     */
    public List<Long> getCustomerIdsByIdNumber(String idNumber);
}
