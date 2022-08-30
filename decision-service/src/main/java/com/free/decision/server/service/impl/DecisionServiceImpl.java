package com.free.decision.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.apis.BldApi;
import com.free.decision.server.apis.MxApi;
import com.free.decision.server.apis.XyApi;
import com.free.decision.server.apis.ZhiMiApi;
import com.free.decision.server.constants.ConfigConstant;
import com.free.decision.server.enums.*;
import com.free.decision.server.model.*;
import com.free.decision.server.model.vo.*;
import com.free.decision.server.service.*;
import com.free.decision.server.utils.AliOssUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;


/**
 * 决策
 *
 * @author Xingyf
 */
@Service
public class DecisionServiceImpl implements DecisionService {

    private static Logger logger = LoggerFactory.getLogger(DecisionServiceImpl.class);

    @Resource
    private ZmxyConfigService zmxyConfigService;

    @Resource
    private CreditConfigService creditConfigService;

    @Resource
    private UdService udService;

    @Resource
    private XyService xyService;

    @Resource
    private MobileService mobileService;

    @Resource
    private ConsumeService consumeService;

    @Resource
    private HitRecordService hitRecordService;

    @Resource
    private DecisionRecordService decisionRecordService;

    @Resource
    private BlackListService blackListService;

    @Resource
    private AliOssUtils aliOssUtils;

    @Resource
    private ZhiMiApi zhiMiApi;

    @Resource
    private MxApi mxApi;

    @Resource
    private XyApi xyApi;

    @Resource
    private BldApi bldApi;

    @Resource
    private UserService userService;

    @Resource
    private NoticeRecordService noticeRecordService;

    @Value("${oss.report.bucket}")
    private String reportBucket;

