package com.free.decision.server.task;

import com.free.decision.server.enums.SmsContentEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.SmsSendParamVO;
import com.free.decision.server.service.*;
import net.oschina.j2cache.CacheChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 系统任务
 */
@Component
public class SystemTask {

    private static Logger logger = LoggerFactory.getLogger(SystemTask.class);

    @Value("${devModel}")
    private boolean devModel;

    @Resource
    private CacheChannel cacheChannel;

    @Resource
    private MobileService mobileService;

    @Resource
    private NoticeRecordService noticeRecordService;

    @Resource
    private CompanyService companyService;

    @Resource
    private TaoBaoService taoBaoService;

    @Resource
    private SmsSendService smsSendService;

    @Resource
    private DebtAnalysisService debtAnalysisService;

    @Resource
    private BlackListService blackListService;

    /**
     * 异步通知第三方
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void sendAsynNotice() {
        Integer running = (Integer) cacheChannel.get("sync.notice", "send_Asyn_Notice").getValue();
        if (running != null && running == 1) {
            logger.warn("异步通知第三方job,有缓存再跑，直接结束");
            return;
        }
        logger.info("===============异步通知执行开始===============");
        try {
            cacheChannel.set("sync.notice", "send_Asyn_Notice", 1);
            noticeRecordService.asyncNotice();
        } catch (Exception e) {
            logger.error("异步通知执行异常", e);
        } finally {
            logger.info("===============异步通知执行结束===============");
            cacheChannel.set("sync.notice", "send_Asyn_Notice", 0);
        }
    }

    /**
     * 给余额大于100 小于500的公司发送短信提醒
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void sendAmountSms() {
        Integer running = (Integer) cacheChannel.get("job.running", "send_amount_sms").getValue();
        if (running != null && running == 1) {
            return;
        }
        if(devModel){
            return;
        }
        logger.info("===============余额不足短信提醒发送job开始===============");
        try {
            cacheChannel.set("job.running", "send_amount_sms", 1);
            List<Long> companyIds = companyService.getAllCompanyIds();
            if (companyIds != null){
                //当天已经发送短信的公司不再发送
                for (Long companyId : companyIds) {
                    Object cacheCompanyId = cacheChannel.get("decision.amount.sms.list","companyId_"+companyId).getValue();
                    BigDecimal amount = companyService.getAmount(companyId);
                    //缓存里有公司id || 公司余额小于100 || 公司余额大于2000
                    if (cacheCompanyId != null || amount.compareTo(new BigDecimal(100)) <= 0 || amount.compareTo(new BigDecimal(1000)) >= 0){
                        continue;
                    }
                    Company company = companyService.loadById(companyId);
                    Date dd = Calendar.getInstance().getTime();
                    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
                    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
                    SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
                    String day = sdfDay.format(dd);
                    String month = sdfMonth.format(dd);
                    String hour = sdfHour.format(dd);
                    //调用决策引擎的短信发送接口
                    SmsSendParamVO smsSendParamVO = new SmsSendParamVO();
                    smsSendParamVO.setIp("127.0.0.1");
                    smsSendParamVO.setMobile(company.getContactMobile());
                    smsSendParamVO.setSmsSign("【" + company.getAppName() + "】");
                    smsSendParamVO.setContent(company.getAppName() +","+month +","+ day +","+ hour +","+ amount.setScale(0, BigDecimal.ROUND_HALF_UP));// "尊敬的{$var}客户，截止到{$var}月{$var}日{$var}时，您的余额已不足{$var}元。请您及时充值，以免余额不足而影响正常使用，谢谢。"
                    smsSendParamVO.setSmsType(SmsContentEnum.BALANCE.getId());
                    Result apiResult = smsSendService.send(smsSendParamVO, company);
                    cacheChannel.set("decision.amount.sms.list" ,"companyId_"+companyId,1);
                    if (apiResult.isSuccess()) {
                        logger.info("===============余额不足短信提醒发送job执行成功，公司id:{}===============", companyId);
                    } else {
                        logger.error("===============余额不足短信提醒发送job执行失败，公司id:{}===============", companyId);
                    }
                }
            }
        }catch (Exception e) {
            logger.error("余额不足短信提醒job执行异常", e);
        } finally {
            logger.info("===============余额不足短信提醒发送job执行结束===============");
            cacheChannel.set("job.running", "send_amount_sms", 0);
        }
    }

    /**
     * 淘宝报告查询
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void taobaoData(){
        Integer running = (Integer) cacheChannel.get("job.running", "taobao_data").getValue();
        if (running != null && running == 1) {
            logger.warn("异步处理淘宝报告job,有缓存再跑，直接结束");
            return;
        }
        logger.info("===============异步处理淘宝报告job开始===============");
        try {
            cacheChannel.set("job.running", "taobao_data", 1);
            //业务代码
            taoBaoService.queryData();
        } catch (Exception e) {
            logger.error("异步处理淘宝报告job执行异常", e);
        } finally {
            logger.info("===============异步处理淘宝报告job执行结束===============");
            cacheChannel.set("job.running", "taobao_data", 0);
        }
    }

    /**
     * 运营商报告查询
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void mobileData(){
        Integer running = (Integer) cacheChannel.get("job.running", "mobile_data").getValue();
        if (running != null && running == 1) {
            logger.warn("异步处理运营商报告job,有缓存再跑，直接结束");
            return;
        }
        logger.info("===============异步处理运营商报告job开始===============");
        try {
            cacheChannel.set("job.running", "mobile_data", 1);
            //业务代码
            mobileService.queryData();
        } catch (Exception e) {
            logger.error("异步处理运营商报告job执行异常", e);
        } finally {
            logger.info("===============异步处理运营商报告job执行结束===============");
            cacheChannel.set("job.running", "mobile_data", 0);
        }
    }

    /**
     * 负债分析
     */
   /* @Scheduled(cron = "0 0 1 * * ?")
    public void getDebtAnalysisData() {
        Integer running = (Integer) cacheChannel.get("job.running", "get_debt_analysis_data").getValue();
        if (running != null && running == 1) {
            logger.warn("负债分析数据job,有缓存再跑，直接结束");
            return;
        }
        logger.info("===============负债分析数据job开始===============");
        try {
            cacheChannel.set("job.running", "get_debt_analysis_data", 1);
            //业务代码
            debtAnalysisService.loadDebtAnalysisData();
        } catch (Exception e) {
            logger.error("负债分析数据job执行异常", e);
        } finally {
            logger.info("===============负债分析数据job执行结束===============");
            cacheChannel.clear("decision.liabilities.report.id");
            cacheChannel.set("job.running", "get_debt_analysis_data", 0);
        }
    }*/

    /**
     * 黑名单分析
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void blackList(){
        Integer running = (Integer) cacheChannel.get("job.running", "black_list").getValue();
        if (running != null && running == 1) {
            logger.warn("黑名单job,有缓存再跑，直接结束");
            return;
        }
        logger.info("===============黑名单job开始===============");
        try {
            cacheChannel.set("job.running", "black_list", 1);
            //业务代码
            blackListService.blackAnAnalysis();
        } catch (Exception e) {
            logger.error("黑名单job执行异常", e);
        } finally {
            logger.info("===============黑名单job执行结束===============");
            cacheChannel.set("job.running", "black_list", 0);
        }
    }
}
