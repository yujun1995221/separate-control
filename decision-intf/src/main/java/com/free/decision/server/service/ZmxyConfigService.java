package com.free.decision.server.service;

import com.free.decision.server.model.ZmxyConfig;
import com.free.decision.server.model.vo.DecisonZmxyConfigVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 芝麻信用决策参数配置接口类
 * @author yxs
 */
public interface ZmxyConfigService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ZmxyConfig loadById(long id);

    /**
     * 分页查询
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<ZmxyConfig> getPageData(long companyId, int pageNumber, int pageSize);

    /**
     * 查询列表
     * @return
     */
    public List<ZmxyConfig> getAllZmxyConfig(long companyId);

    /**
     * 查询芝麻信用分配置
     * @param companyId
     * @param province
     * @return
     */
    public DecisonZmxyConfigVO getZmxyScore(long companyId, String province);
}
