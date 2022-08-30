package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.ZhiMiMapper;
import com.free.decision.server.model.ZhiMiRecord;
import com.free.decision.server.service.ZhiMiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZhiMiServiceImpl implements ZhiMiService {

    @Resource
    private ZhiMiMapper zhiMiMapper;

    @Override
    public int add(ZhiMiRecord zhiMiRecord) {
        return zhiMiMapper.insert(zhiMiRecord);
    }
}
