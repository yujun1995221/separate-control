package com.free.decision.server.service;

import com.free.decision.server.model.UdCreditUserFeature;
import com.free.decision.server.model.vo.UdCreditParamVO;
import com.free.decision.server.model.vo.UdCreditUserInfoVO;
import com.free.decision.server.model.vo.UdCreditVO;
import com.free.decision.server.model.vo.UdUserFeatureDicVO;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UdCreditService {

    /**
     * 查询用户特征字典
     * @return
     */
    List<UdUserFeatureDicVO> loadUserFeatureDic();

    /**
     * 查询用户特征
     * @param id
     * @return
     */
    public List<UdCreditUserFeature> loadUserFeatureById(long id);

    /**
     * 查询反欺诈报告
     * @param id
     * @return
     */
    public UdCreditVO loadInfoById(long id);

    /**
     * 查询反欺诈报告分页数据
     * @param udCreditParamVO
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<UdCreditUserInfoVO> getPageData(UdCreditParamVO udCreditParamVO, int pageNumber, int pageSize);




}
