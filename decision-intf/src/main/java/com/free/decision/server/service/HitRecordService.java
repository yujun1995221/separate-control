package com.free.decision.server.service;

import com.free.decision.server.model.HitRecord;

import java.util.List;

/**
 * 命中记录
 * @author Xingyf
 */
public interface HitRecordService {

    /**
     * 批量插入命中记录
     * @param hitRecordList
     */
    public void batchAddRecord(List<HitRecord> hitRecordList);

}
