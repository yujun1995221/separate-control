package com.free.decision.server.service;

import com.free.decision.server.model.MxRecord;

/**
 * 魔蝎
 */
public interface MxService {

    /**
     * 保存请求记录
     * @param mxRecord
     * @return
     */
    public long addRecord(MxRecord mxRecord);

    /**
     * 根据和type查询
     * @param phone
     * @param type
     * @return
     */
    public MxRecord loadByPhoneAndType(String phone, int type);

}
