package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.AliMapper;
import com.free.decision.server.model.AliFace;
import com.free.decision.server.service.AliService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 阿里云接口
 */
@Service
public class AliServiceImpl implements AliService {

    @Resource
    private AliMapper aliMapper;

    @Override
    public int addAliFace(AliFace aliFace) {
        return aliMapper.addAliFace(aliFace);
    }

    @Override
    public int updateFaceByBizId(String bizId, String result) {
        return aliMapper.updateFaceByBizId(bizId, result);
    }
}
