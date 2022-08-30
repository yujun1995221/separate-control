package com.free.decision.server.mapper;

import com.free.decision.server.model.BlackList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 黑名单库
 * @author Xingyf
 */
@Mapper
public interface BlackListMapper {

    /**
     * 根据身份证号查询 Xingyf
     * @param idNumber
     * @return
     */
    public long loadCountByIdNumber(@Param("idNumber") String idNumber);

    /**
     * 插入黑名单
     * @Param blackList
     * @return
     */
    public long batchInsertBlackList(@Param("blackLists") List<BlackList> blackLists);
}
