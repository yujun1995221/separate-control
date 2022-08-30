package com.free.decision.server.service.impl;

import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.mapper.OrderInfoMapper;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.CustomerInfo;
import com.free.decision.server.model.OrderInfo;
import com.free.decision.server.model.PersistOrderInfo;
import com.free.decision.server.model.vo.LiabilityDataVO;
import com.free.decision.server.model.vo.OrderInfoVO;
import com.free.decision.server.model.vo.PersistOrderInfoVO;
import com.free.decision.server.service.*;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.collections.CollectionUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订单实现类
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private CustomerInfoService customerInfoService;

    @Resource
    private CacheChannel cacheChannel;

    @Resource
    private ConsumeService consumeService;

    @Resource
    private CompanyService companyService;

    @Resource
    private BlackListService blackListService;

    @Resource
    private Redisson redisson;

    @Override
    public int push(OrderInfoVO orderInfoVO) {
        if(orderInfoVO.getCompanyId() == null){
            return 0;
        }
        Long customerId = customerInfoService.loadIdByIdNumber(orderInfoVO.getIdNumber(), orderInfoVO.getCompanyId());
        if(customerId == null){
            return 0;
        }
        RLock lock = redisson.getLock("push_order_info_"+orderInfoVO.getOrderNumber());
        lock.lock(5, TimeUnit.MINUTES);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCustomerId(customerId);
        orderInfo.setCompanyId(orderInfoVO.getCompanyId());
        orderInfo.setOrderNumber(orderInfoVO.getOrderNumber());
        orderInfo.setSellPrice(orderInfoVO.getSellPrice());
        orderInfo.setRentPeriod(orderInfoVO.getRentPeriod());
        orderInfo.setLoanDate(orderInfoVO.getLoanDate());
        orderInfo.setOverdueDate(orderInfoVO.getOverdueDate());
        orderInfo.setCutRealMoney(orderInfoVO.getCutRealMoney());
        orderInfo.setRepaymentDate(orderInfoVO.getRepaymentDate());
        orderInfo.setFinanceStatus(orderInfoVO.getFinanceStatus());
        orderInfo.setOrderStatus(orderInfoVO.getOrderStatus());
        orderInfo.setPersistFlag(orderInfoVO.getPersistFlag());
        orderInfo.setPersistCount(orderInfoVO.getPersistCount());
        orderInfo.setEquipmentName(orderInfoVO.getEquipmentName());
        orderInfo.setEquipmentNumber(orderInfoVO.getEquipmentNumber());
        orderInfo.setLendStatus(orderInfoVO.getLendStatus());
        orderInfo.setApplyTime(orderInfoVO.getApplyTime());
        long count = orderInfoMapper.loadCountByOrderNumber(orderInfo.getOrderNumber());
        if(count > 0){
            int k = orderInfoMapper.update(orderInfo);
            lock.unlock();
            return k;
        }
        int k = orderInfoMapper.addOrderInfo(orderInfo);
        lock.unlock();
        return k;
    }

    @Override
    public int logicDeleteByCustomerId(long customerId) {
        return orderInfoMapper.logicDeleteByCustomerId(customerId);
    }

    @Override
    public LiabilityDataVO getOrderInfo(String idNumber, long companyId) {
        CustomerInfo customerInfo = customerInfoService.loadByIdNumber(idNumber, companyId);
        if(customerInfo == null){
            return null;
        }
        List<Long> customerIds = customerInfoService.getCustomerIdsByIdNumber(idNumber);
        LiabilityDataVO liabilityDataVO = new LiabilityDataVO();
        liabilityDataVO.setIdNumber(customerInfo.getIdNumber());
        liabilityDataVO.setName(customerInfo.getName());
        liabilityDataVO.setPhone(customerInfo.getPhone());
        liabilityDataVO.setIdAddress(customerInfo.getIdAddress());
        List<LiabilityDataVO.LiabilityOrderInfo> orderInfoList = orderInfoMapper.getLiabilityOrderInfo(customerIds);
        liabilityDataVO.setOrderInfoList(orderInfoList);
        long count = blackListService.loadCountByIdNumber(idNumber);
        if(count == 0){
            liabilityDataVO.setBlackFlag(false);
        }else {
            liabilityDataVO.setBlackFlag(true);
        }
        if(CollectionUtils.isNotEmpty(orderInfoList)){
            Object obj = cacheChannel.get("decision.liability.data", "data_"+idNumber+"_"+companyId).getValue();
            if(obj == null){
                //消费记录 扣费
                Company company = companyService.loadById(companyId);
                consumeService.consume(companyId, company.getLiabilityPrice(), ReportTypeEnum.LIABILITY, ConsumeTypeEnum.CONSUME, "查询负债共享数据-"+customerInfo.getPhone());
                cacheChannel.set("decision.liability.data", "data_"+idNumber+"_"+companyId, 1);
            }
        }
        return liabilityDataVO;
    }

    @Override
    public int pushPersistOrderInfo(PersistOrderInfoVO persistOrderInfoVO) {
        CustomerInfo customerInfo = customerInfoService.loadByIdNumber(persistOrderInfoVO.getIdNumber(), persistOrderInfoVO.getCompanyId());
        if(customerInfo == null){
            return 0;
        }
        long count = orderInfoMapper.loadCountByPersistNumber(persistOrderInfoVO.getPersistNumber(), persistOrderInfoVO.getOrderNumber(), customerInfo.getId());
        if(count > 0){
            return 0;
        }
        PersistOrderInfo persistOrderInfo = new PersistOrderInfo();
        persistOrderInfo.setOrderNumber(persistOrderInfoVO.getOrderNumber());
        persistOrderInfo.setPersistNumber(persistOrderInfoVO.getPersistNumber());
        persistOrderInfo.setCustomerId(customerInfo.getId());
        persistOrderInfo.setMainOrderOverdueTime(persistOrderInfoVO.getMainOrderOverdueTime());
        persistOrderInfo.setPersistTime(persistOrderInfoVO.getPersistTime());
        return orderInfoMapper.addPersistOrder(persistOrderInfo);
    }

    @Override
    public int logicDeletePersistOrderByCustomerId(long customerId) {
        return orderInfoMapper.logicDeletePersistOrderByCustomerId(customerId);
    }
}
