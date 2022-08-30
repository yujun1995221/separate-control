package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.ZmxyConfigMapper;
import com.free.decision.server.model.ZmxyConfig;
import com.free.decision.server.model.vo.DecisonZmxyConfigVO;
import com.free.decision.server.service.ZmxyConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 芝麻信用决策参数配置实现类
 * @author yxs
 */
@Service
public class ZmxyConfigServiceImpl implements ZmxyConfigService {

    private static Logger logger = LoggerFactory.getLogger(ZmxyConfigServiceImpl.class);

    @Resource
    private ZmxyConfigMapper zmxyConfigMapper;

    /**
     * 根据ID查询
     * @param id
     * @return ZmxyConfig
     */
    @Override
    public ZmxyConfig loadById(long id) {
        return zmxyConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<ZmxyConfig> getPageData(long companyId, int pageNumber, int pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        List<ZmxyConfig> list = getAllZmxyConfig(companyId);
        PageInfo<ZmxyConfig> pageInfo = new PageInfo<>(list);
        PageHelper.clearPage();
        return pageInfo;
    }

    /**
     * 查询列表
     * @return List<ZmxyConfig>
     */
    @Override
    public List<ZmxyConfig> getAllZmxyConfig(long companyId) {
        return zmxyConfigMapper.getAllZmxyConfig(companyId);
    }

    @Override
    public DecisonZmxyConfigVO getZmxyScore(long companyId, String province){
        return zmxyConfigMapper.getZmxyScore(companyId, province);
    }
}
