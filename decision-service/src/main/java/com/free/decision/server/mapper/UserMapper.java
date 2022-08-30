package com.free.decision.server.mapper;

import com.free.decision.server.model.User;
import com.free.decision.server.model.vo.DecisionRecordCodeVO;
import com.free.decision.server.model.vo.UserInfoParamsVO;
import com.free.decision.server.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息
 */
@Mapper
public interface UserMapper {

    /**
     * 分页查询用户信息
     *
     * @param companyId
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    public List<UserInfoParamsVO> getUserPageData(@Param("companyId") long companyId, @Param("createTimeStart") String createTimeStart, @Param("createTimeEnd") String createTimeEnd, @Param("name") String name, @Param("idNumber") String idNumber, @Param("phone") String phone, @Param("code") String code, @Param("resultType") Integer resultType);

    /**
     * 根据手机号和身份证号查询用户ID
     *
     * @param companyId
     * @param mobile
     * @param idNumber
     * @return
     */
    public Long loadUserIdByMobileAndIdNumber(@Param("companyId") long companyId, @Param("mobile") String mobile, @Param("idNumber") String idNumber);

    public Long insertIdByMobileAndIdNumber(User user);

    /**
     * 查询决策命中点
     *
     * @param decisionReportId
     * @return
     */
    public List<DecisionRecordCodeVO> loadRecodCode(@Param("decisionReportId") long decisionReportId);

    public UserVO getUserInfoById(@Param("id") long id);

}
