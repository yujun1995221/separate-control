package com.free.decision.server.mapper;

import com.free.decision.server.model.TaoBaoOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 淘宝订单记录
 */
@Mapper
public interface TaoBaoOrderMapper {

    /**
     * 新增新颜淘宝订单记录
     * @param taoBaoOrder
     * @return
     */
    public int insert(TaoBaoOrder taoBaoOrder);

    /**
     * 更新状态
     * @param status
     * @param type 1：原始数据 2：报告数据
     * @param orderNo
     * @return
     */
    public int updateStatus(@Param("status") int status, @Param("type") int type, @Param("orderNo") String orderNo,
                            @Param("token") String token);

    public TaoBaoOrder queryByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询进行中的订单
     * @return
     */
    public List<TaoBaoOrder> getDoingOrder();

    /**
     * 查询用户最新的淘宝认证
     * @param userId
     * @return
     */
    public TaoBaoOrder queryLastByUserId(@Param("userId") long userId, @Param("channel") int channel);

    /**
     * 修改淘宝订单结果
     * @param type 1：原始数据 2：报告数据
     * @param result
     * @param orderNo
     * @return
     */
    public int updateResult(@Param("type") int type, @Param("result") String result, @Param("orderNo") String orderNo);

    /**
     * 更新获取次数
     * @param type
     * @param taobaoOrderId
     * @return
     */
    public int updateTimes(@Param("type") int type, @Param("taobaoOrderId") long taobaoOrderId);
}
