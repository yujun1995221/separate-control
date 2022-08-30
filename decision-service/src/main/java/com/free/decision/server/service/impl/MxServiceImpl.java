package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.MxMapper;
import com.free.decision.server.model.MxRecord;
import com.free.decision.server.service.MxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 魔蝎
 */
@Service
public class MxServiceImpl implements MxService {

    @Resource
    MxMapper mxMapper;

    @Override
    public long addRecord(MxRecord mxRecord) {
        return mxMapper.insert(mxRecord);
    }

    @Override
    public MxRecord loadByPhoneAndType(String phone, int type) {
        return mxMapper.loadByPhoneAndType(phone, type);
    }
}
