package com.free.decision.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import com.free.decision.server.mapper.BlackListMapper;
import com.free.decision.server.model.BlackList;
import com.free.decision.server.model.CreditCompany;
import com.free.decision.server.service.BlackListService;
import com.free.decision.server.service.CreditCompanyService;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 黑名单库
 */
@Service
public class BlackListServiceImpl implements BlackListService {

    private static Logger logger = LoggerFactory.getLogger(BlackListServiceImpl.class);

    @Resource
    private BlackListMapper blackListMapper;

    @Resource
    private CreditCompanyService creditCompanyService;

    @Override
    public long loadCountByIdNumber(String idNumber) {
        return blackListMapper.loadCountByIdNumber(idNumber);
    }

    @Override
    public long batchInsertBlackList(List<BlackList> blackLists) {
        return blackListMapper.batchInsertBlackList(blackLists);
    }

    @Override
    public void blackAnAnalysis() {
        //获取每家商户的数据源信息
        List<CreditCompany> creditCompanyList = creditCompanyService.loadAll();
        logger.info("所有数据源的个数为：{}", creditCompanyList.size());
        ConcurrentMap<String, Dict> blackMap = new ConcurrentHashMap<>();
        if (CollectionUtils.isNotEmpty(creditCompanyList)) {
            //每页条数
            int pageSize = 50;
            //线程数
            int threadCount = (creditCompanyList.size() % pageSize) != 0 ? (creditCompanyList.size() / pageSize) + 1 : creditCompanyList.size() / pageSize;

            // 每页一个新线程
            CountDownLatch countDownLatch = new CountDownLatch(threadCount);
            //新建一个指定线程数的 线程池
            ExecutorService executorService = ThreadUtil.newExecutor(threadCount);
            for (int i = 0; i < threadCount; i++) {
                int start = i * pageSize;
                int end = (i + 1) * pageSize - 1;
                //通讯录分析
                executorService.execute(() -> {
                    logger.info("多线程，黑名单数据分析开始...{}", threadCount);
                    try {
                        for (int j = start; j <= end; j++) {
                            if (j > creditCompanyList.size() - 1) {
                                break;
                            }
                            CreditCompany creditCompany = creditCompanyList.get(j);
                            DruidPlugin dp = null;
                            ActiveRecordPlugin arp = null;
                            try{
                                logger.info("第{}数据源{}开始访问", creditCompany.getId(), creditCompany.getAppName());
                                dp = new DruidPlugin("jdbc:mysql://" + creditCompany.getAddress() + ":" + creditCompany.getPort() + "/" + creditCompany.getExampleName()+"?serverTimezone=Asia/Shanghai",
                                        creditCompany.getUserName(), creditCompany.getPassword());
                                String uid = RandomStringUtils.randomNumeric(10);
                                arp = new ActiveRecordPlugin(uid, dp);
                                boolean start1 = dp.start();
                                boolean start2 = arp.start();
                                logger.info("第{}数据源{},连接dp结果:{}，连接arp结果:{}", creditCompany.getId(), creditCompany.getAppName(), start1, start2);
                                //查询逾期客户
                                List<Record> customerList = Db.use(uid).find("SELECT id_number as idNumber , customer_name as customerName, mobile AS mobile FROM order_info WHERE(( cut_time IS NULL AND DATEDIFF(now() , overdue_time) > 3) OR( cut_time IS NOT NULL AND DATEDIFF(cut_time , overdue_time) > 3)) GROUP BY id_number");
                                if(CollUtil.isEmpty(customerList)){
                                    continue;
                                }
                                for(Record customer : customerList){
                                    String idNumber = customer.getStr("idNumber");
                                    if(blackMap.containsKey(idNumber)){
                                        continue;
                                    }
                                    Dict dict = Dict.create();
                                    dict.set("idNumber", customer.getStr("idNumber"))
                                            .set("name", customer.getStr("customerName"))
                                            .set("mobile", customer.getStr("mobile"));
                                    blackMap.put(idNumber, dict);
                                }
                            } catch (Exception e) {
                                logger.info("第{}数据源{},连接异常", creditCompany.getId(),creditCompany.getAppName());
                                logger.error("循环内数据源连接异常！！！:", e);
                            }finally {
                                if(dp != null){
                                    dp.stop();
                                }
                                if(arp != null){
                                    arp.stop();
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("多线程，黑名单数据分析异常", e);
                    }finally {
                        countDownLatch.countDown();
                    }
                    logger.info("多线程，黑名单数据分析结束...{}", threadCount);
                });
                try {
                    countDownLatch.await();
                    if(CollUtil.isNotEmpty(blackMap)){
                        List<BlackList> blackList = new ArrayList<>();
                        blackMap.forEach((k, v) -> {
                            long count = loadCountByIdNumber(k);
                            if(count == 0){
                                BlackList black = new BlackList();
                                black.setIdNumber(v.getStr("idNumber"));
                                black.setName(v.getStr("name"));
                                black.setMobile(v.getStr("mobile"));
                                blackList.add(black);
                            }
                        });
                        batchInsertBlackList(blackList);
                    }
                    logger.info("黑名单数据分析成功！！！");
                    executorService.shutdown();
                } catch (InterruptedException e) {
                    logger.error("黑名单数据分析异常,线程被中断", e);
                }
            }
        }
    }
}
