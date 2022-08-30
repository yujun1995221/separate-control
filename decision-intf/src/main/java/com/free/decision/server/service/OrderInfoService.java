package com.free.decision.server.service;

import com.free.decision.server.model.vo.LiabilityDataVO;
import com.free.decision.server.model.vo.OrderInfoVO;
import com.free.decision.server.model.vo.PersistOrderInfoVO;

/**
 * 订单接口
 */
public interface OrderInfoService {

    public int push(OrderInfoVO orderInfoVO);

    /**
     * 逻辑删除
     * @param customerId
     * @return
     */
    public int logicDeleteByCustomerId(long customerId);

    /**
     * 查询负债共享数据
     * @param idNumber
     * @param companyId
     * @return
     */
    public LiabilityDataVO getOrderInfo(String idNumber, long companyId);

    /**
     * 推送续期订单
     * @param persistOrderInfoVO
     * @return
     */
    public int pushPersistOrderInfo(PersistOrderInfoVO persistOrderInfoVO);

    /**
     * 逻辑删除续期订单信息
     * @param customerId
     * @return
     */
    public int logicDeletePersistOrderByCustomerId(long customerId);
}
