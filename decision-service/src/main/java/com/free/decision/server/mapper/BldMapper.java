package com.free.decision.server.mapper;

import com.free.decision.server.model.BldRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 宝莲灯接口
 */
@Mapper
public interface BldMapper {

    /**
     * 根据和type查询
     * @param phone
     * @param type
     * @return
     */
    public BldRecord loadBlackByPhoneAndType(@Param("phone") String phone, @Param("type") int type);

    public long insert(BldRecord bldRecord);

}
