package com.free.decision.server.mapper;

import com.free.decision.server.model.OrderInfo;
import com.free.decision.server.model.PersistOrderInfo;
import com.free.decision.server.model.vo.LiabilityDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单信息
 */
@Mapper
public interface OrderInfoMapper {

    public int addOrderInfo(OrderInfo orderInfo);

    /**
     * 逻辑删除
     * @param customerId
     * @return
     */
    public int logicDeleteByCustomerId(@Param("customerId") long customerId);

    /**
     * 更新
     * @param orderInfo
     * @return
     */
    public int update(OrderInfo orderInfo);

    public long loadCountByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 查询负债共享订单信息
     * @param customerIds
     * @return
     */
    public List<LiabilityDataVO.LiabilityOrderInfo> getLiabilityOrderInfo(@Param("customerIds") List<Long> customerIds);

    /**
     * 查询续期订单count
     * @param persistNumber
     * @param orderNumber
     * @param customerId
     * @return
     */
    public long loadCountByPersistNumber(@Param("persistNumber") String persistNumber, @Param("orderNumber") String orderNumber, @Param("customerId") long customerId);

    /**
     * 新增续期订单信息
     * @param persistOrderInfo
     * @return
     */
    public int addPersistOrder(PersistOrderInfo persistOrderInfo);

    /**
     * 逻辑删除续期订单信息
     * @param customerId
     * @return
     */
    public int logicDeletePersistOrderByCustomerId(@Param("customerId") long customerId);

}
