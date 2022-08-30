package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.HitRecordMapper;
import com.free.decision.server.model.HitRecord;
import com.free.decision.server.service.HitRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 命中记录
 * @author Xingyf
 */
@Service
public class HitRecordServiceImpl implements HitRecordService {

    @Resource
    private HitRecordMapper hitRecordMapper;

    @Override
    public void batchAddRecord(List<HitRecord> hitRecordList) {
        hitRecordMapper.batchAddRecord(hitRecordList);
    }
}
