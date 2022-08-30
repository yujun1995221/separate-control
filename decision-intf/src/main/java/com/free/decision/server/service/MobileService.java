package com.free.decision.server.service;

import com.free.decision.server.model.Company;
import com.free.decision.server.model.MobileOrder;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.MobileParamsVO;

/**
 * 运营商
 */
public interface MobileService {

    /**
     * 创建运营商订单
     * @param mobileParamsVO
     * @return
     */
    public Result createOrder(MobileParamsVO mobileParamsVO, Company company);

    /**
     * 运营商缓存下单
     * @param mobileParamsVO
     * @param company
     * @return
     */
    public Result cacheData(MobileParamsVO mobileParamsVO, Company company);

    /**
     * 查询数据和报告
     */
    public void queryData();

    /**
     * 修改状态
     * @param status
     * @param type 1：原始数据 2：报告数据
     * @param orderNo
     * @return
     */
    public int updateStatus(int status, int type, String orderNo, String token);

    /**
     * 修改淘宝订单结果
     * @param type 1：原始数据 2：报告数据
     * @param result
     * @param orderNo
     * @return
     */
    public int updateResult(int type, String result, String orderNo);

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    public MobileOrder loadByOrderNo(String orderNo);

    /**
     * 查询用户最新的运营商认证
     * @param userId
     * @return
     */
    public MobileOrder queryLastByUserId(long userId, int channel);

}
