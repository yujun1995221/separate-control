package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.TaoBaoOrderMapper;
import com.free.decision.server.model.TaoBaoOrder;
import com.free.decision.server.service.TaoBaoOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 淘宝订单
 */
@Service
public class TaoBaoOrderServiceImpl implements TaoBaoOrderService {

    @Resource
    private TaoBaoOrderMapper taoBaoOrderMapper;

    @Override
    public int add(TaoBaoOrder taoBaoOrder) {
        return taoBaoOrderMapper.insert(taoBaoOrder);
    }

    @Override
    public int updateStatus(int status, int type, String orderNo, String token) {
        return taoBaoOrderMapper.updateStatus(status, type, orderNo, token);
    }

    @Override
    public List<TaoBaoOrder> getDoingOrder() {
        return taoBaoOrderMapper.getDoingOrder();
    }

    @Override
    public TaoBaoOrder queryLastByUserId(long userId, int channel) {
        return taoBaoOrderMapper.queryLastByUserId(userId, channel);
    }

    @Override
    public int updateResult(int type, String result, String orderNo) {
        return taoBaoOrderMapper.updateResult(type, result, orderNo);
    }

    @Override
    public int updateTimes(int type, long taobaoOrderId) {
        return taoBaoOrderMapper.updateTimes(type, taobaoOrderId);
    }
}
