package com.free.decision.server.service;

import com.free.decision.server.model.AliFace;

/**
 * 阿里云接口
 */
public interface AliService {

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
    public int updateFaceByBizId(String bizId, String result);

}
