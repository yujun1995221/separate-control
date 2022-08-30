package com.free.decision.server.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.apis.MxApi;
import com.free.decision.server.apis.XyApi;
import com.free.decision.server.enums.*;
import com.free.decision.server.mapper.MobileOrderMapper;
import com.free.decision.server.model.*;
import com.free.decision.server.model.vo.MobileParamsVO;
import com.free.decision.server.service.*;
import com.free.decision.server.utils.AliOssUtils;
import com.free.decision.server.utils.HttpKit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 运营商
 */
@Service
public class MobileServiceImpl implements MobileService {

    private static Logger logger = LoggerFactory.getLogger(MobileServiceImpl.class);

    @Resource
    private MobileOrderMapper mobileOrderMapper;

    @Resource
    private UserService userService;

    @Resource
    private AliOssUtils aliOssUtils;

    @Resource
    private NoticeRecordService noticeRecordService;

    @Resource
    private ConsumeService consumeService;

    @Resource
    private CompanyService companyService;

    @Resource
    private XyApi xyApi;

    @Resource
    private MxApi mxApi;

    @Value("${oss.report.bucket}")
    private String reportBucket;

    @Value("${decision.domain}")
    private String domain;

    @Value("${decision.xy.apiUser}")
    private String xyApiUser;

    @Value("${decision.xy.apiKey}")
    private String xyApiKey;

    @Value("${mx.apikey}")
    private String mxApiKey;

