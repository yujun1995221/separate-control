package com.free.decision.server.service;

import com.free.decision.server.model.NoticeRecord;

public interface NoticeRecordService {

    /**
     * 异步通知
     * @return
     */
    public void  asyncNotice();


    public int insertRecord(NoticeRecord noticeRecord);
}
