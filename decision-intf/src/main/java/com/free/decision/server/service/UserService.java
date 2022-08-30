package com.free.decision.server.service;


import com.free.decision.server.model.vo.DecisionRecordCodeVO;
import com.free.decision.server.model.vo.UserInfoParamsVO;
import com.free.decision.server.model.vo.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户信息接口
 */
public interface UserService {
    /**
     * 根据手机号和身份证号查询用户ID
     *
     * @param companyId
     * @param mobile
     * @param idNumber
     * @return
     */
    public Long loadUserIdByMobileAndIdNumber(long companyId, String mobile, String idNumber);

    /**
     * 根据手机号和身份证号插入用户
     *
     * @param companyId
     * @param mobile
     * @param idNumber
     * @return
     */
    public long insertByMobileAndIdNumber(long companyId, String mobile, String idNumber, String name, String province);

    /**
     * 获得用户id（仔细看看你能不能用！）
     *
     * @param companyId
     * @param mobile
     * @param idNumber
     * @return
     */
    public long getUserIdByMobileAndIdNumber(long companyId, String mobile, String idNumber, String name);

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    public UserVO getUserInfoById(long id);

    /**
     * 用户信息分页数据查询
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageInfo<UserInfoParamsVO> getUserPageData(long companyId, String createTimeStart, String createTimeEnd, String name, String idNumber, String phone, String code, Integer resultType, int pageNumber, int pageSize);


    /**
     * 导出用户信息
     *
     * @param companyId
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    public List<Map<String, String>> exportUserData(long companyId, String createTimeStart, String createTimeEnd, String name, String idNumber, String phone, String code, Integer resultType);


    /**
     * 查询决策命中点
     *
     * @param decisionReportId
     * @return
     */
    public List<DecisionRecordCodeVO> getRecordCode(long decisionReportId);

}
