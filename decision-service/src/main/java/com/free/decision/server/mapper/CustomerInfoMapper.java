package com.free.decision.server.mapper;

import com.free.decision.server.model.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户信息
 * @author Xingyf
 */
@Mapper
public interface CustomerInfoMapper {

    public int insert(CustomerInfo customerInfo);

    /**
     * 逻辑删除
     * @param idNumber
     * @return
     */
    public int logicDeleteByIdNumber(@Param("idNumber") String idNumber, @Param("companyId") long companyId);

    public long loadCountByIdNumber(@Param("idNumber") String idNumber, @Param("companyId") long companyId);

    /**
     * 根据身份证查询id
     * @param idNumber
     * @param companyId
     * @return
     */
    public Long loadIdByIdNumber(@Param("idNumber") String idNumber, @Param("companyId") long companyId);

    /**
     * 根据身份证查询客户信息
     * @param idNumber
     * @param companyId
     * @return
     */
    public CustomerInfo loadByIdNumber(@Param("idNumber") String idNumber, @Param("companyId") long companyId);

    /**
     * 根据身份证号查询 用户ID集合
     * @param idNumber
     * @return
     */
    public List<Long> getCustomerIdsByIdNumber(@Param("idNumber") String idNumber);
}
