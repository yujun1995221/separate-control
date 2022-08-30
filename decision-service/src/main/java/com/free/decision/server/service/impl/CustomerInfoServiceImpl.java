package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.CustomerInfoMapper;
import com.free.decision.server.model.CustomerInfo;
import com.free.decision.server.model.vo.CustomerInfoVO;
import com.free.decision.server.service.CustomerInfoService;
import com.free.decision.server.service.OrderInfoService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户信息
 * @author Xingyf
 */
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Resource
    private CustomerInfoMapper customerInfoMapper;

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private Redisson redisson;

    @Override
    public int push(CustomerInfoVO customerInfoVO) {
        RLock lock = redisson.getLock("push_customer_info_"+customerInfoVO.getIdNumber());
        lock.lock(5, TimeUnit.MINUTES);
        long count = customerInfoMapper.loadCountByIdNumber(customerInfoVO.getIdNumber(), customerInfoVO.getCompanyId());
        if(count > 0){
            lock.unlock();
            return 0;
        }
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCompanyId(customerInfoVO.getCompanyId());
        customerInfo.setIdNumber(customerInfoVO.getIdNumber());
        customerInfo.setPhone(customerInfoVO.getPhone());
        customerInfo.setName(customerInfoVO.getName());
        customerInfo.setIdAddress(customerInfoVO.getIdAddress());
        customerInfo.setRegisterTime(customerInfoVO.getRegisterTime());
        int k = customerInfoMapper.insert(customerInfo);
        lock.unlock();
        return k;
    }

    @Override
    public Long loadIdByIdNumber(String idNumber, long companyId) {
        return customerInfoMapper.loadIdByIdNumber(idNumber, companyId);
    }

    @Override
    @Transactional
    public void deleteCustomer(String idNumber, long companyId) {
        Long customerId = loadIdByIdNumber(idNumber, companyId);
        if(customerId != null && customerId > 0){
            orderInfoService.logicDeleteByCustomerId(customerId);
            orderInfoService.logicDeletePersistOrderByCustomerId(customerId);
        }
        customerInfoMapper.logicDeleteByIdNumber(idNumber, companyId);
    }

    @Override
    public CustomerInfo loadByIdNumber(String idNumber, long companyId) {
        return customerInfoMapper.loadByIdNumber(idNumber, companyId);
    }

    /**
     * 根据身份证号查询 用户ID集合
     * @param idNumber
     * @return
     */
    public List<Long> getCustomerIdsByIdNumber(String idNumber){
        return customerInfoMapper.getCustomerIdsByIdNumber(idNumber);
    }
}
