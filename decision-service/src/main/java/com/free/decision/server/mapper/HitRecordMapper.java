package com.free.decision.server.mapper;

import com.free.decision.server.model.HitRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 命中记录
 * @author Xingyf
 */
@Mapper
public interface HitRecordMapper {

    /**
     * 批量插入命中记录
     * @param hitRecordList
     */
    public void batchAddRecord(@Param("hitRecordList") List<HitRecord> hitRecordList);

}
