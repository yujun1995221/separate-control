package com.free.decision.server.mapper;

import com.free.decision.server.model.UdCredit;
import com.free.decision.server.model.UdCreditDevices;
import com.free.decision.server.model.UdCreditLoanIndustry;
import com.free.decision.server.model.UdCreditUserFeature;
import com.free.decision.server.model.UdFeaturesDic;
import com.free.decision.server.model.vo.UdCreditParamVO;
import com.free.decision.server.model.vo.UdCreditUserInfoVO;
import com.free.decision.server.model.vo.UdUserFeatureDicVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UdCreditMapper {

    /**
     * 查询用户特征字典表
     * @return
     */
    List<UdUserFeatureDicVO> getUdFeaturesDic();

    /**
     * 查询分页数据
     * @param udCreditParamVO
     * @return
     */
    List<UdCreditUserInfoVO> getPageData(UdCreditParamVO udCreditParamVO);

    /**
     * 查询有盾报告
     * @param id
     * @return
     */
    public UdCredit loadInfoById(@Param("id") long id);

    /**
     * 查询用户特征描述
     * @param id
     * @return
     */
    public List<UdFeaturesDic> loadUserFeatureInfo(@Param("id") long id);

    /**
     * 查询用户特征
     * @param id
     * @return
     */
    public List<UdCreditUserFeature> loadUserFeatureById(@Param("id") long id);

    /**
     * 查询关联设备信息
     * @param id
     * @return
     */
    public List<UdCreditDevices> loadDeviceById(@Param("id") long id);

    /**
     * 查询有盾行业借款详情
     * @param id
     * @return
     */
    public List<UdCreditLoanIndustry> loadLoanIndustryById(@Param("id") long id);

}
