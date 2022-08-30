package com.free.decision.server.mapper;

import com.free.decision.server.model.NoticeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeRecordMapper {

    /**
     * 查询flag = 0 & times <=5 的id
     * @return
     */
    public List<Long> getNeedSendNoticeId(@Param("times") int times);

    /**
     * 查询异步通知记录
     * @return
     */
    public NoticeRecord getNoticeById(@Param("id") long id, @Param("times") int times);

    /**
     * 根据id更新flag
     * @param id
     * @return
     */
    public void updateSuccess(@Param("id") long id);

    /**
     * 根据id更新times
     * @param id
     * @return
     */
    public void updateTimes(@Param("id") long id);

    public int insertRecord(NoticeRecord noticeRecord);

}
