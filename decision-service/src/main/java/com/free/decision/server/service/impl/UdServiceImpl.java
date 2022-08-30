package com.free.decision.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.apis.UdApi;
import com.free.decision.server.enums.BankVerifTypeEnum;
import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.enums.UdReturnTypeEnum;
import com.free.decision.server.mapper.UdMapper;
import com.free.decision.server.mapper.UdYunXiangMapper;
import com.free.decision.server.model.*;
import com.free.decision.server.model.vo.DecisionUdReportVO;
import com.free.decision.server.model.vo.UdInterfaceParamsVO;
import com.free.decision.server.service.ConsumeService;
import com.free.decision.server.service.UdService;
import com.free.decision.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 有盾
 * @author Xingyf
 */
@Service
public class UdServiceImpl implements UdService {

    private static Logger logger = LoggerFactory.getLogger(UdServiceImpl.class);

    @Resource
    private UdMapper udMapper;

    @Resource
    private UdYunXiangMapper udYunXiangMapper;

    /**
     * 有盾接口类
     */
    @Resource
    private UdApi udApi;

    /**
     * 用户信息接口类
     */
    @Resource
    private UserService userService;

    /**
     * 消费记录接口类
     */
    @Resource
    private ConsumeService consumeService;

    @Override
    public DecisionUdReportVO getLastUdDecisionData(long userId) {
        return udMapper.getLastUdDecisionData(userId);
    }

