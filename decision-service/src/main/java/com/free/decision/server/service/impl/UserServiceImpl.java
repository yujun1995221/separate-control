package com.free.decision.server.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.free.decision.server.enums.DecisionRecordResultTypeEnum;
import com.free.decision.server.enums.DecisionRecordStatusEnum;
import com.free.decision.server.mapper.UserMapper;
import com.free.decision.server.model.User;
import com.free.decision.server.model.vo.DecisionRecordCodeVO;
import com.free.decision.server.model.vo.UserInfoParamsVO;
import com.free.decision.server.model.vo.UserVO;
import com.free.decision.server.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jodd.util.StringUtil;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息实现类
 */
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private CacheChannel cacheChannel;


    @Override
    public Long loadUserIdByMobileAndIdNumber(long companyId, String mobile, String idNumber) {
        Long userId = (Long) cacheChannel.get("decision.userid", "company_user_" + companyId + "_" + mobile + "_" + idNumber).getValue();
        if (userId == null) {
            userId = userMapper.loadUserIdByMobileAndIdNumber(companyId, mobile, idNumber);
            cacheChannel.set("decision.userid", "company_user_" + companyId + "_" + mobile + "_" + idNumber, userId);
        }
        return userId;
    }

    @Override
    public long insertByMobileAndIdNumber(long companyId, String mobile, String idNumber, String name, String province) {
        User user = new User();
        user.setCompanyId(companyId);
        user.setMobile(mobile);
        user.setIdNumber(idNumber);
        user.setName(name);
        user.setProvince(province);
        userMapper.insertIdByMobileAndIdNumber(user);
        long userId = user.getId();
        cacheChannel.set("decision.userid", "company_user_" + companyId + "_" + mobile + "_" + idNumber, userId);
        return userId;
    }

    @Override
    public long getUserIdByMobileAndIdNumber(long companyId, String mobile, String idNumber, String name) {
        Long userId = loadUserIdByMobileAndIdNumber(companyId, mobile, idNumber);
        return userId == null ? insertByMobileAndIdNumber(companyId, mobile, idNumber, name, IdcardUtil.getProvinceByIdCard(idNumber)) : userId;
    }

    @Override
    public UserVO getUserInfoById(long id) {
        return userMapper.getUserInfoById(id);
    }


    /**
     * 分页查询用户信息
     *
     * @param companyId
     * @param createTimeStart
     * @param createTimeEnd
     * @param name
     * @param idNumber
     * @param phone
     * @param resultType
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<UserInfoParamsVO> getUserPageData(long companyId, String createTimeStart, String createTimeEnd, String name, String idNumber, String phone, String code, Integer resultType, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<UserInfoParamsVO> list = userMapper.getUserPageData(companyId, createTimeStart, createTimeEnd, name, idNumber, phone, code, resultType);
        PageInfo<UserInfoParamsVO> pageInfo = new PageInfo<>(list);
        PageHelper.clearPage();
        return pageInfo;
    }


    /**
     * 导出用户信息
     *
     * @param companyId
     * @param createTimeStart
     * @param createTimeEnd
     * @param name
     * @param idNumber
     * @param phone
     * @param resultType
     * @return
     */
    @Override
    public List<Map<String, String>> exportUserData(long companyId, String createTimeStart, String createTimeEnd, String name, String idNumber, String phone, String code, Integer resultType) {
        List<UserInfoParamsVO> list = userMapper.getUserPageData(companyId, createTimeStart, createTimeEnd, name, idNumber, phone, code, resultType);
        List<Map<String, String>> exportList = new ArrayList<>();
        for (UserInfoParamsVO userInfoParamsVO : list) {
            Map<String, String> dataMap = new LinkedHashMap<>();
            dataMap.put("姓名", (StringUtil.isBlank(userInfoParamsVO.getName()) ? "" : userInfoParamsVO.getName()));
            dataMap.put("身份证", (StringUtil.isBlank(userInfoParamsVO.getIdNumber()) ? "" : userInfoParamsVO.getIdNumber()));
            dataMap.put("手机号码", userInfoParamsVO.getPhone());
            dataMap.put("时间", DateFormatUtils.format(userInfoParamsVO.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            if(DecisionRecordStatusEnum.getEnum(userInfoParamsVO.getStatus()) != null){
                dataMap.put("状态", DecisionRecordStatusEnum.getEnum(userInfoParamsVO.getStatus()).getName());
            }else{
                dataMap.put("状态", "-");
            }
            if(DecisionRecordResultTypeEnum.getEnum(userInfoParamsVO.getResultType()) != null){
                dataMap.put("结果", DecisionRecordResultTypeEnum.getEnum(userInfoParamsVO.getResultType()).getName());
            }else{
                dataMap.put("结果", "-");
            }
            exportList.add(dataMap);
        }
        return exportList;
    }


    /**
     * 查询决策命中点
     *
     * @param decisionReportId
     * @return
     */
    @Override
    public List<DecisionRecordCodeVO> getRecordCode(long decisionReportId) {
        return userMapper.loadRecodCode(decisionReportId);
    }
}
