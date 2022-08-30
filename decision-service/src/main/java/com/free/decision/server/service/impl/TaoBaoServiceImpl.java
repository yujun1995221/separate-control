package com.free.decision.server.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.apis.XyApi;
import com.free.decision.server.enums.*;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.NoticeRecord;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.TaoBaoOrder;
import com.free.decision.server.model.vo.TaoBaoParamsVO;
import com.free.decision.server.service.*;
import com.free.decision.server.utils.AliOssUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 淘宝认证
 */
@Service
public class TaoBaoServiceImpl implements TaoBaoService {

    private static Logger logger = LoggerFactory.getLogger(TaoBaoServiceImpl.class);

    @Resource
    private UserService userService;

    @Resource
    private TaoBaoOrderService taoBaoOrderService;

    @Resource
    private XyApi xyApi;

    @Resource
    private NoticeRecordService noticeRecordService;

    @Resource
    private AliOssUtils aliOssUtils;

    @Value("${oss.report.bucket}")
    private String reportBucket;

    @Value("${decision.domain}")
    private String domain;

    @Resource
    private ConsumeService consumeService;

    @Resource
    private CompanyService companyService;

    @Override
    public Result createOrder(TaoBaoParamsVO taoBaoParamsVO, Company company) {
        Long userId = userService.getUserIdByMobileAndIdNumber(company.getId(), taoBaoParamsVO.getPhone(), taoBaoParamsVO.getIdNumber(), taoBaoParamsVO.getName());
        if (userId == null) {
            return new Result(false, "淘宝认证请求失败");
        }
        Map<String, Object> dataMap = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        String orderNo = DateFormatUtils.format(timestamp, "yyyyMMddHHmmssS") + RandomStringUtils.randomNumeric(10);
        TaoBaoOrder taoBaoOrder = new TaoBaoOrder();
        taoBaoOrder.setCompanyId(company.getId());
        taoBaoOrder.setUserId(userId);
        taoBaoOrder.setPhone(taoBaoParamsVO.getPhone());
        taoBaoOrder.setChannel(TaoBaoChannelEnum.XIN_YAN.getId());
        taoBaoOrder.setOrderNo(orderNo);
        taoBaoOrder.setCallback(taoBaoParamsVO.getCallback());
        taoBaoOrder.setDataStatus(TaoBaoOrderStatusEnum.UN_KNOW.getId());
        taoBaoOrder.setReportStatus(TaoBaoOrderStatusEnum.UN_KNOW.getId());
        taoBaoOrder.setCreateTime(new Date());
        taoBaoOrderService.add(taoBaoOrder);
        dataMap.put("taskId", orderNo);
        dataMap.put("dataCallback", domain+"/taobao/callback/xinyan");
        dataMap.put("cache", false);
        return new Result(true, "淘宝认证请求成功", dataMap);
    }

