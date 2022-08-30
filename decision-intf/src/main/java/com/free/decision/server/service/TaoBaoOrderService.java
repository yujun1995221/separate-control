package com.free.decision.server.service;

import com.free.decision.server.model.TaoBaoOrder;

import java.util.List;

/**
 * 淘宝订单
 */
public interface TaoBaoOrderService {

    public int add(TaoBaoOrder taoBaoOrder);

    /**
     * 修改状态
     * @param status
     * @param type 1：原始数据 2：报告数据
     * @param orderNo
     * @return
     */
    public int updateStatus(int status, int type, String orderNo, String token);

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
    public TaoBaoOrder queryLastByUserId(long userId, int channel);

    /**
     * 修改淘宝订单结果
     * @param type 1：原始数据 2：报告数据
     * @param result
     * @param orderNo
     * @return
     */
    public int updateResult(int type, String result, String orderNo);

    /**
     * 更新获取次数
     * @param type
     * @param taobaoOrderId
     * @return
     */
    public int updateTimes(int type, long taobaoOrderId);
}
