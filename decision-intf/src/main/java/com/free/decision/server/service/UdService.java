package com.free.decision.server.service;

import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.UdRecord;
import com.free.decision.server.model.vo.DecisionUdReportVO;
import com.free.decision.server.model.vo.UdInterfaceParamsVO;

import java.util.List;

/**
 * 有盾
 * @author Xingyf
 */
public interface UdService {

    /**
     * 查询用户最新的反欺诈报告数据
     * @param userId
     * @return
     */
    public DecisionUdReportVO getLastUdDecisionData(long userId);

    public Result getCreditReport(UdInterfaceParamsVO udInterfaceParamsVO, Company company);

    /**
     * 查询所有设备 使用用户数
     * @param udCreditId
     * @return
     */
    public List<Integer> getDeviceUseCount(long udCreditId);

    /**
     * 插入请求记录
     * @param udRecord
     * @return
     */
    public Long insertUdRecord(UdRecord udRecord);

    /**
     * 根据ID查询记录的data
     * @param id
     * @return
     */
    public String getUdRecordResultById(long id);

    /***
     * 有盾实名认证
     * @param udResult
     * @param idNo
     * @param idName
     * @param company
     * @return
     */
    public Result getRealName(String udResult, String phone, String idNo, String idName, Company company);
}
