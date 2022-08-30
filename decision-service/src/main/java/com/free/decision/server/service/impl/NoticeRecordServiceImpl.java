package com.free.decision.server.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.free.decision.server.constants.ConfigConstant;
import com.free.decision.server.enums.NoticeRecordTypeEnum;
import com.free.decision.server.mapper.NoticeRecordMapper;
import com.free.decision.server.model.NoticeRecord;
import com.free.decision.server.service.NoticeRecordService;
import com.free.decision.server.utils.AliOssUtils;
import com.free.decision.server.utils.HttpKit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Service
public class NoticeRecordServiceImpl implements NoticeRecordService {

    private static Logger logger = LoggerFactory.getLogger(NoticeRecordServiceImpl.class);

    @Resource
    private NoticeRecordMapper noticeRecordMapper;

    @Resource
    private AliOssUtils aliOssUtils;

    @Value("${oss.report.bucket}")
    private String reportBucket;

    @Override
    public void asyncNotice() {
        List<Long> idList = noticeRecordMapper.getNeedSendNoticeId(ConfigConstant.TIMES_MAX);
        if (CollectionUtils.isNotEmpty(idList)) {
            //每页条数
            int pageSize = 50;
            //线程数
            int threadCount = (idList.size() % pageSize) != 0 ? (idList.size() / pageSize)+1 : idList.size() / pageSize;

            // 每页一个新线程
            CountDownLatch countDownLatch = new CountDownLatch(threadCount);
            //新建一个指定线程数的 线程池
            ExecutorService executorService = ThreadUtil.newExecutor(threadCount);

            for(int i = 0; i <threadCount ; i++){
                int start = i * pageSize;
                int end = (i + 1) * pageSize - 1;
                //通讯录分析
                executorService.execute(() -> {
                    logger.info("多线程，异步通知第三方开始...{}",threadCount);
                    try {
                        for (int j = start; j <= end; j++) {
                            if (j > idList.size() - 1) {
                                break;
                            }
                            Long id = idList.get(j);
                            Map<String,String> header = new HashMap<>();
                            header.put("Content-Type", "application/json;charset=UTF-8");
                            String postString = "";
                            //根据id查询记录
                            NoticeRecord noticeRecord = noticeRecordMapper.getNoticeById(id,ConfigConstant.TIMES_MAX);
                            if (noticeRecord == null){
                                continue;
                            }
                            noticeRecordMapper.updateTimes(id);
                            try {
                                String data = noticeRecord.getResult();
                                if(NoticeRecordTypeEnum.OPERATOR.getId() == noticeRecord.getReportType() || NoticeRecordTypeEnum.TAOBAO.getId() == noticeRecord.getReportType()){
                                    data = aliOssUtils.readReport(reportBucket, data);
                                }
                                postString = HttpKit.post(noticeRecord.getUrl(), data, header);
                            } catch (Exception e) {
                                logger.error("决策引擎回调出错",e);
                                continue;
                            }
                            if ("SUCCESS".equalsIgnoreCase(StringUtils.chop(postString))) {
                                noticeRecordMapper.updateSuccess(id);
                            }
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                    logger.info("多线程，异步通知第三方结束...{}",threadCount);
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                logger.error("异步通知执行异常,线程被中断", e);
            }
            // 关闭线程池
            executorService.shutdown();
        }
    }

    @Override
    public int insertRecord(NoticeRecord noticeRecord) {
        return noticeRecordMapper.insertRecord(noticeRecord);
    }
}
