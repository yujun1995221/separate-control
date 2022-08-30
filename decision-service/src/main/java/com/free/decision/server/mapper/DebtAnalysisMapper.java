package com.free.decision.server.mapper;

import com.free.decision.server.model.CustomerRecord;
import com.free.decision.server.model.CreditCompany;
import com.free.decision.server.model.LiabilitiesParam;
import com.free.decision.server.model.OrderRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DebtAnalysisMapper {

    //获取每家商户的数据源信息
    List<CreditCompany> getAllDataSource();

    //批量插入订单流水临时表
    int batchInsertOrderRecordTemp(@Param("orderRecordList") List<OrderRecord> orderRecordList);

    //批量插入订单流水临时表
    int batchInsertCustomerRecordTemp(@Param("customerRecordList") List<CustomerRecord> customerRecordList);

    //删除订单流水临时表
    int dropOrderTempTable();

    //删除用户流水临时表
    int dropCustomerTempTable();

    //创建用户流水临时表
    int createCustomerTempTable();

    //创建订单流水临时表
    int createOrderTempTable();

    //删除订单流水正式表
    int dropOrderTable();

    //重命名订单临时表
    int renameOrderTempTable();

    //删除订单流水正式表
    int dropCustomerTable();

    //重命名订单临时表
    int renameCustomerTempTable();

    LiabilitiesParam getLiabilitiesReport(String idNumber);

    int getNumExpire(String idNumber);
}

