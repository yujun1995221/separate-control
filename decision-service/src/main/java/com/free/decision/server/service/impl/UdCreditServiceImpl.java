package com.free.decision.server.service.impl;

import com.free.decision.server.model.UdCreditUserFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.free.decision.server.mapper.UdCreditMapper;
import com.free.decision.server.model.vo.UdCreditParamVO;
import com.free.decision.server.model.vo.UdCreditUserInfoVO;
import com.free.decision.server.model.vo.UdCreditVO;
import com.free.decision.server.model.vo.UdUserFeatureDicVO;
import com.free.decision.server.service.UdCreditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 有盾
 * @author Xingyf
 */
@Service
public class UdCreditServiceImpl implements UdCreditService {

    @Resource
    private UdCreditMapper udCreditMapper;

    /**
     * 查询用户特征字典表
     * @return
     */
    @Override
    public List<UdUserFeatureDicVO> loadUserFeatureDic() {
        return udCreditMapper.getUdFeaturesDic();
    }

    /**
     * 查询用户特征
     * @param id
     * @return
     */
    @Override
    public List<UdCreditUserFeature> loadUserFeatureById(long id) {
        return udCreditMapper.loadUserFeatureById(id);
    }

    /**
     * 获取报告
     * @param id
     * @return
     */
    @Override
    public UdCreditVO loadInfoById(long id) {
        UdCreditVO udCreditVO = new UdCreditVO();
        udCreditVO.setUdCredit(udCreditMapper.loadInfoById(id));
        udCreditVO.setUdCreditUserFeatureList(udCreditMapper.loadUserFeatureById(id));
        udCreditVO.setUdCreditDevices(udCreditMapper.loadDeviceById(id));
        udCreditVO.setUdFeaturesDicList(udCreditMapper.loadUserFeatureInfo(id));
        udCreditVO.setUdCreditLoanIndustryList(udCreditMapper.loadLoanIndustryById(id));
        return udCreditVO;
    }

    /**
     * 获取反欺诈报告分页数据
     * @param udCreditParamVO
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<UdCreditUserInfoVO> getPageData(UdCreditParamVO udCreditParamVO, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<UdCreditUserInfoVO> list = udCreditMapper.getPageData(udCreditParamVO);
        PageInfo<UdCreditUserInfoVO> pageInfo = new PageInfo<>(list);
        PageHelper.clearPage();
        return pageInfo;
    }
}
