package com.free.decision.server.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.free.decision.server.mapper.DebtAnalysisMapper;
import com.free.decision.server.model.*;
import com.free.decision.server.service.BlackListService;
import com.free.decision.server.service.DebtAnalysisService;
import com.free.decision.server.utils.ConvertBeanUtil;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 负债分析业务处理
 * qiantao
 */
@Service
public class DebtAnalysisServiceImpl implements DebtAnalysisService {

    private static Logger logger = LoggerFactory.getLogger(DebtAnalysisServiceImpl.class);

    @Resource
    private DebtAnalysisMapper debtAnalysisMapper;

    @Resource
    private BlackListService blackListService;

    /**
     * 连接数据源获取各个商户负债分析的数据
     */
    @Override
    @Transactional
    public void loadDebtAnalysisData() {
        //获取每家商户的数据源信息
        List<CreditCompany> dataBaseList = debtAnalysisMapper.getAllDataSource();
        logger.info("所有数据源的个数为：{}", dataBaseList.size());
        if (CollectionUtils.isNotEmpty(dataBaseList)) {
            //每页条数
            int pageSize = 50;
            //线程数
            int threadCount = (dataBaseList.size() % pageSize) != 0 ? (dataBaseList.size() / pageSize) + 1 : dataBaseList.size() / pageSize;

            // 每页一个新线程
            CountDownLatch countDownLatch = new CountDownLatch(threadCount);
            //新建一个指定线程数的 线程池
            ExecutorService executorService = ThreadUtil.newExecutor(threadCount);
            //建立订单流水临时表
            debtAnalysisMapper.dropOrderTempTable();
            debtAnalysisMapper.createOrderTempTable();
            //建立客户流水临时表
            debtAnalysisMapper.dropCustomerTempTable();
            debtAnalysisMapper.createCustomerTempTable();
            for (int i = 0; i < threadCount; i++) {
                int start = i * pageSize;
                int end = (i + 1) * pageSize - 1;
                //通讯录分析
                executorService.execute(() -> {
                    logger.info("多线程，连接数据源获取各个商户负债分析的数据业务开始...{}", threadCount);
                    try {
                        for (int j = start; j <= end; j++) {
                            if (j > dataBaseList.size() - 1) {
                                break;
                            }
                            CreditCompany database = dataBaseList.get(j);
                            DruidPlugin dp = null;
                            ActiveRecordPlugin arp = null;
                            try{
                                logger.info("第{}数据源{}开始访问",database.getId(),database.getAppName());
                                dp = new DruidPlugin("jdbc:mysql://" + database.getAddress() + ":" + database.getPort() + "/" + database.getExampleName()+"?serverTimezone=Asia/Shanghai", database.getUserName(), database.getPassword());
                                String UUID = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS").concat(RandomStringUtils.randomNumeric(10));
                                arp = new ActiveRecordPlugin(UUID, dp);
                                boolean start1 = dp.start();
                                boolean start2 = arp.start();
                                logger.info("第{}数据源{},连接dp结果:{}，连接arp结果:{}",database.getId(),database.getAppName(),start1,start2);
                                //订单流水
                                getOrderRecord(database, UUID);
                                //客户流水
                            } catch (Exception e) {
                                logger.info("第{}数据源{},连接异常",database.getId(),database.getAppName());
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
                        logger.error("数据源连接异常！！！:", e);
                    } finally {
                        countDownLatch.countDown();
                    }
                    logger.info("多线程，连接数据源获取各个商户负债分析的数据业务开始结束...{}", threadCount);
                });
            }
            try {
                countDownLatch.await();
                //删正式表，改名临时表
                dropAndCreateOrderTemp();
                //删正式表，改名临时表
                dropAndCreateCustomerTemp();
                logger.info("负债分析，获取信息成功！！！");

            } catch (InterruptedException e) {
                logger.error("异步通知执行异常,线程被中断", e);
            }
            // 关闭线程池
            executorService.shutdown();
        }
    }

    @Override
    @Transactional
    public void getOrderRecord(CreditCompany database, String UUID){
        System.out.println(database.getAppName());
        System.out.println(database.getId());
        List<Record> orderRecord = Db.use(UUID).find("SELECT ? AS companyFlag,t1.finance_status AS financeStatus,t1.create_time AS orderCreateTime,t1.`status` AS orderStatus,t1.cut_time AS cutTime,t1.overdue_time AS overdueTime,t2.id_number AS idNumber,t2. NAME AS NAME,t2.phone AS phone,t3.first_lend FROM credit_order t1 LEFT JOIN credit_customer_base_info t2 ON t1.customer_id = t2.id LEFT JOIN credit_order_info t3 ON t3.order_id = t1.id where t1.usable = 1", database.getId());
        List<Record> balckList = Db.use(UUID).find("SELECT t2.id_number AS idNumber,t2. NAME AS NAME,t2.phone AS mobile FROM credit_order t1 LEFT JOIN credit_customer_base_info t2 ON t1.customer_id = t2.id WHERE((cut_time IS NULL AND DATEDIFF(now(), overdue_time) > 3)OR (cut_time IS NOT NULL AND DATEDIFF(cut_time, overdue_time) > 3))GROUP BY t2.id_number");
        if(CollectionUtils.isNotEmpty(orderRecord)) {
            //订单流水数据
            List<OrderRecord> orderRecordList = ConvertBeanUtil.convertRecordList(orderRecord, OrderRecord.class);

            List<List<OrderRecord>> ret = listSplit(orderRecordList, 500);
            //写入临时表
            for (List<OrderRecord> orderList: ret) {
                if(CollectionUtils.isEmpty(orderList))
                {
                    continue;
                }
                debtAnalysisMapper.batchInsertOrderRecordTemp(orderList);
            }
        }
        if(CollectionUtils.isNotEmpty(balckList)){
            //黑名单数据
            List<BlackList> balckLists = ConvertBeanUtil.convertRecordList(balckList, BlackList.class);
            List<BlackList> newBalckLists = new ArrayList<>();
            for (BlackList bl: balckLists) {
                long res = selectBlackList(bl);
                if(res < 1){
                    newBalckLists.add(bl);
                }
            }
            List<List<BlackList>> res = listSplit(newBalckLists, 500);
            //写入黑名单表
            for (List<BlackList> blackList:res) {
                if(CollectionUtils.isEmpty(blackList))
                {
                    continue;
                }
                blackListService.batchInsertBlackList(blackList);
            }
        }
    }

    /**
     * 黑名单管理
     * @param blackList
     */
    private synchronized long selectBlackList(BlackList blackList){
        return blackListService.loadCountByIdNumber(blackList.getIdNumber());
    }


    @Override
    @Transactional
    public void getCustomerRecord(CreditCompany database, String UUID){
        List<Record> customerRecord = Db.use(UUID).find("SELECT ? AS companyFlag, t1.id_number AS idNumber, t1.create_time AS customerCreateTime FROM credit_customer_base_info t1 WHERE t1.id_number IS NOT NULL AND `status` = 1", database.getId());
        if(CollectionUtils.isNotEmpty(customerRecord)) {
            //订单流水数据
            List<CustomerRecord> customerRecordList = ConvertBeanUtil.convertRecordList(customerRecord, CustomerRecord.class);
            List<List<CustomerRecord>> ret = listSplit(customerRecordList, 500);
            //写入临时表
            for (List<CustomerRecord> customerList: ret) {
                if(CollectionUtils.isEmpty(customerList))
                {
                    continue;
                }
                try{
                    int res = debtAnalysisMapper.batchInsertCustomerRecordTemp(customerList);
                    logger.info("第{}数据源{}，批量插入客户流水结果{}",database.getId(),database.getAppName(),res);
                }
                catch (Exception e)
                {
                    logger.info("数据源{}，批量插入报错了",database.getId());
                    continue;
                }

            }
        }else{
            logger.info("数据源{}，查询不到数据",database.getId());
        }
    }


    /**
     * 获取负债分析参数
     * @param idNumber
     * @return
     */
    @Override
    public LiabilitiesParam getLiabilitiesReport(String idNumber) {
        return debtAnalysisMapper.getLiabilitiesReport(idNumber);
    }

    @Override
    public int getNumExpire(String idNumber) {
        return debtAnalysisMapper.getNumExpire(idNumber);
    }

    @Transactional
    public void dropAndCreateOrderTemp(){
        debtAnalysisMapper.dropOrderTable();
        debtAnalysisMapper.renameOrderTempTable();
    }

    @Transactional
    public void dropAndCreateCustomerTemp(){
        debtAnalysisMapper.dropCustomerTable();
        debtAnalysisMapper.renameCustomerTempTable();
    }

    /**
     * 拆分集合
     * @param <T>
     * @param resList  要拆分的集合
     * @param count    每个集合的元素个数
     * @return  返回拆分后的各个集合
     */
    private static <T> List<List<T>> listSplit(List<T> resList,int count){

        if(resList==null ||count<1)
            return  null ;
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=resList.size();
        if(size<=count){ //数据量不足count指定的大小
            ret.add(resList);
        }else{
            int pre=size/count;
            int last=size%count;
            //前面pre个集合，每个大小都是count个元素
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<count;j++){
                    itemList.add(resList.get(i*count+j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(resList.get(pre*count+i));
                }
                ret.add(itemList);
            }
        }
        return ret;

    }
}
