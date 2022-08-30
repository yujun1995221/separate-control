package com.free.decision.server.mapper;

import com.free.decision.server.model.UdCredit;
import com.free.decision.server.model.UdCreditDevices;
import com.free.decision.server.model.UdCreditLoanIndustry;
import com.free.decision.server.model.UdCreditUserFeature;
import com.free.decision.server.model.UdRecord;
import com.free.decision.server.model.vo.DecisionUdReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 有盾
 */
@Mapper
public interface UdMapper {

    /**
     * 查询用户最新的反欺诈报告数据
     * @param userId
     * @return
     */
    public DecisionUdReportVO getLastUdDecisionData(@Param("userId") long userId);

    public Long insertUdCredit(UdCredit udCredit);

    public Long insertUdCreditDevices(@Param("udCreditDevicesList") List<UdCreditDevices> udCreditDevices);

    public Long insertUdCreditLoanIndustry(@Param("udCreditLoanIndustryList") List<UdCreditLoanIndustry> udCreditLoanIndustry);

    public Long insertUdCreditUserFeature(@Param("udCreditUserFeatureList") List<UdCreditUserFeature> udCreditUserFeature);

    public Long insertUdRecord(UdRecord udRecord);

    public String getUdRecordResultById(@Param("id") long id);

    /**
     * 查询所有设备 使用用户数
     * @param udCreditId
     * @return
     */
    public List<Integer> getDeviceUseCount(@Param("udCreditId") long udCreditId);

}