    @Override
    public Result decision(DecisionParamsVO decisionParamsVO, Company company) {
        Long userId = userService.loadUserIdByMobileAndIdNumber(company.getId(), decisionParamsVO.getPhone(), decisionParamsVO.getIdNumber());
        if (userId == null) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        //新颜行为雷达
        if(company.getCreditFlag() == CommonYesOrNoEnum.YES.getId()){
            xyApi.getRadarNew(company, decisionParamsVO.getIdNumber(), decisionParamsVO.getName(), userId);
        }
        long timestamp = new Date().getTime();
        String sign = SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp));
        Dict ret = Dict.create().set("timestamp", timestamp).set("other", decisionParamsVO.getOther()).set("sign", sign).set("force_reject", 0);
        if (company.getBlackFlag() == CommonYesOrNoEnum.YES.getId()) {
            //校验黑名单库
            if (StrUtil.isNotBlank(decisionParamsVO.getIdNumber())) {
                long count = blackListService.loadCountByIdNumber(decisionParamsVO.getIdNumber());
                if (count > 0) {
                    List<String> hit = new ArrayList<>();
                    hit.add("HMD");
                    ret.set("body", Dict.create().set("hit", hit)).set("score", -1).set("force_reject", 1);
                    //添加消费记录
                    consumeService.consume(company.getId(), company.getDecisionPrice(),
                            ReportTypeEnum.DECISION, ConsumeTypeEnum.CONSUME, "决策-" + decisionParamsVO.getPhone());
                    addNotice(decisionParamsVO.getCallback(), JSON.toJSONString(ret));
                    return new Result(true, ResultCodeEnum.SUCCESS.getId(), "请求成功", ret);
                }
            }
        }
        //宝莲灯黑名单
        if(company.getBldBlackFlag() == CommonYesOrNoEnum.YES.getId()){
            Result result = bldApi.black(userId, decisionParamsVO.getName(), decisionParamsVO.getPhone(), decisionParamsVO.getIdNumber(), company);
            logger.info("宝莲灯黑名单决策返回,phone:{},ret:{}", decisionParamsVO.getPhone(), JSON.toJSONString(result));
            if(result.isSuccess()){
                //命中黑名单
                List<String> hit = new ArrayList<>();
                hit.add("bld_black");
                ret.set("body", Dict.create().set("hit", hit)).set("score", -1).set("force_reject", 1);
                //添加消费记录
                consumeService.consume(company.getId(), company.getDecisionPrice().add(company.getBldBlackPrice()),
                        ReportTypeEnum.DECISION, ConsumeTypeEnum.CONSUME, "决策-" + decisionParamsVO.getPhone());
                addNotice(decisionParamsVO.getCallback(), JSON.toJSONString(ret));
                return new Result(true, ResultCodeEnum.SUCCESS.getId(), "请求成功", ret);
            }
        }
        //前置分控
        Result frontResult = frontDecision(decisionParamsVO, company);
        DecisionReportVO report = frontResult.getData();
        if(report.getDecisionResult() == DecisionRecordResultTypeEnum.REJECT.getId()){
            logger.info("前置分控条件成立，订单被拒，phone:{}", decisionParamsVO.getPhone());
            ret.set("body", Dict.create().set("hit", report.getHitRules())).set("score", -1);
            //添加消费记录
            consumeService.consume(company.getId(), company.getDecisionPrice(),
                    ReportTypeEnum.DECISION, ConsumeTypeEnum.CONSUME, "决策-" + decisionParamsVO.getPhone());
            addNotice(decisionParamsVO.getCallback(), JSON.toJSONString(ret));
            return new Result(true, ResultCodeEnum.SUCCESS.getId(), "请求成功", ret);
        }
        //魔杖分
        if(company.getMozhangFlag() == CommonYesOrNoEnum.YES.getId() && company.getMozhangScore() > 0){
           /* String mzRet = mxApi.mozhangStandard(decisionParamsVO.getName(), decisionParamsVO.getPhone(), decisionParamsVO.getIdNumber(), company.getId(), userId);
            logger.info("魔杖分返回:{}", mzRet);
            if(StrUtil.isNotBlank(mzRet)){
                JSONObject mzObj = JSON.parseObject(mzRet);
                Integer mzScore = mzObj.getInteger("mz_score");
                if(mzScore != null && mzScore >= 0 && mzScore < company.getMozhangScore()){
                    logger.info("魔杖分不满足条件，订单被拒，mzRet:{}, phone:{}", mzRet, decisionParamsVO.getPhone());
                    ret.set("body", Dict.create().set("mzScore", mzScore).set("rejectScore", company.getMozhangScore())).set("score", -1).set("force_reject", 1);
                    //添加消费记录
                    consumeService.consume(company.getId(), company.getDecisionPrice(),
                            ReportTypeEnum.DECISION, ConsumeTypeEnum.CONSUME, "决策-" + decisionParamsVO.getPhone());
                    addNotice(decisionParamsVO.getCallback(), JSON.toJSONString(ret));
                    return new Result(true, ResultCodeEnum.SUCCESS.getId(), "请求成功", ret);
                }
            }*/
        }
        Result result = new Result(false);
        logger.info("开始全自动分控，decisionChannel:{}，phone:{}", company.getDecisionChannel(), decisionParamsVO.getPhone());
        Double score = 0d;
        if(company.getDecisionChannel() == DecisionChannelEnum.ZHI_MI.getId()){
            //指迷分控
            result = zhiMiApi.decision(userId, decisionParamsVO, company);
            if(result.isSuccess()){
                score = result.getData();
            }
            logger.info("指迷分控最终返回phone:{}, ret：{}", decisionParamsVO.getPhone(), JSON.toJSONString(result));
        }else if(company.getDecisionChannel() == DecisionChannelEnum.PEI_QI_FEN.getId()){
           /* //佩琪分
            result = mxApi.peiQiFen(decisionParamsVO.getName(), decisionParamsVO.getPhone(), decisionParamsVO.getIdNumber(), company.getId(), userId);
            if(result.isSuccess()){
                Dict dict = result.getData();
                score = dict.getDouble("score");
                if(company.getForceRejectFlag() == CommonYesOrNoEnum.YES.getId() && "1".equals(dict.getStr("highRiskHit"))){
                    ret.set("force_reject", 1);
                }
            }
            logger.info("佩琪分最终返回phone:{}, ret：{}", decisionParamsVO.getPhone(), JSON.toJSONString(result));*/
        }
        if (!result.isSuccess()) {
            ret.set("score", 0);
            //添加消费记录
            consumeService.consume(company.getId(), company.getDecisionPrice(),
                    ReportTypeEnum.DECISION, ConsumeTypeEnum.CONSUME, "决策-" + decisionParamsVO.getPhone());
            addNotice(decisionParamsVO.getCallback(), JSON.toJSONString(ret));
            return new Result(true, ResultCodeEnum.SUCCESS.getId(), "请求成功", ret);
        }
        ret.set("score", score);
        //添加消费记录
        consumeService.consume(company.getId(), company.getDecisionPrice(),
                ReportTypeEnum.DECISION, ConsumeTypeEnum.CONSUME, "决策-" + decisionParamsVO.getPhone());
        addNotice(decisionParamsVO.getCallback(), JSON.toJSONString(ret));
        return new Result(true, ResultCodeEnum.SUCCESS.getId(), "请求成功", ret);
    }

    /**
     * 添加回调记录
     */
    private void addNotice(String callback, String result){
        NoticeRecord noticeRecord = new NoticeRecord();
        noticeRecord.setReportType(NoticeRecordTypeEnum.DECISION.getId());
        noticeRecord.setUrl(callback);
        noticeRecord.setResult(result);
        noticeRecord.setTimes(0);
        noticeRecord.setFlag(0);
        noticeRecord.setCreateTime(new Date());
        noticeRecordService.insertRecord(noticeRecord);
    }

    /**
     * 前置决策
     * @param decisionParamsVO
     * @param company
     * @return
     */
    private Result frontDecision(DecisionParamsVO decisionParamsVO, Company company) {
        //新增调用记录
        DecisionRecord decisionRecord = new DecisionRecord();
        decisionRecord.setCompanyId(company.getId());
        decisionRecord.setUserId(decisionParamsVO.getUserId());
        decisionRecord.setStatus(DecisionRecordStatusEnum.GENERATING.getId());
        decisionRecord.setName(decisionParamsVO.getName());
        decisionRecord.setIdNumber(decisionParamsVO.getIdNumber());
        decisionRecord.setPhone(decisionParamsVO.getPhone());
        decisionRecord.setType(decisionParamsVO.getType());
        decisionRecord.setSesameScore(decisionParamsVO.getSesameScore());
        if(CollUtil.isEmpty(decisionParamsVO.getPhoneList())){
            decisionParamsVO.setPhoneList(decisionParamsVO.getContactList());
        }
        //通讯录 上传oss
        String fileName = aliOssUtils.uploadReport(reportBucket, "decisionRecord/phone_list_"+decisionParamsVO.getUserId()+"_"+System.currentTimeMillis()+".txt",
                JSON.toJSONString(decisionParamsVO.getPhoneList()));
        decisionRecord.setPhoneList(fileName);
        decisionRecord.setMobileChannel(decisionParamsVO.getMobileChannel());
        decisionRecord.setExtend(decisionParamsVO.getExtend());
        decisionRecordService.add(decisionRecord);
        long decisionRecordId = decisionRecord.getId();

        //命中记录
        ConcurrentMap<String, List<String>> hitMap = new ConcurrentHashMap<>();
        //默认线程数
        int threadCount = 5;

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        //多投 反欺诈配置
        List<DecisionCreditConfigVO> decisionCreditConfigVO = creditConfigService.getCompanyConfig(company.getId(), decisionParamsVO.getType());
        Map<String, DecisionCreditConfigVO> configMap = new HashMap<>();
        for (DecisionCreditConfigVO config : decisionCreditConfigVO) {
            if (!configMap.containsKey(config.getCode())) {
                configMap.put(config.getCode(), config);
            }
        }

        //新建一个指定线程数的 线程池
        ExecutorService executorService = ThreadUtil.newExecutor(threadCount);
        //通讯录分析
        executorService.execute(() -> {
            logger.info("通讯录分析开始...{}", decisionParamsVO.getPhone());
            try {
                List<String> hitList = decisionTxl(decisionParamsVO.getFrontDecisionFlag(), decisionParamsVO.getPhoneList(), decisionParamsVO.getIdNumber(), configMap);
                if (CollectionUtils.isNotEmpty(hitList)) {
                    List<String> hitListTemp = hitMap.get(DecisionGroupEnum.INTERNAL.getCode());
                    if (CollectionUtils.isNotEmpty(hitListTemp)) {
                        hitList.addAll(hitListTemp);
                    }
                    hitMap.put(DecisionGroupEnum.INTERNAL.getCode(), hitList);
                }
            } finally {
                countDownLatch.countDown();
            }
            logger.info("通讯录分析结束...{}", decisionParamsVO.getPhone());
        });


        //芝麻分分析
        executorService.execute(() -> {
            logger.info("芝麻信用分析开始...{}", decisionParamsVO.getPhone());
            try {
                List<String> hitList = decisionZmxy(decisionParamsVO.getFrontDecisionFlag(), company.getId(), decisionParamsVO.getSesameScore(),
                        decisionParamsVO.getProvince());
                if (CollectionUtils.isNotEmpty(hitList)) {
                    List<String> hitListTemp = hitMap.get(DecisionGroupEnum.ZMXY.getCode());
                    if (CollectionUtils.isNotEmpty(hitListTemp)) {
                        hitList.addAll(hitListTemp);
                    }
                    hitMap.put(DecisionGroupEnum.ZMXY.getCode(), hitList);
                }
            } finally {
                countDownLatch.countDown();
            }
            logger.info("芝麻信用分析结束...{}", decisionParamsVO.getPhone());
        });

        //反欺诈分析
        executorService.execute(() -> {
            logger.info("反欺诈分析开始...{}", decisionParamsVO.getPhone());
            try {
                List<String> hitList = decisionUdCredit(configMap, decisionParamsVO.getUserId(), decisionParamsVO.getType(), decisionParamsVO.getFrontDecisionFlag());
                if (CollectionUtils.isNotEmpty(hitList)) {
                    List<String> hitListTemp = hitMap.get(DecisionGroupEnum.INTERNAL.getCode());
                    if (CollectionUtils.isNotEmpty(hitListTemp)) {
                        hitList.addAll(hitListTemp);
                    }
                    hitMap.put(DecisionGroupEnum.INTERNAL.getCode(), hitList);
                }
            } finally {
                countDownLatch.countDown();
            }
            logger.info("反欺诈分析结束...{}", decisionParamsVO.getPhone());
        });

        //多头
        executorService.execute(() -> {
            logger.info("多头分析开始...{}", decisionParamsVO.getPhone());
            try {
                List<String> hitList = decisionCredit(configMap, decisionParamsVO.getIdNumber(), decisionParamsVO.getUserId(), decisionParamsVO.getFrontDecisionFlag());
                if (CollectionUtils.isNotEmpty(hitList)) {
                    List<String> hitListTemp = hitMap.get(DecisionGroupEnum.INTERNAL.getCode());
                    if (CollectionUtils.isNotEmpty(hitListTemp)) {
                        hitList.addAll(hitListTemp);
                    }
                    hitMap.put(DecisionGroupEnum.INTERNAL.getCode(), hitList);
                }
            } finally {
                countDownLatch.countDown();
            }
            logger.info("多头分析结束...{}", decisionParamsVO.getPhone());
        });

        //运营商
        executorService.execute(() -> {
            logger.info("运营商分析开始...{}", decisionParamsVO.getPhone());
            try {
                List<String> hitList = decisionMobile(configMap, decisionParamsVO.getUserId(), decisionParamsVO.getPhoneList(), decisionParamsVO.getMobileChannel(), decisionParamsVO.getFrontDecisionFlag());
                if (CollectionUtils.isNotEmpty(hitList)) {
                    List<String> hitListTemp = hitMap.get(DecisionGroupEnum.YYS.getCode());
                    if (CollectionUtils.isNotEmpty(hitListTemp)) {
                        hitList.addAll(hitListTemp);
                    }
                    hitMap.put(DecisionGroupEnum.YYS.getCode(), hitList);
                }
            } finally {
                countDownLatch.countDown();
            }
            logger.info("运营商分析结束...{}", decisionParamsVO.getPhone());
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("决策报告获取异常,线程被中断", e);
        }
        executorService.shutdown();
        //添加命中记录
        List<HitRecord> hitList = new ArrayList<>();
        List<String> hitRules = new ArrayList<>();
        //内置
        if (hitMap.get(DecisionGroupEnum.INTERNAL.getCode()) != null) {
            for (String code : hitMap.get(DecisionGroupEnum.INTERNAL.getCode())) {
                HitRecord hitRecord = new HitRecord();
                hitRecord.setDecisionReportId(decisionRecordId);
                hitRecord.setCompanyId(company.getId());
                hitRecord.setGroup(DecisionGroupEnum.INTERNAL.getId());
                hitRecord.setCode(code);
                hitRecord.setUserId(decisionParamsVO.getUserId());
                hitRecord.setType(decisionParamsVO.getType());
                hitList.add(hitRecord);
            }
            hitRules.addAll(hitMap.get(DecisionGroupEnum.INTERNAL.getCode()));
        }

        //芝麻信用
        if (hitMap.get(DecisionGroupEnum.ZMXY.getCode()) != null) {
            for (String code : hitMap.get(DecisionGroupEnum.ZMXY.getCode())) {
                HitRecord hitRecord = new HitRecord();
                hitRecord.setDecisionReportId(decisionRecordId);
                hitRecord.setCompanyId(company.getId());
                hitRecord.setGroup(DecisionGroupEnum.ZMXY.getId());
                hitRecord.setCode(code);
                hitRecord.setUserId(decisionParamsVO.getUserId());
                hitRecord.setType(decisionParamsVO.getType());
                hitList.add(hitRecord);
            }
            hitRules.addAll(hitMap.get(DecisionGroupEnum.ZMXY.getCode()));
        }

        //运营商
        if (hitMap.get(DecisionGroupEnum.YYS.getCode()) != null) {
            for (String code : hitMap.get(DecisionGroupEnum.YYS.getCode())) {
                HitRecord hitRecord = new HitRecord();
                hitRecord.setDecisionReportId(decisionRecordId);
                hitRecord.setCompanyId(company.getId());
                hitRecord.setGroup(DecisionGroupEnum.YYS.getId());
                hitRecord.setCode(code);
                hitRecord.setUserId(decisionParamsVO.getUserId());
                hitRecord.setType(decisionParamsVO.getType());
                hitList.add(hitRecord);
            }
            hitRules.addAll(hitMap.get(DecisionGroupEnum.YYS.getCode()));
        }
        if (CollectionUtils.isNotEmpty(hitList)) {
            hitRecordService.batchAddRecord(hitList);
        }

        //修改决策记录
        Integer resultType = hitMap.size() > 0 ? DecisionRecordResultTypeEnum.REJECT.getId() : DecisionRecordResultTypeEnum.PASS.getId();
        DecisionReportVO report = new DecisionReportVO();
        report.setDecisionResult(resultType);
        report.setHitRules(hitRules);
        report.setAuto(false);
        decisionRecord = new DecisionRecord();
        decisionRecord.setId(decisionRecordId);
        decisionRecord.setStatus(DecisionRecordStatusEnum.GENERATING_SUCCESS.getId());
        decisionRecord.setResultType(resultType);
        decisionRecord.setResult(JSON.toJSONString(report));

        decisionRecordService.update(decisionRecord);
        logger.info("决策执行完毕-{}", decisionParamsVO.getPhone());
        return new Result(true, ResultCodeEnum.SUCCESS.getId(), "请求成功", report);
    }

    /**
     * 分析通讯录
     *
     * @param phoneList
     * @return
     */
    private List<String> decisionTxl(boolean frontDecisionFlag, List<DecisionParamsVO.Phone> phoneList, String idNumber, Map<String, DecisionCreditConfigVO> configMap) {
        Set<String> namesSet = new HashSet<>();
        //通讯录号码
        String mailsPhone;
        List<String> hitList = new ArrayList<>();
        int keyCount = 0;
        String[] txlKeyWords = null;
        if (configMap.get("TXL02") != null) {
            txlKeyWords = StringUtils.split(StringUtils.split(configMap.get("TXL02").getVal(), "-")[0], ",");
        }
        //通讯录涉及：警官  警察  警方 法官 这几个词
        String[] txlSensitive = null;
        int sensitiveCount = 0;
        if (configMap.get("TXL03") != null) {
            txlSensitive = StringUtils.split(configMap.get("TXL03").getVal(), ",");
        }
        if(CollUtil.isNotEmpty(phoneList)){
            for (DecisionParamsVO.Phone phone : phoneList) {
                mailsPhone = StringUtils.replace(phone.getPhone(), " ", "");
                mailsPhone = StringUtils.replace(mailsPhone, "+86", "");
                mailsPhone = StringUtils.replace(mailsPhone, "-", "");
                if (StringUtils.isBlank(phone.getName()) || StringUtils.isBlank(mailsPhone)) {
                    continue;
                }
                namesSet.add(phone.getName().concat("|").concat(mailsPhone));
                //校验通讯录命中敏感性词汇
                if (txlKeyWords != null && StringUtils.containsAny(phone.getName(), txlKeyWords)) {
                    keyCount++;
                }
                //匹配敏感词汇
                if (txlSensitive != null && StringUtils.containsAny(phone.getName(), txlSensitive)) {
                    sensitiveCount++;
                }
            }
        }
        //通讯录联系人数量低于设置值
        if (configMap.get("TXL01") != null && namesSet.size() <= NumberUtils.toInt(configMap.get("TXL01").getVal(), 0)) {
            hitList.add("TXL01");
        }
        //通讯录命中敏感性词汇大于设置值（借、贷相关个人或平台）
        if (txlKeyWords != null && keyCount >= NumberUtils.toInt(StringUtils.split(configMap.get("TXL02").getVal(), "-")[1])) {
            hitList.add("TXL02");
        }
        if(frontDecisionFlag){
            if (sensitiveCount > 0) {
                hitList.add("TXL03");
            }
        }
        return hitList;
    }

    /**
     * 分析芝麻分
     *
     * @param sesameScore
     * @return
     */
    private List<String> decisionZmxy(boolean frontDecisionFlag, long companyId, Integer sesameScore, String province) {
        if(!frontDecisionFlag){
            return null;
        }
        DecisonZmxyConfigVO decisonZmxyConfigVO = zmxyConfigService.getZmxyScore(companyId, province);
        List<String> hitList = new ArrayList<>();
        if (decisonZmxyConfigVO != null && sesameScore != null && sesameScore > 0 && sesameScore < decisonZmxyConfigVO.getScore()) {
            hitList.add(decisonZmxyConfigVO.getCode());
        }
        return hitList;
    }

    /**
     * 反欺诈报告分析
     *
     * @param configMap
     * @param userId
     * @return
     */
    private List<String> decisionUdCredit(Map<String, DecisionCreditConfigVO> configMap, long userId, int creditType, boolean frontDecisionFlag) {
        if(!frontDecisionFlag){
            return null;
        }
        DecisionUdReportVO decisionUdReportVO = udService.getLastUdDecisionData(userId);
        if (decisionUdReportVO == null) {
            return null;
        }
        if (new DateTime(decisionUdReportVO.getCreateTime()).plusDays(ConfigConstant.VALIDITY_PERIOD).isBeforeNow()) {
            return null;
        }
        List<String> hitList = new ArrayList<>();
        Integer riskScore = decisionUdReportVO.getRiskScore();
        //设备使用数
        List<Integer> deviceUseCountList = udService.getDeviceUseCount(decisionUdReportVO.getId());
        if (CollectionUtils.isNotEmpty(deviceUseCountList) && configMap.get("FQZ06") != null) {
            for (Integer count : deviceUseCountList) {
                if (count != null && count > NumberUtils.toInt(configMap.get("FQZ06").getVal(), 0)) {
                    hitList.add("FQZ06");
                    break;
                }
            }
        }
        if (riskScore == null) {
            return null;
        }
        if (riskScore == 0) {
            //未知评分
            if (configMap.get("FQZ01") == null) {
                return null;
            }
            String[] vals = configMap.get("FQZ01").getVal().split(",");
            if (decisionUdReportVO.getLoanPlatformCount() != null && decisionUdReportVO.getLoanPlatformCount() > NumberUtils.toInt(vals[1], 0)) {
                hitList.add("FQZ01");
            }
        } else if (riskScore > 0 && riskScore < 30) {
            //较低风险
            if (configMap.get("FQZ02") == null) {
                return null;
            }
            String[] vals = configMap.get("FQZ02").getVal().split(",");
            if (decisionUdReportVO.getActualLoanPlatformCount() != null && decisionUdReportVO.getLoanPlatformCount() != null
                    && decisionUdReportVO.getActualLoanPlatformCount() <= NumberUtils.toInt(vals[0], 0)
                    && decisionUdReportVO.getLoanPlatformCount() > NumberUtils.toInt(vals[1], 0)) {
                hitList.add("FQZ02");
            }
        } else if (riskScore >= 30 && riskScore < 55) {
            //中低风险
            if (configMap.get("FQZ03") == null) {
                return null;
            }
            String[] vals = configMap.get("FQZ03").getVal().split(",");
            if (decisionUdReportVO.getActualLoanPlatformCount() != null && decisionUdReportVO.getLoanPlatformCount() != null
                    && decisionUdReportVO.getActualLoanPlatformCount() <= NumberUtils.toInt(vals[0], 0)
                    && decisionUdReportVO.getLoanPlatformCount() > NumberUtils.toInt(vals[1], 0)) {
                hitList.add("FQZ03");
            }
        } else if (riskScore >= 55 && riskScore < 80) {
            //中高风险
            if (configMap.get("FQZ04") == null) {
                return null;
            }
            String[] vals = configMap.get("FQZ04").getVal().split(",");
            if (decisionUdReportVO.getActualLoanPlatformCount() != null && decisionUdReportVO.getLoanPlatformCount() != null
                    && decisionUdReportVO.getActualLoanPlatformCount() <= NumberUtils.toInt(vals[0], 0)
                    && decisionUdReportVO.getLoanPlatformCount() > NumberUtils.toInt(vals[1], 0)) {
                hitList.add("FQZ04");
            }
        } else if (riskScore >= 80 && creditType == CreditTypeEnum.FIRST_LEND.getId()) {
            //极高风险
            hitList.add("FQZ05");
        }
        //反欺诈近一个月实际借款数与近一个月申请借款数
        if (configMap.get("FQZ07") != null && decisionUdReportVO.getActualLoanPlatformCount1m() != null
                && decisionUdReportVO.getLoanPlatformCount1m() != null
                && decisionUdReportVO.getActualLoanPlatformCount1m() == NumberUtils.toInt(configMap.get("FQZ07").getVal().split(String.valueOf(CharUtil.COMMA))[0], 0)
                && decisionUdReportVO.getLoanPlatformCount1m() > NumberUtils.toInt(configMap.get("FQZ07").getVal().split(String.valueOf(CharUtil.COMMA))[1], 0)) {
            hitList.add("FQZ07");
        }
        //反欺诈近一个月申请借款数/近一个月实际借款数
        if (configMap.get("FQZ08") != null && decisionUdReportVO.getActualLoanPlatformCount1m() != null
                && decisionUdReportVO.getLoanPlatformCount1m() != null && decisionUdReportVO.getActualLoanPlatformCount1m() > 0
                && decisionUdReportVO.getLoanPlatformCount1m() / decisionUdReportVO.getActualLoanPlatformCount1m() > NumberUtils.toInt(configMap.get("FQZ08").getVal(), 0)) {
            hitList.add("FQZ08");
        }
        return hitList;
    }

    /**
     * 多头报告分析
     *
     * @param configMap
     * @param userId
     * @return
     */
    private List<String> decisionCredit(Map<String, DecisionCreditConfigVO> configMap, String idNumber, long userId, boolean frontDecisionFlag) {
        DecisionCreditReportVO decisionCreditReportVO = xyService.getLastXyDecisionData(userId);
        if (decisionCreditReportVO == null) {
            return null;
        }
        if (new DateTime(decisionCreditReportVO.getCreateTime()).plusDays(ConfigConstant.VALIDITY_PERIOD).isBeforeNow()) {
            return null;
        }
        //反欺诈报告
        DecisionUdReportVO decisionUdReportVO = udService.getLastUdDecisionData(userId);
        if (decisionUdReportVO != null && new DateTime(decisionUdReportVO.getCreateTime()).plusDays(ConfigConstant.VALIDITY_PERIOD).isBeforeNow()) {
            decisionUdReportVO = null;
        }
        List<String> hitList = new ArrayList<>();
        //年龄
        int age = IdcardUtil.getAgeByIdCard(idNumber);
        if (configMap.get("DT01") != null && age < NumberUtils.toInt(configMap.get("DT01").getVal(), 0)) {
            //最小年龄
            hitList.add("DT01");
        }
        /*if (configMap.get("DT02") != null && age > NumberUtils.toInt(configMap.get("DT02").getVal(), 0)) {
            //最大年龄
            hitList.add("DT02");
        }*/
        if(!frontDecisionFlag){
            return hitList;
        }
        //逾期订单数
        if (configMap.get("DT03") != null && decisionCreditReportVO.getLoansOverdueCount() != null && decisionCreditReportVO.getLoansOverdueCount() > NumberUtils.toInt(configMap.get("DT03").getVal(), 0)) {
            hitList.add("DT03");
        }
        //近1个月贷款机构失败扣款笔数
        if (configMap.get("DT04") != null && decisionCreditReportVO.getLatestOneMonthFail() != null && decisionCreditReportVO.getLatestOneMonthFail() > NumberUtils.toInt(configMap.get("DT04").getVal(), 0)) {
            hitList.add("DT04");
        }
        // 近1个月贷款机构失败扣款笔数/逾期订单数
        if (configMap.get("DT05") != null && decisionCreditReportVO.getLatestOneMonthFail() != null && decisionCreditReportVO.getLoansOverdueCount() > 0
                && decisionCreditReportVO.getLoansOverdueCount() != null
                && decisionCreditReportVO.getLatestOneMonthFail() / decisionCreditReportVO.getLoansOverdueCount() >= NumberUtils.toInt(configMap.get("DT05").getVal(), 0)) {
            hitList.add("DT05");
        }
        //历史成功扣款数-历史失败数
        if (configMap.get("DT06") != null && decisionCreditReportVO.getHistorySucFee() != null && decisionCreditReportVO.getHistoryFailFee() != null &&
                decisionCreditReportVO.getHistorySucFee() - decisionCreditReportVO.getHistoryFailFee() < NumberUtils.toInt(configMap.get("DT06").getVal(), 0)) {
            hitList.add("DT06");
        }
        //贷款行为分
        if (configMap.get("DT07") != null && decisionCreditReportVO.getLoansScore() != null
                && decisionCreditReportVO.getLoansScore() < NumberUtils.toInt(configMap.get("DT07").getVal(), 0)) {
            hitList.add("DT07");
        }
        //贷款置信度
        if (configMap.get("DT08") != null && decisionCreditReportVO.getLoansCredibility() != null
                && decisionCreditReportVO.getLoansCredibility() < NumberUtils.toInt(configMap.get("DT08").getVal(), 0)) {
            hitList.add("DT08");
        }
        //多头与反欺诈近一个月实际借款数
        if (configMap.get("DT09") != null && decisionUdReportVO != null
                && decisionCreditReportVO.getLatestOneMonth() != null && decisionUdReportVO.getActualLoanPlatformCount1m() != null
                && decisionCreditReportVO.getLatestOneMonth() <= NumberUtils.toInt(configMap.get("DT09").getVal(), 0)
                && decisionUdReportVO.getActualLoanPlatformCount1m() <= NumberUtils.toInt(configMap.get("DT09").getVal(), 0)) {
            hitList.add("DT09");
        }
        //近1个月失败扣款笔数-近1个月成功扣款笔数
        if (configMap.get("DT10") != null
                && decisionCreditReportVO.getLatestOneMonthFail() != null && decisionCreditReportVO.getLatestOneMonthSuc() != null
                && decisionCreditReportVO.getLatestOneMonthFail() - decisionCreditReportVO.getLatestOneMonthSuc() >= NumberUtils.toInt(configMap.get("DT10").getVal(), 0)) {
            hitList.add("DT10");
        }
        //贷款放款总订单数-贷款已结清订单数
        if (configMap.get("DT11") != null
                && decisionCreditReportVO.getLoansCount() != null && decisionCreditReportVO.getLoansSettleCount() != null
                && decisionCreditReportVO.getLoansCount() - decisionCreditReportVO.getLoansSettleCount() > NumberUtils.toInt(configMap.get("DT11").getVal(), 0)) {
            hitList.add("DT11");
        }
        return hitList;
    }

    /**
     * 运营商报告分析
     * @param configMap
     * @param userId
     * @return
     */
    private List<String> decisionMobile(Map<String, DecisionCreditConfigVO> configMap, long userId, List<DecisionParamsVO.Phone> phoneList, int mobileChannel, boolean frontDecisionFlag) {
        List<String> hitList = new ArrayList<>();
        MobileOrder mobileOrder = mobileService.queryLastByUserId(userId, mobileChannel);
        if (mobileOrder == null) {
            return null;
        }
        if (new DateTime(mobileOrder.getCreateTime()).plusDays(ConfigConstant.VALIDITY_PERIOD).isBeforeNow()) {
            return null;
        }
        //报告数据
        JSONObject reportDataObj = JSON.parseObject(aliOssUtils.readReport(reportBucket, mobileOrder.getReportResult()));
        //原始数据
        JSONObject originalDataObj = JSON.parseObject(aliOssUtils.readReport(reportBucket, mobileOrder.getDataResult()));
        if(MobileChannelEnum.XIN_YAN.getId() == mobileChannel){
            //新颜运营商
            reportDataObj = reportDataObj.getJSONObject("detail");
            originalDataObj = originalDataObj.getJSONObject("detail");
            if(reportDataObj.getJSONObject("friend_circle") != null && reportDataObj.getJSONObject("friend_circle").getJSONObject("risk_analysis") != null){
                JSONObject riskAnalysisObj = reportDataObj.getJSONObject("friend_circle").getJSONObject("risk_analysis");
                //特殊号码 通话分析
                JSONArray jsonArray = riskAnalysisObj.getJSONArray("risk_check_item");
                if(jsonArray != null){
                    for (int i = 0, j = jsonArray.size(); i < j; i++){
                        //与110有通话记录
                        if (configMap.get("A09") != null && !hitList.contains("A09")) {
                            if(StringUtils.equalsIgnoreCase("110", jsonArray.getJSONObject(i).getString("item"))
                                    && jsonArray.getJSONObject(i).getInteger("call_num_6m") > 0){
                                hitList.add("A09");
                            }
                        }
                    }
                }
                if(!frontDecisionFlag) {
                    return hitList;
                }
                // 申请人姓名+身份证号码是否出现在法院风险单
                if("是".equalsIgnoreCase(riskAnalysisObj.getString("is_name_and_idcard_in_court_black")) && !hitList.contains("A02")){
                    hitList.add("A02");
                }
                // 申请人姓名+身份证号码是否出现在金融机构风险名单 || 申请人姓名+手机号码是否出现在金融机构风险名单
                if(("是".equalsIgnoreCase(riskAnalysisObj.getString("is_name_and_idcard_in_finance_black"))
                        || "是".equalsIgnoreCase(riskAnalysisObj.getString("is_name_and_mobile_in_finance_black"))) && !hitList.contains("A01")) {
                    hitList.add("A01");
                }
            }

            // 原始数据不为空
            if(originalDataObj != null){
                //号码使用时长
                JSONObject jsonDataObject = originalDataObj.getJSONObject("data");
                if(jsonDataObject != null && jsonDataObject.getJSONObject("carrierUserInfo") != null){
                    if (configMap.get("A07") != null) {
                        String openTime = jsonDataObject.getJSONObject("carrierUserInfo").getString("openTime");
                        if (StringUtils.isBlank(openTime)) {
                            hitList.add("A07");
                        } else {
                            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                            int days = calculateDay(formatter.parseLocalDate(openTime).toDate(), new Date());
                            if (days < NumberUtils.toInt(configMap.get("A07").getVal(), 0)) {
                                hitList.add("A07");
                            }
                        }
                    }
                }
                // 通话记录
                if(jsonDataObject != null && jsonDataObject.getJSONArray("calls") != null){
                    JSONArray callsArray = jsonDataObject.getJSONArray("calls");
                    if(callsArray != null){
                        Set<String> beginTimeSet = new HashSet<>();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        for (int i = 0, j = callsArray.size(); i < j; i++) {
                            String time = callsArray.getJSONObject(i).getString("time");
                            if(StringUtils.isBlank(time)){
                                continue;
                            }
                            try {
                                Date date = format.parse(callsArray.getJSONObject(i).getString("time"));
                                beginTimeSet.add(String.valueOf(date.getTime()));
                            } catch (ParseException e) {
                                logger.error("运营商记录格式化日期异常");
                            }
                        }
                        String updateTime = originalDataObj.getString("updateTime");
                        DateTime reportBeginTime = new DateTime(new Date(Long.parseLong(updateTime)));
                        reportBeginTime = new DateTime(reportBeginTime.toString("yyyy-MM-dd"));
                        //没有通话记录天数
                        int noCallCount = 0;
                        //连续3天无通话记录天数
                        int noCallCountThree = 0;
                        int tempCount = 0;
                        long before = reportBeginTime.getMillis();
                        for (int i = 1; i <= 160; i++) {
                            if (i == 151 && tempCount > 3) {
                                noCallCountThree++;
                            }
                            if (!beginTimeSet.contains(String.valueOf(before))) {
                                noCallCount++;
                                tempCount++;
                            } else if (i <= 150) {
                                if (tempCount > 3) {
                                    noCallCountThree++;
                                    tempCount = 0;
                                } else if (tempCount > 0) {
                                    tempCount = 0;
                                }
                            }
                            before = reportBeginTime.minusDays(i).getMillis();
                        }
                        //160天内有50天以上无通话记录
                        if (!hitList.contains("A03") && configMap.get("A03") != null && noCallCount >= NumberUtils.toInt(configMap.get("A03").getVal(), 0)) {
                            hitList.add("A03");
                        }
                        //150天内连续3天无通话记录次数
                        if (!hitList.contains("A06") && configMap.get("A06") != null && noCallCountThree >= NumberUtils.toInt(configMap.get("A06").getVal(), 0)) {
                            hitList.add("A06");
                        }

                        //通讯录与通话记录中前20位匹配号码数
                        if (configMap.get("A11") != null && !hitList.contains("A11")) {
                            Map<String, Integer> callMap = new TreeMap<>();
                            for(int i=0,j=callsArray.size();i<j;i++){
                                String phoneNumber = callsArray.getJSONObject(i).getString("peerNumber");
                                if(callMap.containsKey(phoneNumber)){
                                    int count = callMap.get(phoneNumber)+1;
                                    callMap.put(phoneNumber, count);
                                }else{
                                    callMap.put(phoneNumber, 1);
                                }
                            }
                            //按照通话次数降序排序后的
                            List<Map.Entry<String, Integer>> callList = sortMapByValue(callMap);
                            List<String> sortPhoneList = new ArrayList<>();
                            for (Map.Entry<String, Integer> entry : callList) {
                                sortPhoneList.add(entry.getKey());
                            }

                            //去重后的通讯录号码数量
                            Set<String> phoneSet = new HashSet<>();
                            for (DecisionParamsVO.Phone phone : phoneList) {
                                String mailsPhone = phone.getPhone();
                                mailsPhone = StringUtils.replace(mailsPhone, " ", "");
                                mailsPhone = StringUtils.replace(mailsPhone, "+86", "");
                                mailsPhone = StringUtils.replace(mailsPhone, "-", "");
                                phoneSet.add(mailsPhone);
                            }
                            int size = sortPhoneList.size() < 20 ? sortPhoneList.size() : 20;
                            int count = 0;
                            for (int i = 0; i < size; i++) {
                                if (phoneSet.contains(sortPhoneList.get(i))) {
                                    count++;
                                }
                            }
                            if (count < NumberUtils.toInt(configMap.get("A11").getVal(), 0)) {
                                hitList.add("A11");
                            }
                        }
                    }
                }

                //近5个月缴费记录
                if(jsonDataObject != null && jsonDataObject.getJSONArray("recharges") != null) {
                    JSONArray rechargesArray = jsonDataObject.getJSONArray("recharges");
                    if (configMap.get("A10") != null && !hitList.contains("A10") && rechargesArray != null) {
                        int size = rechargesArray.size() < 5 ? rechargesArray.size() : 5;
                        int count = 0;
                        for (int i = 0; i < size; i++) {
                            String rechargeMoney = rechargesArray.getJSONObject(i).getString("amount");
                            if (NumberUtils.toFloat(rechargeMoney) < 30) {
                                count++;
                            }
                        }
                        if (count == size) {
                            hitList.add("A10");
                        }
                    }
                }
            }
        }else if(MobileChannelEnum.MO_XIE.getId() == mobileChannel){
            //魔蝎运营商
            //特殊号码 通话分析
            JSONArray behaviorArray = reportDataObj.getJSONArray("behavior_check");
            if(behaviorArray != null && behaviorArray.size() > 0){
                for(int i=0,j=behaviorArray.size();i<j;i++){
                    JSONObject item = behaviorArray.getJSONObject(i);
                    //与法院通话记录
                    if(configMap.get("A08") != null && !hitList.contains("A08") && StrUtil.equalsIgnoreCase("contact_court", item.getString("check_point"))
                            && (StrUtil.equals("多次通话", item.getString("result")) || StrUtil.equals("偶尔通话", item.getString("result")))){
                        hitList.add("A08");
                    }
                    //与110有通话记录
                    if(configMap.get("A09") != null && !hitList.contains("A09") && StrUtil.equalsIgnoreCase("contact_110", item.getString("check_point"))
                            && (StrUtil.equals("多次通话", item.getString("result")) || StrUtil.equals("偶尔通话", item.getString("result")))){
                        hitList.add("A09");
                    }
                }
            }
            if(!frontDecisionFlag){
                return hitList;
            }
            //金融机构 法院黑名单
            JSONArray basicCheckItemsArray = reportDataObj.getJSONArray("basic_check_items");
            if(basicCheckItemsArray != null && basicCheckItemsArray.size() > 0){
                for(int i=0,j=basicCheckItemsArray.size();i<j;i++){
                    JSONObject item = basicCheckItemsArray.getJSONObject(i);
                    if(((StrUtil.equalsIgnoreCase(item.getString("check_item"), "is_name_and_mobile_in_finance_black")
                            && StrUtil.equals("是", item.getString("result")))
                            || (StrUtil.equalsIgnoreCase(item.getString("check_item"), "is_name_and_idcard_in_finance_black")
                            && StrUtil.equals("是", item.getString("result"))))
                                    && !hitList.contains("A01")){
                        //金融机构黑名单
                        hitList.add("A02");
                    }
                    else if(StrUtil.equalsIgnoreCase(item.getString("check_item"), "is_name_and_idcard_in_court_black")
                            && StrUtil.equals("是", item.getString("result")) && !hitList.contains("A01")){
                        //法院黑名单
                        hitList.add("A01");
                    }
                }
            }
            //反欺诈云
            JSONArray userInfoArray = reportDataObj.getJSONArray("user_info_check");
            if(userInfoArray != null && userInfoArray.size() > 0){
                for(int i=0,j=userInfoArray.size();i<j;i++){
                    JSONObject searchInfo = userInfoArray.getJSONObject(i).getJSONObject("check_search_info");
                    if(searchInfo != null){
                        //手机号关联身份证个数
                        if(!hitList.contains("A04") && configMap.get("A04") != null && searchInfo.getJSONArray("phone_with_other_idcards") != null
                                && searchInfo.getJSONArray("phone_with_other_idcards").size() > NumberUtils.toInt(configMap.get("A04").getVal(), 0)){
                            hitList.add("A04");
                        }
                        //身份证关联手机号个数
                        if(!hitList.contains("A05") && configMap.get("A05") != null && searchInfo.getJSONArray("idcard_with_other_phones") != null
                                && searchInfo.getJSONArray("idcard_with_other_phones").size() > NumberUtils.toInt(configMap.get("A05").getVal(), 0)){
                            hitList.add("A05");
                        }
                    }
                }
            }
            //号码使用时长
            if(configMap.get("A07") != null){
                String openTime = originalDataObj.getString("open_time");
                if(StrUtil.isNotBlank(openTime)){
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                    int days = calculateDay(formatter.parseLocalDate(openTime).toDate(), new Date());
                    if(!hitList.contains("A07") && days < NumberUtils.toInt(configMap.get("A07").getVal(), 0)){
                        hitList.add("A07");
                    }
                }
            }

            //通话记录
            /*if(originalDataObj != null && originalDataObj.getJSONArray("calls") != null){
                JSONArray callsArray = originalDataObj.getJSONArray("calls");
                if(callsArray != null && callsArray.size() > 0){
                    Set<String> beginTimeSet = new HashSet<>();
                    for (int i = 0, j = callsArray.size(); i < j; i++) {
                        JSONArray itemArray = callsArray.getJSONObject(i).getJSONArray("items");
                        if(itemArray == null || itemArray.size() == 0){
                            continue;
                        }
                        for(int k=0,l=itemArray.size();k<l;k++){
                            JSONObject item = itemArray.getJSONObject(k);
                            String time = item.getString("time");
                            if(StringUtils.isBlank(time)){
                                continue;
                            }
                            beginTimeSet.add(String.valueOf(DateUtil.parse(time, "yyyy-MM-dd").getTime()));
                        }
                    }
                    String updateTime = originalDataObj.getString("last_modify_time");
                    cn.hutool.core.date.DateTime reportBeginTime = DateUtil.parse(updateTime, "yyyy-MM-dd");
                    //没有通话记录天数
                    int noCallCount = 0;
                    //连续3天无通话记录天数
                    int noCallCountThree = 0;
                    int tempCount = 0;
                    long before = reportBeginTime.getTime();
                    for (int i = 1; i <= 160; i++) {
                        if (i == 151 && tempCount > 3) {
                            noCallCountThree++;
                        }
                        if (!beginTimeSet.contains(String.valueOf(before))) {
                            noCallCount++;
                            tempCount++;
                        } else if (i <= 150) {
                            if (tempCount > 3) {
                                noCallCountThree++;
                                tempCount = 0;
                            } else if (tempCount > 0) {
                                tempCount = 0;
                            }
                        }
                        before = DateUtil.offsetDay(reportBeginTime, -1*i).getTime();
                    }
                    //160天内有50天以上无通话记录
                    if (configMap.get("A03") != null && noCallCount >= NumberUtils.toInt(configMap.get("A03").getVal(), 0)) {
                        hitList.add("A03");
                    }
                    //150天内连续3天无通话记录次数
                    if (configMap.get("A06") != null && noCallCountThree >= NumberUtils.toInt(configMap.get("A06").getVal(), 0)) {
                        hitList.add("A06");
                    }

                    //通讯录与通话记录中前20位匹配号码数
                    if (configMap.get("A11") != null) {
                        Map<String, Integer> callMap = new TreeMap<>();
                        for (int i = 0, j = callsArray.size(); i < j; i++) {
                            JSONArray itemArray = callsArray.getJSONObject(i).getJSONArray("items");
                            if(itemArray == null || itemArray.size() == 0){
                                continue;
                            }
                            for(int k=0,l=itemArray.size();k<l;k++){
                                JSONObject item = itemArray.getJSONObject(k);
                                String phoneNumber = item.getString("peer_number");
                                if(StringUtils.isBlank(phoneNumber)){
                                    continue;
                                }
                                if(callMap.containsKey(phoneNumber)){
                                    int count = callMap.get(phoneNumber)+1;
                                    callMap.put(phoneNumber, count);
                                }else{
                                    callMap.put(phoneNumber, 1);
                                }
                            }
                        }
                        //按照通话次数降序排序后的
                        List<Map.Entry<String, Integer>> callList = sortMapByValue(callMap);
                        List<String> sortPhoneList = new ArrayList<>();
                        for (Map.Entry<String, Integer> entry : callList) {
                            sortPhoneList.add(entry.getKey());
                        }
                        //去重后的通讯录号码数量
                        Set<String> phoneSet = new HashSet<>();
                        for (DecisionParamsVO.Phone phone : phoneList) {
                            String mailsPhone = phone.getPhone();
                            mailsPhone = StringUtils.replace(mailsPhone, " ", "");
                            mailsPhone = StringUtils.replace(mailsPhone, "+86", "");
                            mailsPhone = StringUtils.replace(mailsPhone, "-", "");
                            phoneSet.add(mailsPhone);
                        }
                        int size = sortPhoneList.size() < 20 ? sortPhoneList.size() : 20;
                        int count = 0;
                        for (int i = 0; i < size; i++) {
                            if (phoneSet.contains(sortPhoneList.get(i))) {
                                count++;
                            }
                        }
                        if (count < NumberUtils.toInt(configMap.get("A11").getVal(), 0)) {
                            hitList.add("A11");
                        }
                    }
                }
            }*/
        }
        return hitList;
    }


    private List<Map.Entry<String, Integer>> sortMapByValue(Map<String, Integer> map) {
        // 升序比较器
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        };
        // map转换成list进行排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        // 排序
        Collections.sort(list,valueComparator);
        return list;
    }

    /**
     * 计算两个日期相差天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private int calculateDay(Date startTime, Date endTime) {
        DateTime dateTime = new DateTime(startTime);
        DateTime nowTime = new DateTime(endTime);
        String dataTimeStr = dateTime.toString("yyyy-MM-dd");
        String nowTimeStr = nowTime.toString("yyyy-MM-dd");
        dateTime = new DateTime(dataTimeStr);
        nowTime = new DateTime(nowTimeStr);
        return Days.daysBetween(dateTime, nowTime).getDays();
    }
}