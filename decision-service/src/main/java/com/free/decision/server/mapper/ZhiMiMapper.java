package com.free.decision.server.mapper;

import com.free.decision.server.model.ZhiMiRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 指迷
 */
@Mapper
public interface ZhiMiMapper {

    public int insert(ZhiMiRecord zhiMiRecord);

}
