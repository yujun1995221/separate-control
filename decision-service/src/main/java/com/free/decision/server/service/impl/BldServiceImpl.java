package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.BldMapper;
import com.free.decision.server.model.BldRecord;
import com.free.decision.server.service.BldService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 宝莲灯接口
 */
@Service
public class BldServiceImpl implements BldService {

    @Resource
    private BldMapper bldMapper;

    @Override
    public BldRecord loadBlackByPhoneAndType(String phone, int type) {
        return bldMapper.loadBlackByPhoneAndType(phone, type);
    }

    @Override
    public long insert(BldRecord bldRecord) {
        return bldMapper.insert(bldRecord);
    }
}