    /**
     * 调用U盾接口 获取相关信息并保存
     * @param udInterfaceParamsVO
     * @return
     */
    @Override
    @Transactional
    public Result getCreditReport(UdInterfaceParamsVO udInterfaceParamsVO, Company company){
        // 获取用户userId
        Long userId = userService.getUserIdByMobileAndIdNumber(company.getId(),udInterfaceParamsVO.getMobile(),udInterfaceParamsVO.getIdNo(),udInterfaceParamsVO.getName());
        // 调用有盾接口
        Result result = udApi.getCreditReport(udInterfaceParamsVO, company.getId(),userId);
        if(!result.isSuccess()){
            return result;
        }
        // 获取有盾接口结果集
        Map<String, String> resultMap = result.getData();
        logger.info("有盾反欺诈接口返回数据成功 ===> companyId_"+company.getId()+"_idNo_"+udInterfaceParamsVO.getIdNo());
        String orderId = resultMap.get("orderId");
        String ret = resultMap.get("ret");
        JSONObject retJson = JSON.parseObject(ret);
        String body = retJson.getString("body");
        JSONObject bodyJsonObject = JSON.parseObject(body);
        bodyJsonObject.remove("ud_order_no");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        UdCredit udCredit = new UdCredit();
        // 解析并设置身份证详情
        String idDetail = bodyJsonObject.getString("id_detail");
        JSONObject idJson = JSON.parseObject(idDetail);
        DateTimeFormatter formatter1 = DateTimeFormat.forPattern("yyyy.MM.dd");
        udCredit.setUserId(userId);
        udCredit.setCode(orderId);
        String birthday = idJson.getString("birthday");
        udCredit.setBirthday(birthday == null ? null : formatter1.parseLocalDate(birthday.split("-")[0]).toDate());
        udCredit.setAddress(idJson.getString("address"));
        udCredit.setNames(idJson.getString("names"));
        udCredit.setGender(idJson.getString("gender"));
        udCredit.setIdNumberMask(idJson.getString("id_number_mask"));
        udCredit.setNation(idJson.getString("nation"));
        udCredit.setProvince(idJson.getString("province"));
        udCredit.setCity(idJson.getString("city"));
        udCredit.setIssuingAgency(idJson.getString("issuing_agency"));
        udCredit.setNameCredible(idJson.getString("name_credible"));

        // 解析并设置设备关联信息
        String graphDetail = bodyJsonObject.getString("graph_detail");
        if(StringUtils.isNotBlank(graphDetail)){
            JSONObject graphDetailJson = JSON.parseObject(graphDetail);
            udCredit.setMobileCount(graphDetailJson.getInteger("mobile_count"));
            udCredit.setLinkUserCount(graphDetailJson.getInteger("link_user_count"));
            udCredit.setOtherLinkDeviceCount(graphDetailJson.getInteger("other_link_device_count"));

            // 解析并设置关联用户详情
            String linkUserDetail = graphDetailJson.getString("link_user_detail");
            if(StringUtils.isNotBlank(linkUserDetail)){
                JSONObject linkUserDetailJson = JSON.parseObject(linkUserDetail);
                udCredit.setPartnerMarkCount(linkUserDetailJson.getInteger("partner_mark_count"));
                udCredit.setCourtDishonestCount(linkUserDetailJson.getInteger("court_dishonest_count"));
                udCredit.setOnlineDishonestCount(linkUserDetailJson.getInteger("online_dishonest_count"));
                udCredit.setLivingAttackCount(linkUserDetailJson.getInteger("living_attack_count"));
            }

            // 解析并设置名下银行卡数
            udCredit.setUserHaveBankcardCount(graphDetailJson.getInteger("user_have_bankcard_count"));

            // 解析并设置其他关联设详情
            String otherLinkDeviceDetail = graphDetailJson.getString("other_link_device_detail");
            if(StringUtils.isNotBlank(otherLinkDeviceDetail)){
                JSONObject otherLinkDeviceDetailJson = JSON.parseObject(otherLinkDeviceDetail);
                udCredit.setOtherFraudDeviceCount(otherLinkDeviceDetailJson.getInteger("other_frand_device_count"));
                udCredit.setOtherLivingAttackDeviceCount(otherLinkDeviceDetailJson.getInteger("other_living_attack_device_count"));
            }

            // 解析并设置本商户内用户数
            udCredit.setPartnerUserCount(graphDetailJson.getInteger("partner_user_count"));
            udCredit.setBankcardCount(graphDetailJson.getInteger("bankcard_count"));

            // 解析并设置使用设备数详情
            String linkDeviceDetail = graphDetailJson.getString("link_device_detail");
            if(StringUtils.isNotBlank(linkDeviceDetail)){
                JSONObject linkDeviceDetailJson = JSON.parseObject(linkDeviceDetail);
                udCredit.setFraudDeviceCount(linkDeviceDetailJson.getInteger("frand_device_count"));
                udCredit.setLivingAttackDeviceCount(linkDeviceDetailJson.getInteger("living_attack_device_count"));
            }

            // 解析并设置使用设备数
            udCredit.setLinkDeviceCount(graphDetailJson.getInteger("link_device_count"));
        }

        // 解析并设置借款详情
        String loanDetail = bodyJsonObject.getString("loan_detail");
        if(StringUtils.isNotBlank(loanDetail)){
            JSONObject loanDetailJson = JSON.parseObject(loanDetail);
            udCredit.setActualLoanPlatformCount(loanDetailJson.getInteger("actual_loan_platform_count"));
            udCredit.setActualLoanPlatformCount1m(loanDetailJson.getInteger("actual_loan_platform_count_1m"));
            udCredit.setActualLoanPlatformCount3m(loanDetailJson.getInteger("actual_loan_platform_count_3m"));
            udCredit.setActualLoanPlatformCount6m(loanDetailJson.getInteger("actual_loan_platform_count_6m"));
            udCredit.setLoanPlatformCount(loanDetailJson.getInteger("loan_platform_count"));
            udCredit.setRepaymentTimesCount(loanDetailJson.getInteger("repayment_times_count"));
            udCredit.setRepaymentPlatformCount(loanDetailJson.getInteger("repayment_platform_count"));
            udCredit.setRepaymentPlatformCount1m(loanDetailJson.getInteger("repayment_platform_count_1m"));
            udCredit.setRepaymentPlatformCount3m(loanDetailJson.getInteger("repayment_platform_count_3m"));
            udCredit.setRepaymentPlatformCount6m(loanDetailJson.getInteger("repayment_platform_count_6m"));
            udCredit.setLoanPlatformCount1m(loanDetailJson.getInteger("loan_platform_count_1m"));
            udCredit.setLoanPlatformCount3m(loanDetailJson.getInteger("loan_platform_count_3m"));
            udCredit.setLoanPlatformCount6m(loanDetailJson.getInteger("loan_platform_count_6m"));
        }
        // 解析并设置风险模型得分
        String scoreDetail = bodyJsonObject.getString("score_detail");
        if(StringUtils.isNotBlank(scoreDetail)){
            JSONObject scoreDetailJson = JSON.parseObject(scoreDetail);
            udCredit.setScore(scoreDetailJson.getInteger("score"));
            udCredit.setRiskEvaluation(scoreDetailJson.getString("risk_evaluation"));
        }
        Date storeTime = new Date();
        udCredit.setLastModifiedTime(storeTime);
        udCredit.setCreateTime(storeTime);
        // 插入有盾关联信息
        udMapper.insertUdCredit(udCredit);

        // 获取有盾主键ID
        Long udCreditId = udCredit.getId();
        // 解析并设置关联设备信息
        JSONArray devices = bodyJsonObject.getJSONArray("devices_list");
        if(null != devices && devices.size()>0){
            List<UdCreditDevices> udCreditDevicesList = new ArrayList<>();
            for (int i = 0; i<devices.size();i++) {
                UdCreditDevices udCreditDevices = new UdCreditDevices();
                udCreditDevices.setUdCreditId(udCreditId);
                udCreditDevices.setDeviceName(devices.getJSONObject(i).getString("device_name"));
                udCreditDevices.setDeviceId(devices.getJSONObject(i).getString("device_id"));
                udCreditDevices.setDeviceLinkIdCount(devices.getJSONObject(i).getInteger("device_link_id_count"));
                String deviceLastUseDate = devices.getJSONObject(i).getString("device_last_use_date");
                udCreditDevices.setDeviceLastUseDate(deviceLastUseDate == null ? null :formatter.parseLocalDate(deviceLastUseDate).toDate());
                String deviceDetail = devices.getJSONObject(i).getString("device_detail");
                if(StringUtils.isNotBlank(deviceDetail)){
                    JSONObject deviceDetails = JSON.parseObject(deviceDetail);
                    udCreditDevices.setAppInstalmentCount(deviceDetails.getInteger("app_instalment_count"));
                    udCreditDevices.setIsRooted(deviceDetails.getInteger("is_rooted"));
                    udCreditDevices.setFraudDevice(deviceDetails.getInteger("fraud_device"));
                    udCreditDevices.setCheatsDevice(deviceDetails.getInteger("cheats_device"));
                    udCreditDevices.setIsUsingProxyPort(deviceDetails.getInteger("is_using_proxy_port"));
                    udCreditDevices.setNetworkType(deviceDetails.getString("network_type"));
                    udCreditDevices.setLivingAttack(deviceDetails.getInteger("living_attack"));
                    udCreditDevicesList.add(udCreditDevices);
                }
            }
            // 批量插入关联设备信息
            udMapper.insertUdCreditDevices(udCreditDevicesList);
        }

        // 解析并设置有盾用户特征数据
        JSONArray features = bodyJsonObject.getJSONArray("user_features");
        if(null != features && features.size()>0){
            List<UdCreditUserFeature> udCreditUserFeatureList = new ArrayList<>();
            for (int i = 0; i<features.size();i++){
                UdCreditUserFeature udCreditUserFeature = new UdCreditUserFeature();
                udCreditUserFeature.setUdCreditId(udCreditId);
                udCreditUserFeature.setFeatureType(features.getJSONObject(i).getString("user_feature_type"));
                String lastModifiedDate = features.getJSONObject(i).getString("last_modified_date");
                udCreditUserFeature.setLastModifiedDate(lastModifiedDate == null ? null :formatter.parseLocalDate(lastModifiedDate).toDate());
                udCreditUserFeatureList.add(udCreditUserFeature);
            }
            // 批量插入有盾用户特征数据
            udMapper.insertUdCreditUserFeature(udCreditUserFeatureList);
        }

        /*
        // 第三方接口不返回  解析并设置有盾借款详情数据
        if(StringUtils.isNotBlank(loanDetail)) {
            JSONObject loanDetailJson = JSON.parseObject(loanDetail);
            JSONArray loanIndustrys = loanDetailJson.getJSONArray("loan_industry");
            if(null != loanIndustrys && loanIndustrys.size()>0){
                List<UdCreditLoanIndustry> udCreditLoanIndustryList = new ArrayList<>();
                for(int i = 0; i<loanIndustrys.size(); i++){
                    UdCreditLoanIndustry udCreditLoanIndustry = new UdCreditLoanIndustry();
                    udCreditLoanIndustry.setUdCreditId(udCreditId);
                    udCreditLoanIndustry.setActualLoanPlatformCount(loanIndustrys.getJSONObject(i).getInteger("actual_loan_platform_count"));
                    udCreditLoanIndustry.setName(loanIndustrys.getJSONObject(i).getString("name"));
                    udCreditLoanIndustry.setLoanPlatformCount(loanIndustrys.getJSONObject(i).getInteger("loan_platform_count"));
                    udCreditLoanIndustry.setRepaymentPlatformCount(loanIndustrys.getJSONObject(i).getInteger("repayment_platform_count"));
                    udCreditLoanIndustry.setRepaymentTimesCount(loanIndustrys.getJSONObject(i).getInteger("repayment_times_count"));
                    udCreditLoanIndustryList.add(udCreditLoanIndustry);
                }
                // 批量插入有盾借款详情数据
                udMapper.insertUdCreditLoanIndustry(udCreditLoanIndustryList);
            }
        }*/

        // 组装返回JSON对象数据
        JSONObject retObject = new JSONObject();
        //设置日期格式
        retObject.put("store_time",DateFormatUtils.format(storeTime,"yyyy-MM-dd HH:mm:ss"));
        retObject.put("body",bodyJsonObject);
        logger.info("有盾反欺诈接口数据解析成功 ===> companyId_"+company.getId()+"_idNo_"+udInterfaceParamsVO.getIdNo());
        // 添加消费记录
        consumeService.consume(company.getId(),company.getAntiFraudPrice(),ReportTypeEnum.ANTI_FRAUD,ConsumeTypeEnum.CONSUME,"查询反欺诈报告-"+udInterfaceParamsVO.getMobile());
        return new Result(true,UdReturnTypeEnum.SUCCESS.getCode(),UdReturnTypeEnum.SUCCESS.getResultDesc(),retObject);
    }

