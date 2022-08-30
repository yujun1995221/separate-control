package com.free.decision.server.mapper;

import com.free.decision.server.model.Consume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 财务管理
 */
@Mapper
public interface ConsumeMapper {

    /**
     * 查询财务管理分页数据
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    public List<Consume> getPageData(@Param("companyId") long companyId, @Param("createTimeStart") String createTimeStart,
                                       @Param("createTimeEnd") String createTimeEnd, @Param("type") Integer type);

    /**
     * 新增一条消费记录
     * @param consume
     * @return
     */
    public int addConsume(Consume consume);
}
