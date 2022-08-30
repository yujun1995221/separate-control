package com.free.decision.server.mapper;

import com.free.decision.server.model.UdYunXiang;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xuechenye
 * @create 2019-04-02 17:38
 **/
@Mapper
public interface UdYunXiangMapper {

    /**
     * 新增云相结果记录
     * @param udYunXiang
     * @return
     */
    Long insertYunXiang(UdYunXiang udYunXiang);
}
