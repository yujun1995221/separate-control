package com.free.decision.server.mapper;

import com.free.decision.server.model.ZmxyConfig;
import com.free.decision.server.model.vo.DecisonZmxyConfigVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 芝麻信用决策参数配置接口
 * @author yxs
 */
@Mapper
public interface ZmxyConfigMapper {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public ZmxyConfig selectByPrimaryKey(@Param("id") long id);

    /**
     * 查询列表
     * @return
     */
    public List<ZmxyConfig> getAllZmxyConfig(@Param("companyId") long companyId);

    /**
     * 查询芝麻信用分配置
     * @param companyId
     * @param province
     * @return
     */
    public DecisonZmxyConfigVO getZmxyScore(@Param("companyId") long companyId, @Param("province") String province);
}