    @Override
    public void queryData() {
        List<TaoBaoOrder> taoBaoOrderList = taoBaoOrderService.getDoingOrder();
        if (CollectionUtils.isNotEmpty(taoBaoOrderList)) {
            //每页条数
            int pageSize = 50;
            //线程数
            int threadCount = (taoBaoOrderList.size() % pageSize) != 0 ? (taoBaoOrderList.size() / pageSize) + 1 : taoBaoOrderList.size() / pageSize;
            // 每页一个新线程
            CountDownLatch countDownLatch = new CountDownLatch(threadCount);
            //新建一个指定线程数的 线程池
            ExecutorService executorService = ThreadUtil.newExecutor(threadCount);
            for (int i = 0; i < threadCount; i++) {
                int start = i * pageSize;
                int end = (i + 1) * pageSize - 1;
                //通讯录分析
                executorService.execute(() -> {
                    logger.info("多线程，查询淘宝报告开始...{}", threadCount);
                    try {
                        for (int j = start; j <= end; j++) {
                            if (j > taoBaoOrderList.size() - 1) {
                                break;
                            }
                            TaoBaoOrder taoBaoOrder = taoBaoOrderList.get(j);
                            Company company = companyService.loadById(taoBaoOrder.getCompanyId());
                            long timestamp = System.currentTimeMillis();
                            if (taoBaoOrder.getChannel() == TaoBaoChannelEnum.XIN_YAN.getId()) {
                                if (taoBaoOrder.getDataStatus() == TaoBaoOrderStatusEnum.DOING.getId()) {
                                    taoBaoOrderService.updateTimes(1, taoBaoOrder.getId());
                                    String data = xyApi.getData(taoBaoOrder.getDataToken(), 1, taoBaoOrder.getOrderNo());
                                    if(StringUtils.isBlank(data)){
                                        continue;
                                    }
                                    JSONObject jsonObj = JSON.parseObject(data);
                                    jsonObj.remove("id");
                                    jsonObj.remove("token");
                                    jsonObj.remove("updateTime");
                                    jsonObj.put("store_time", DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss"));
                                    //data：原始数据 report：报告数据
                                    jsonObj.put("data_type", "data");
                                    jsonObj.put("data_channel", TaoBaoChannelEnum.XIN_YAN.getId());
                                    jsonObj.put("phone", taoBaoOrder.getPhone());
                                    jsonObj.put("timestamp", timestamp);
                                    jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                                    String fileName = "callback/taobao/xy_taobao_original_" + taoBaoOrder.getUserId() + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                                    aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                                    NoticeRecord noticeRecord = new NoticeRecord();
                                    noticeRecord.setReportType(NoticeRecordTypeEnum.TAOBAO.getId());
                                    noticeRecord.setUrl(taoBaoOrder.getCallback());
                                    noticeRecord.setResult(fileName);
                                    noticeRecord.setTimes(0);
                                    noticeRecord.setFlag(0);
                                    noticeRecord.setCreateTime(new Date());
                                    noticeRecordService.insertRecord(noticeRecord);
                                    //插入消费记录
                                    consumeService.consume(company.getId(), company.getTaoBaoPrice(), ReportTypeEnum.TAO_BAO, ConsumeTypeEnum.CONSUME, "淘宝认证-" + taoBaoOrder.getPhone());
                                } else if (taoBaoOrder.getReportStatus() == TaoBaoOrderStatusEnum.DOING.getId()) {
                                    taoBaoOrderService.updateTimes(2, taoBaoOrder.getId());
                                    String data = xyApi.getReportData(taoBaoOrder.getReportToken(), 1, taoBaoOrder.getOrderNo());
                                    if(StringUtils.isBlank(data)){
                                        continue;
                                    }
                                    JSONObject jsonObj = JSON.parseObject(data);
                                    jsonObj.remove("id");
                                    jsonObj.remove("token");
                                    jsonObj.remove("updateTime");
                                    jsonObj.put("store_time", DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                                    //data：原始数据 report：报告数据
                                    jsonObj.put("data_type", "report");
                                    jsonObj.put("data_channel", TaoBaoChannelEnum.XIN_YAN.getId());
                                    jsonObj.put("phone", taoBaoOrder.getPhone());
                                    jsonObj.put("timestamp", timestamp);
                                    jsonObj.put("sign", SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp)));
                                    String fileName = "callback/taobao/xy_taobao_report_" + taoBaoOrder.getUserId() + "_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS") + "_" + RandomStringUtils.randomNumeric(5) + ".txt";
                                    aliOssUtils.uploadReport(reportBucket, fileName, jsonObj.toJSONString());
                                    NoticeRecord noticeRecord = new NoticeRecord();
                                    noticeRecord.setReportType(NoticeRecordTypeEnum.TAOBAO.getId());
                                    noticeRecord.setUrl(taoBaoOrder.getCallback());
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
                    logger.info("多线程，查询淘宝报告结束...{}", threadCount);
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                logger.error("多线程,查询淘宝报告异常,线程被中断", e);
            }
            // 关闭线程池
            executorService.shutdown();
        }
    }
}
