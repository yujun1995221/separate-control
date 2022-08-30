package com.free.decision.server.service;

import com.free.decision.server.model.BldRecord;

/**
 * 宝莲灯接口
 */
public interface BldService {

    /**
     * 根据和type查询黑名单
     * @param phone
     * @param type
     * @return
     */
    public BldRecord loadBlackByPhoneAndType(String phone, int type);

    public long insert(BldRecord bldRecord);
}