    @Override
    public Result createOrder(MobileParamsVO mobileParamsVO, Company company) {
        long userId = userService.getUserIdByMobileAndIdNumber(company.getId(), mobileParamsVO.getPhone(), mobileParamsVO.getIdNumber(), mobileParamsVO.getName());
        Map<String, Object> dataMap = new HashMap<>();
        //判断15天内是否认证过
        MobileOrder lastMobileOrder = mobileOrderMapper.queryLastNoCacheByPhone(mobileParamsVO.getPhone(), mobileParamsVO.getChannel());
        boolean cache = false;
        if(lastMobileOrder != null){
            DateTime createTime = new DateTime(lastMobileOrder.getCreateTime());
            if(createTime.plusDays(15).isAfterNow()){
                cache = true;
            }
        }
        long timestamp = System.currentTimeMillis();
        if(cache){
            logger.info("运营商认证有缓存,phone:{}", mobileParamsVO.getPhone());
            MobileOrder mobileOrder = new MobileOrder();
            mobileOrder.setCompanyId(company.getId());
            mobileOrder.setUserId(userId);
            mobileOrder.setPhone(mobileParamsVO.getPhone());
            mobileOrder.setIdNumber(mobileParamsVO.getIdNumber());
            mobileOrder.setChannel(lastMobileOrder.getChannel());
            mobileOrder.setOrderNo(lastMobileOrder.getOrderNo());
            mobileOrder.setPhoneList(lastMobileOrder.getPhoneList());
            mobileOrder.setEmergContacts(lastMobileOrder.getEmergContacts());
            mobileOrder.setReportStatus(lastMobileOrder.getReportStatus());
            mobileOrder.setDataStatus(lastMobileOrder.getDataStatus());
            mobileOrder.setReportResult(lastMobileOrder.getReportResult());
            mobileOrder.setDataResult(lastMobileOrder.getDataResult());
            mobileOrder.setCacheFlag(CommonYesOrNoEnum.YES.getId());
            mobileOrder.setCallback(mobileParamsVO.getCallback());
            mobileOrder.setCreateTime(new Date());
            mobileOrderMapper.insert(mobileOrder);
        }else{
            String orderNo = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + RandomStringUtils.randomNumeric(10);
            MobileOrder mobileOrder = new MobileOrder();
            mobileOrder.setCompanyId(company.getId());
            mobileOrder.setUserId(userId);
            mobileOrder.setPhone(mobileParamsVO.getPhone());
            mobileOrder.setIdNumber(mobileParamsVO.getIdNumber());
            mobileOrder.setOrderNo(orderNo);
            mobileOrder.setDataStatus(MobileOrderStatusEnum.UN_KNOW.getId());
            mobileOrder.setReportStatus(MobileOrderStatusEnum.UN_KNOW.getId());
            mobileOrder.setCacheFlag(CommonYesOrNoEnum.NO.getId());
            mobileOrder.setCreateTime(new Date());
            mobileOrder.setCallback(mobileParamsVO.getCallback());
            if(mobileParamsVO.getChannel() == MobileChannelEnum.XIN_YAN.getId()){
                //新颜
                //通讯录
                Map<String, String> contacts = new HashMap<>();
                if(CollectionUtils.isNotEmpty(mobileParamsVO.getContactList())){
                    for(MobileParamsVO.Contact contact : mobileParamsVO.getContactList()){
                        if(StringUtils.isBlank(contact.getName()) || StringUtils.isBlank(contact.getPhone())){
                            continue;
                        }
                        contacts.put(contact.getPhone(), contact.getName());
                    }
                    String fileName = "report/mobile/phonelist_"+userId+"_"+DateFormatUtils.format(timestamp, "yyyyMMddHHmmssS")+".txt";
                    aliOssUtils.uploadReport(reportBucket, fileName, JSON.toJSONString(contacts));
                    mobileOrder.setPhoneList(fileName);
                }
                //紧急联系人
                Map<String, String> emergContact = new HashMap<>();
                if(CollectionUtils.isNotEmpty(mobileParamsVO.getEmergContactList())){
                    for(MobileParamsVO.Contact contact : mobileParamsVO.getEmergContactList()){
                        if(StringUtils.isBlank(contact.getName()) || StringUtils.isBlank(contact.getPhone())){
                            continue;
                        }
                        emergContact.put(contact.getPhone(), contact.getName());
                    }
                    mobileOrder.setEmergContacts(JSON.toJSONString(emergContact));
                }
                mobileOrder.setChannel(MobileChannelEnum.XIN_YAN.getId());
                dataMap.put("dataCallback", domain+"/mobile/callback/xinyan");
            }else if(mobileParamsVO.getChannel() == MobileChannelEnum.MO_XIE.getId()){
                //魔蝎
                //通讯录
                if(CollectionUtils.isNotEmpty(mobileParamsVO.getContactList())){
                    List<Map<String, String>> dataList = new ArrayList<>();
                    for(MobileParamsVO.Contact contact : mobileParamsVO.getContactList()){
                        if(StringUtils.isBlank(contact.getName()) || StringUtils.isBlank(contact.getPhone())){
                            continue;
                        }
                        Map<String, String> contacts = new HashMap<>();
                        contacts.put("phone", contact.getPhone());
                        contacts.put("name", contact.getName());
                        contacts.put("remark", "");
                        dataList.add(contacts);
                    }
                    String txl = JSON.toJSONString(Dict.create().set("data", dataList));
                    String fileName = "report/mobile/phonelist_"+userId+"_"+DateFormatUtils.format(timestamp, "yyyyMMddHHmmssS")+".txt";
                    aliOssUtils.uploadReport(reportBucket, fileName, txl);
                    mobileOrder.setPhoneList(fileName);
                    //紧急联系人
                    String emergContact = "";
                    //1800000000:李四:朋友,17000000000::
                    if(CollectionUtils.isNotEmpty(mobileParamsVO.getEmergContactList())){
                        for(MobileParamsVO.Contact contact : mobileParamsVO.getEmergContactList()){
                            if(StringUtils.isBlank(contact.getName()) || StringUtils.isBlank(contact.getPhone())){
                                continue;
                            }
                            emergContact += contact.getPhone()+":"+contact.getName()+":,";
                        }
                        emergContact = StrUtil.removeSuffix(emergContact, ",");
                        mobileOrder.setEmergContacts(emergContact);
                    }
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "apikey "+mxApiKey);
                    headers.put("Content-Type", "application/json");
                    HttpKit.post(StrUtil.format("https://api.51datakey.com/carrier/v1/mobile/{}/contacts", mobileParamsVO.getPhone()), txl, headers);
                }
                mobileOrder.setChannel(MobileChannelEnum.MO_XIE.getId());
            }
            mobileOrderMapper.insert(mobileOrder);
            dataMap.put("taskId", orderNo);
        }
        dataMap.put("cache", cache);
        dataMap.put("idNumber", mobileParamsVO.getIdNumber());
        return new Result(true, "运营商认证请求成功", dataMap);
    }

    @Override
    public Result cacheData(MobileParamsVO mobileParamsVO, Company company) {
        Long userId = userService.loadUserIdByMobileAndIdNumber(company.getId(), mobileParamsVO.getPhone(), mobileParamsVO.getIdNumber());
        if (userId == null) {
            return new Result(false, "认证失败-"+mobileParamsVO.getPhone());
        }
        long timestamp = System.currentTimeMillis();
        MobileOrder lastMobileOrder = mobileOrderMapper.queryLastByUserId(userId, mobileParamsVO.getChannel());
        if(lastMobileOrder != null){
            DateTime createTime = new DateTime(lastMobileOrder.getCreateTime());
            if(createTime.plusDays(15).isBeforeNow()){
                logger.warn("运营商认证缓存条件不符合,时间不对,phone:{}", mobileParamsVO.getPhone());
                return new Result(false, "认证失败,条件不符合-"+mobileParamsVO.getPhone());
            }
            if(lastMobileOrder.getChannel() == MobileChannelEnum.XIN_YAN.getId()){
                //插入原始数据
                String data = aliOssUtils.readReport(reportBucket, lastMobileOrder.getDataResult());
                JSONObject jsonObj = JSON.parseObject(data);
                jsonObj = jsonObj.getJSONObject("detail");
                jsonObj.put("reportParams", "apiUser=" + xyApiUser + "&apiEnc=" + SecureUtil.md5(xyApiUser + xyApiKey).toUpperCase() + "&token=" + lastMobileOrder.getDataToken());
                jsonObj.remove("id");
                jsonObj.remove("token");
                jsonObj.remove("update_time");
                jsonObj.put("store_time", DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                //data：原始数据 report：报告数据
                jsonObj.put("data_type", "data");
                jsonObj.put("data_channel", MobileChannelEnum.XIN_YAN.getId());
                jsonObj.put("phone", lastMobileOrder.getPhone());
                jsonObj.put("timestamp", timestamp);
                jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                String fileName = "callback/mobile/xy_mobile_original_" + userId + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                NoticeRecord noticeRecord = new NoticeRecord();
                noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                noticeRecord.setUrl(lastMobileOrder.getCallback());
                noticeRecord.setResult(fileName);
                noticeRecord.setTimes(0);
                noticeRecord.setFlag(0);
                noticeRecord.setCreateTime(new Date());
                noticeRecordService.insertRecord(noticeRecord);
                //插入报告数据
                String reportData = aliOssUtils.readReport(reportBucket, lastMobileOrder.getReportResult());
                jsonObj = JSON.parseObject(reportData);
                jsonObj = jsonObj.getJSONObject("detail");
                jsonObj.put("reportParams", "apiUser=" + xyApiUser + "&apiEnc=" + SecureUtil.md5(xyApiUser + xyApiKey).toUpperCase() + "&token=" + lastMobileOrder.getDataToken());
                jsonObj.remove("id");
                jsonObj.remove("token");
                jsonObj.remove("update_time");
                jsonObj.put("store_time", DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                //data：原始数据 report：报告数据
                jsonObj.put("data_type", "report");
                jsonObj.put("data_channel", MobileChannelEnum.XIN_YAN.getId());
                jsonObj.put("phone", mobileParamsVO.getPhone());
                jsonObj.put("timestamp", timestamp);
                jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                fileName = "callback/mobile/xy_mobile_report_" + userId + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                noticeRecord = new NoticeRecord();
                noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                noticeRecord.setUrl(lastMobileOrder.getCallback());
                noticeRecord.setResult(fileName);
                noticeRecord.setTimes(0);
                noticeRecord.setFlag(0);
                noticeRecord.setCreateTime(new Date());
                noticeRecordService.insertRecord(noticeRecord);
                //插入消费记录
                consumeService.consume(company.getId(), company.getMobilePrice(), ReportTypeEnum.MOBILE, ConsumeTypeEnum.CONSUME, "运营商认证-" + mobileParamsVO.getPhone());
                return new Result(true, "运营商认证成功");
            }
            else if(lastMobileOrder.getChannel() == MobileChannelEnum.MO_XIE.getId()){
                //插入原始数据
                String data = aliOssUtils.readReport(reportBucket, lastMobileOrder.getDataResult());
                JSONObject jsonObj = JSON.parseObject(data);
                jsonObj.put("store_time", DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                //data：原始数据 report：报告数据
                jsonObj.put("data_type", "data");
                jsonObj.put("data_channel", MobileChannelEnum.MO_XIE.getId());
                jsonObj.put("phone", lastMobileOrder.getPhone());
                jsonObj.put("timestamp", timestamp);
                jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                String fileName = "callback/mobile/mx_mobile_original_" + userId + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                NoticeRecord noticeRecord = new NoticeRecord();
                noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                noticeRecord.setUrl(lastMobileOrder.getCallback());
                noticeRecord.setResult(fileName);
                noticeRecord.setTimes(0);
                noticeRecord.setFlag(0);
                noticeRecord.setCreateTime(new Date());
                noticeRecordService.insertRecord(noticeRecord);
                //插入报告数据
                String reportData = aliOssUtils.readReport(reportBucket, lastMobileOrder.getReportResult());
                jsonObj = JSON.parseObject(reportData);
                jsonObj.put("store_time", DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                //data：原始数据 report：报告数据
                jsonObj.put("data_type", "report");
                jsonObj.put("data_channel", MobileChannelEnum.MO_XIE.getId());
                jsonObj.put("phone", mobileParamsVO.getPhone());
                jsonObj.put("timestamp", timestamp);
                jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                fileName = "callback/mobile/mx_mobile_report_" + userId + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                noticeRecord = new NoticeRecord();
                noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                noticeRecord.setUrl(lastMobileOrder.getCallback());
                noticeRecord.setResult(fileName);
                noticeRecord.setTimes(0);
                noticeRecord.setFlag(0);
                noticeRecord.setCreateTime(new Date());
                noticeRecordService.insertRecord(noticeRecord);
                //插入消费记录
                consumeService.consume(company.getId(), company.getMobilePrice(), ReportTypeEnum.MOBILE, ConsumeTypeEnum.CONSUME, "运营商认证-" + mobileParamsVO.getPhone());
                return new Result(true, "运营商认证成功");
            }
        }
        logger.warn("运营商认证缓存条件不符合,phone:{}", mobileParamsVO.getPhone());
        return new Result(false, "认证失败,条件不符合-"+mobileParamsVO.getPhone());
    }

    @Override
    public void queryData() {
        List<MobileOrder> mobileOrderList = mobileOrderMapper.getDoingOrder();
        if (CollectionUtils.isNotEmpty(mobileOrderList)) {
            //每页条数
            int pageSize = 50;
            //线程数
            int threadCount = (mobileOrderList.size() % pageSize) != 0 ? (mobileOrderList.size() / pageSize) + 1 : mobileOrderList.size() / pageSize;
            // 每页一个新线程
            CountDownLatch countDownLatch = new CountDownLatch(threadCount);
            //新建一个指定线程数的 线程池
            ExecutorService executorService = ThreadUtil.newExecutor(threadCount);
            for (int i = 0; i < threadCount; i++) {
                int start = i * pageSize;
                int end = (i + 1) * pageSize - 1;
                //通讯录分析
                executorService.execute(() -> {
                    logger.info("多线程，查询运营商报告开始...{}", threadCount);
                    try {
                        for (int j = start; j <= end; j++) {
                            if (j > mobileOrderList.size() - 1) {
                                break;
                            }
                            MobileOrder mobileOrder = mobileOrderList.get(j);
                            Company company = companyService.loadById(mobileOrder.getCompanyId());
                            long timestamp = System.currentTimeMillis();
                            if (mobileOrder.getChannel() == MobileChannelEnum.XIN_YAN.getId()) {
                                //新颜
                                if (mobileOrder.getDataStatus() == MobileOrderStatusEnum.DOING.getId()) {
                                    mobileOrderMapper.updateTimes(1, mobileOrder.getId());
                                    String data = xyApi.getData(mobileOrder.getDataToken(), 2, mobileOrder.getOrderNo());
                                    if(StringUtils.isBlank(data)){
                                        continue;
                                    }
                                    JSONObject jsonObj = JSON.parseObject(data);
                                    jsonObj.put("reportParams", "apiUser=" + xyApiUser + "&apiEnc=" + SecureUtil.md5(xyApiUser + xyApiKey).toUpperCase() + "&token=" + mobileOrder.getDataToken());
                                    jsonObj.remove("id");
                                    jsonObj.remove("token");
                                    jsonObj.put("store_time", DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                                    //data：原始数据 report：报告数据
                                    jsonObj.put("data_type", "data");
                                    jsonObj.put("data_channel", MobileChannelEnum.XIN_YAN.getId());
                                    jsonObj.put("phone", mobileOrder.getPhone());
                                    jsonObj.put("timestamp", timestamp);
                                    jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                                    String fileName = "callback/mobile/xy_mobile_original_" + mobileOrder.getUserId() + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                                    aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                                    NoticeRecord noticeRecord = new NoticeRecord();
                                    noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                                    noticeRecord.setUrl(mobileOrder.getCallback());
                                    noticeRecord.setResult(fileName);
                                    noticeRecord.setTimes(0);
                                    noticeRecord.setFlag(0);
                                    noticeRecord.setCreateTime(new Date());
                                    noticeRecordService.insertRecord(noticeRecord);
                                    //插入消费记录
                                    consumeService.consume(company.getId(), company.getMobilePrice(), ReportTypeEnum.MOBILE, ConsumeTypeEnum.CONSUME, "运营商认证-" + mobileOrder.getPhone());
                                } else if (mobileOrder.getReportStatus() == MobileOrderStatusEnum.DOING.getId()) {
                                    String data = xyApi.getReportData(mobileOrder.getReportToken(), 2, mobileOrder.getOrderNo());
                                    mobileOrderMapper.updateTimes(2, mobileOrder.getId());
                                    if(StringUtils.isBlank(data)){
                                        continue;
                                    }
                                    JSONObject jsonObj = JSON.parseObject(data);
                                    jsonObj.put("reportParams", "apiUser=" + xyApiUser + "&apiEnc=" + SecureUtil.md5(xyApiUser + xyApiKey).toUpperCase() + "&token=" + mobileOrder.getDataToken());
                                    jsonObj.remove("id");
                                    jsonObj.remove("token");
                                    jsonObj.put("store_time", DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                                    //data：原始数据 report：报告数据
                                    jsonObj.put("data_type", "report");
                                    jsonObj.put("data_channel", MobileChannelEnum.XIN_YAN.getId());
                                    jsonObj.put("phone", mobileOrder.getPhone());
                                    jsonObj.put("timestamp", timestamp);
                                    jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                                    String fileName = "callback/mobile/xy_mobile_report_" + mobileOrder.getUserId() + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                                    aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                                    NoticeRecord noticeRecord = new NoticeRecord();
                                    noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                                    noticeRecord.setUrl(mobileOrder.getCallback());
                                    noticeRecord.setResult(fileName);
                                    noticeRecord.setTimes(0);
                                    noticeRecord.setFlag(0);
                                    noticeRecord.setCreateTime(new Date());
                                    noticeRecordService.insertRecord(noticeRecord);
                                }
                            }else if(mobileOrder.getChannel() == MobileChannelEnum.MO_XIE.getId()){
                                //魔蝎
                                if (mobileOrder.getDataStatus() == MobileOrderStatusEnum.DOING.getId()) {
                                    mobileOrderMapper.updateTimes(1, mobileOrder.getId());
                                    String data = mxApi.getData(mobileOrder.getDataToken(), 2, mobileOrder.getOrderNo(), mobileOrder.getPhone());
                                    if(StringUtils.isBlank(data)){
                                        continue;
                                    }
                                    JSONObject jsonObj = JSON.parseObject(data);
                                    jsonObj.put("store_time", DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                                    //data：原始数据 report：报告数据
                                    jsonObj.put("data_type", "data");
                                    jsonObj.put("data_channel", MobileChannelEnum.MO_XIE.getId());
                                    jsonObj.put("phone", mobileOrder.getPhone());
                                    jsonObj.put("timestamp", timestamp);
                                    jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                                    String fileName = "callback/mobile/mx_mobile_original_" + mobileOrder.getUserId() + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                                    aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                                    NoticeRecord noticeRecord = new NoticeRecord();
                                    noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                                    noticeRecord.setUrl(mobileOrder.getCallback());
                                    noticeRecord.setResult(fileName);
                                    noticeRecord.setTimes(0);
                                    noticeRecord.setFlag(0);
                                    noticeRecord.setCreateTime(new Date());
                                    noticeRecordService.insertRecord(noticeRecord);
                                    //插入消费记录
                                    consumeService.consume(company.getId(), company.getMobilePrice(), ReportTypeEnum.MOBILE, ConsumeTypeEnum.CONSUME, "运营商认证-" + mobileOrder.getPhone());
                                } else if (mobileOrder.getReportStatus() == MobileOrderStatusEnum.DOING.getId()) {
                                    String data = mxApi.getReportData(mobileOrder.getReportToken(), 2, mobileOrder.getOrderNo(), mobileOrder.getPhone(), mobileOrder.getIdNumber());
                                    mobileOrderMapper.updateTimes(2, mobileOrder.getId());
                                    if(StringUtils.isBlank(data)){
                                        continue;
                                    }
                                    JSONObject jsonObj = JSON.parseObject(data);
                                    jsonObj.put("store_time", DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                                    //data：原始数据 report：报告数据
                                    jsonObj.put("data_type", "report");
                                    jsonObj.put("data_channel", MobileChannelEnum.XIN_YAN.getId());
                                    jsonObj.put("phone", mobileOrder.getPhone());
                                    jsonObj.put("timestamp", timestamp);
                                    jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                                    String fileName = "callback/mobile/mx_mobile_report_" + mobileOrder.getUserId() + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                                    aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                                    NoticeRecord noticeRecord = new NoticeRecord();
                                    noticeRecord.setReportType(NoticeRecordTypeEnum.OPERATOR.getId());
                                    noticeRecord.setUrl(mobileOrder.getCallback());
                                    noticeRecord.setResult(fileName);
                                    noticeRecord.setTimes(0);
                                    noticeRecord.setFlag(0);
                                    noticeRecord.setCreateTime(new Date());
                                    noticeRecordService.insertRecord(noticeRecord);
                                }
                            }
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                    logger.info("多线程，查询运营商报告结束...{}", threadCount);
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                logger.error("多线程,查询运营商报告异常,线程被中断", e);
            }
            // 关闭线程池
            executorService.shutdown();
        }
    }

    @Override
    public int updateStatus(int status, int type, String orderNo, String token) {
        return mobileOrderMapper.updateStatus(status, type, orderNo, token);
    }

    @Override
    public int updateResult(int type, String result, String orderNo) {
        return mobileOrderMapper.updateResult(type, result, orderNo);
    }

    @Override
    public MobileOrder loadByOrderNo(String orderNo) {
        return mobileOrderMapper.loadByOrderNo(orderNo);
    }

    @Override
    public MobileOrder queryLastByUserId(long userId, int channel) {
        return mobileOrderMapper.queryLastByUserId(userId,channel);
    }
}
