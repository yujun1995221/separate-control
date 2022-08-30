package com.free.decision.server.mapper;

import com.free.decision.server.model.ZmxyConfigCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 芝麻信用商户决策参数配置接口
 * @author yxs
 */
@Mapper
public interface ZmxyConfigCompanyMapper {

    /**
     * 新增方法
     * @param zmxyConfigCompany
     * @return
     */
    public int insert(ZmxyConfigCompany zmxyConfigCompany);

    /**
     * 根据ID和公司ID查询
     * @param id
     * @param companyId
     * @return
     */
    public ZmxyConfigCompany selectByPrimaryKey(@Param("id") long id, @Param("companyId") long companyId);

    /**
     * 根据configId和公司ID查询
     * @param configId
     * @param companyId
     * @return
     */
    public ZmxyConfigCompany loadByConfigId(@Param("configId") long configId, @Param("companyId") long companyId);

    /**
     * 修改方法
     * @param zmxyConfigCompany
     * @return
     */
    public int updateByPrimaryKey(ZmxyConfigCompany zmxyConfigCompany);

    /**
     * 修改开启/关闭状态
     * @param isOpen
     * @param configId
     * @param companyId
     * @return
     */
    public int updateOpenType(@Param("isOpen") int isOpen, @Param("configId") long configId, @Param("companyId") long companyId);
}