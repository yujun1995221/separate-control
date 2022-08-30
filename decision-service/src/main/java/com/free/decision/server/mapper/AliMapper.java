package com.free.decision.server.mapper;

import com.free.decision.server.model.AliFace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 阿里云接口
 */
@Mapper
public interface AliMapper {

    /**
     * 保存阿里云实人认证
     * @param aliFace
     * @return
     */
    public int addAliFace(AliFace aliFace);

    /**
     * 根据bizId修改阿里云实人认证结果
     * @param bizId
     * @param result
     * @return
     */
    public int updateFaceByBizId(@Param("bizId") String bizId, @Param("result") String result);
}