    @Override
    public List<Integer> getDeviceUseCount(long udCreditId) {
        return udMapper.getDeviceUseCount(udCreditId);
    }

    /**
     * 插入请求记录
     * @param udRecord
     * @return
     */
    @Override
    public Long insertUdRecord(UdRecord udRecord){
        return udMapper.insertUdRecord(udRecord);
    }

    /**
     * 根据ID查询返回的Data
     * @param id
     * @return
     */
    @Override
    public String getUdRecordResultById(long id){
        return udMapper.getUdRecordResultById(id);
    }

    /**
     * 有盾实名认证
     * @param udResult
     * @param idNo
     * @param idName
     * @param company
     * @return
     */
    @Override
    @Transactional
    public Result getRealName(String udResult, String phone,String idNo, String idName, Company company) {
        //获取userId(调用以下方法，若没这个用户新增)
        Long userId = userService.getUserIdByMobileAndIdNumber(company.getId(), phone, idNo,idName);
        //插入udSdkRecord调用记录
        UdRecord udRecord = new UdRecord();
        udRecord.setUserId(userId);
        udRecord.setType(BankVerifTypeEnum.REAL_NAME.getId());
        udRecord.setCompanyId(company.getId());
        udRecord.setResult(udResult);
        insertUdRecord(udRecord);
        //插入消费记录
        consumeService.consume(company.getId(), company.getRealNamePrice(), ReportTypeEnum.REAL_NAME, ConsumeTypeEnum.CONSUME, "实名认证-" + phone);
        return new Result(true, "实名认证成功", udResult);
    }
}
