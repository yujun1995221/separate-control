package com.free.decision.server.apis;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.apis.util.HttpUtils;
import com.free.decision.server.apis.util.RsaCodingUtil;
import com.free.decision.server.apis.util.SecurityUtil;
import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.enums.XyActionRadarCodeEnum;
import com.free.decision.server.enums.XyRecordTypeEnum;
import com.free.decision.server.model.*;
import com.free.decision.server.model.vo.BankCardAuthVO;
import com.free.decision.server.model.vo.DecisionRadarDetailVO;
import com.free.decision.server.model.vo.DecisionRadarParamsVO;
import com.free.decision.server.service.*;
import com.free.decision.server.utils.AliOssUtils;
import com.free.decision.server.utils.HashUtil;
import com.free.decision.server.utils.HttpKit;
import jodd.util.StringUtil;
import jodd.util.URLDecoder;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 新颜api
 * @author Xingyf
 */
@Component
public class XyApi{

    private static Logger logger = LoggerFactory.getLogger(XyApi.class);

    @Value("${decision.xy.member.id}")
    private String memberId;

    @Value("${decision.xy.terminal.id}")
    private String terminalId;

    @Value("${decision.xy.pfx.name}")
    private String pfxName;

    @Value("${decision.xy.pfx.pwd}")
    private String pfxPwd;

    @Value("${decision.xy.apiUser}")
    private String apiUser;

    @Value("${decision.xy.apiKey}")
    private String apiKey;

    @Resource
    private AliOssUtils aliOssUtils;

    @Value("${oss.report.bucket}")
    private String reportBucket;

    @Resource
    private XyService xyService;

    @Resource
    private UserService userService;

    @Resource
    private ConsumeService consumeService;

    @Resource
    private CacheChannel cacheChannel;

    @Resource
    private TaoBaoOrderService taoBaoOrderService;

    @Resource
    private MobileService mobileService;

    @Value("${devModel}")
    private boolean devModel;

    /**
     * 新颜银行卡四要素验证
     * @param userId
     * @param bankCardAuthVO
     * @param company
     * @return
     */
    @RequestMapping("/bank")
    public Result bank(long userId, BankCardAuthVO bankCardAuthVO , Company company) {
        // 商户订单号
        String transId = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+ RandomStringUtils.randomNumeric(10);
        /* 组装参数 */
        Map<String, Object> arrayData = new HashMap<>();
        arrayData.put("member_id", memberId);
        arrayData.put("terminal_id", terminalId);
        arrayData.put("trans_id", transId);// 商户订单号
        arrayData.put("trade_date", DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss"));
        arrayData.put("acc_no", bankCardAuthVO.getAccNo());
        arrayData.put("id_card", bankCardAuthVO.getIdCard());
        arrayData.put("mobile", bankCardAuthVO.getMobile());
        arrayData.put("id_type", 0);
        arrayData.put("card_type", 101);
        arrayData.put("id_holder", bankCardAuthVO.getIdHolder());
        arrayData.put("verify_element", 1234);
        arrayData.put("industry_type", "B14");
        net.sf.json.JSONObject jsonObjectFromMap = net.sf.json.JSONObject.fromObject(arrayData);
        String xmlOrJson = jsonObjectFromMap.toString();
        logger.info("新颜银行卡鉴权，请求明文:{}", xmlOrJson);
        /** base64 编码 **/
        String base64str =  Base64.encode(xmlOrJson);
        base64str=base64str.replaceAll("\r\n", "");//重要 避免出现换行空格符

        /** rsa加密 **/
        String classPath;
        try {
            classPath = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            logger.error("获取文件url路径异常:", e);
            return new Result(false, "鉴权失败");
        }
        // 商户私钥
        String pfxPath= classPath + "ca/xinyan/" +pfxName;
        File pfxFile = new File(pfxPath);
        if (!pfxFile.exists()) {
            logger.warn("新颜银行卡鉴权，私钥文件不存在！");
            throw new RuntimeException("私钥文件不存在！");
        }

        String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxPath, pfxPwd);// 加密数据
        logger.info("新颜银行卡鉴权，加密串:{}", dataContent);

        /** ============== http 请求==================== **/
        Map<String, String> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("terminal_id", terminalId);
        params.put("data_type", "json");
        params.put("data_content", dataContent);
        String postString = HttpUtils.doPostByForm("https://api.xinyan.com/bankcard/v3/auth", headers, params);
        XyRecord xyRecord = new XyRecord();
        xyRecord.setCompanyId(company.getId());
        xyRecord.setUserId(userId);
        xyRecord.setRequestParams(JSON.toJSONString(bankCardAuthVO));
        xyRecord.setResult(JSON.toJSONString(postString));
        xyRecord.setType(XyRecordTypeEnum.BANK_AUTH.getId());
        // 插入接口请求调用记录
        xyService.insertRecord(xyRecord);
        Result result = JSON.parseObject(postString, Result.class);
        if(!result.isSuccess()){
            return new Result(false, "请求参数有误");
        }
        net.sf.json.JSONObject postData = net.sf.json.JSONObject.fromObject(postString);
        String data = postData.getString("data");
        net.sf.json.JSONObject jsonData = net.sf.json.JSONObject.fromObject(data);
        String code = jsonData.getString("code");
        if(XyActionRadarCodeEnum.LOSE.getId()==NumberUtils.toInt(code)){
            return new Result(false, "认证信息不一致",data);
        }else if(XyActionRadarCodeEnum.FALSE.getId()==NumberUtils.toInt(code)){
            return new Result(false, "认证失败");
        }else if(XyActionRadarCodeEnum.OTHER.getId()==NumberUtils.toInt(code)){
            return new Result(false, "请求异常");
        }
        return new Result(true, "认证成功" ,data);
    }

    /**
     * 获取行为雷达报告
     * @param decisionRadarParamsVO
     * @return
     */
    @Transactional
    public Result getRadar(DecisionRadarParamsVO decisionRadarParamsVO, Company company) {
        XyActionRadarParam xyActionRadarParam;
        // 获取缓存数据
        Long id = (Long) cacheChannel.get("decision.radar.report.id", "radar_record_id_" + company.getId() + "_" + decisionRadarParamsVO.getIdNo() + "_" + decisionRadarParamsVO.getPhoneNo()).getValue();
        if (id != null && id > 0) {
            logger.info("结束执行行为雷达接口,从缓存中读取成功 ===> companyId_" + company.getId() + "idNo_" + decisionRadarParamsVO.getPhoneNo());
            String ret = xyService.getXyRecord(id);
            if (StringUtil.isNotBlank(ret)) {
                xyActionRadarParam = JSON.parseObject(ret, XyActionRadarParam.class);
                //插入数据
                xyActionRadarParam.setCreateTime(new Date());
                xyService.insertRadar(xyActionRadarParam);
                //插入消费记录
                consumeService.consume(company.getId(), company.getCreditPrice(), ReportTypeEnum.CREDIT, ConsumeTypeEnum.CONSUME, "查询多头信贷报告-" + decisionRadarParamsVO.getPhoneNo());
                return new Result(true, "查询数据成功", xyActionRadarParam);
            }
        }
        xyActionRadarParam = new XyActionRadarParam();
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        // 商户订单号
        String transId = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+ RandomStringUtils.randomNumeric(10);
        // 交易日期
        String tradeDate = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss");
        Map<Object, Object> arrayData = new HashMap<>();
        arrayData.put("member_id", memberId);
        arrayData.put("terminal_id", terminalId);
        arrayData.put("trade_date", tradeDate);
        arrayData.put("trans_id", transId);
        arrayData.put("id_no", HashUtil.md5(decisionRadarParamsVO.getIdNo()).toLowerCase());
        arrayData.put("id_name", HashUtil.md5(decisionRadarParamsVO.getIdName()).toLowerCase());
        arrayData.put("phone_no", HashUtil.md5(decisionRadarParamsVO.getPhoneNo()).toLowerCase());
        arrayData.put("bankcard_no",HashUtil.md5(decisionRadarParamsVO.getBankcardNo()).toLowerCase());
        arrayData.put("versions", "1.3.0");
        arrayData.put("industry_type", "B14");
        logger.info("执行行为雷达接口,获取请求参数 ===> companyId_" + company.getId() + "idNo_" + decisionRadarParamsVO.getIdNo());
        net.sf.json.JSONObject jsonObjectFromMap = net.sf.json.JSONObject.fromObject(arrayData);
        String xmlOrJson = jsonObjectFromMap.toString();
        String base64str;
        String classPath;
        /** base64 编码 **/
        try {
            base64str = SecurityUtil.Base64Encode(xmlOrJson);
        } catch (UnsupportedEncodingException e) {
            logger.error("数据格式转换异常:", e);
            return new Result(false, "查询失败");
        }
        try {
            classPath = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            logger.error("获取文件url路径异常:", e);
            return new Result(false, "查询失败");
        }
        /** rsa加密 */
        String pfxPath= classPath + "ca/xinyan/" +pfxName;
        File pfxFile = new File(pfxPath);
        if (!pfxFile.exists()) {
            logger.warn("行为雷达私钥文件不存在");
            return new Result(false, "查询失败");
        }
        // 加密数据
        String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxPath, pfxPwd);
        String url = "https://api.xinyan.com/product/radar/v3/behavior";
        params.put("member_id", memberId);
        params.put("terminal_id", terminalId);
        params.put("data_type", "json");
        params.put("data_content", dataContent);
        String postString = HttpUtils.doPostByForm(url, headers, params);
        // 获取用户userId
        Long userId = userService.getUserIdByMobileAndIdNumber(company.getId(), decisionRadarParamsVO.getPhoneNo(), decisionRadarParamsVO.getIdNo(), decisionRadarParamsVO.getIdName());
        xyActionRadarParam.setCreateTime(new Date());
        XyRecord xyRecord = new XyRecord();
        xyRecord.setCompanyId(company.getId());
        xyRecord.setUserId(userId);
        xyRecord.setType(XyRecordTypeEnum.RADAR.getId());
        xyRecord.setRequestParams(JSON.toJSONString(decisionRadarParamsVO));
        Result resultData = JSON.parseObject(postString, Result.class);
        if (!resultData.isSuccess()) {
            xyRecord.setResult(JSON.toJSONString(postString));
            // 插入接口请求调用记录
            xyService.insertRecord(xyRecord);
            return new Result(false, ResultCodeEnum.EXCEPTION.getId(),"请求参数有误");
        }
        net.sf.json.JSONObject postData = net.sf.json.JSONObject.fromObject(postString);
        String data = postData.getString("data");
        net.sf.json.JSONObject jsonData = net.sf.json.JSONObject.fromObject(data);
        String code = jsonData.getString("code");
        //对象赋值
        xyActionRadarParam.setIdNo(decisionRadarParamsVO.getIdNo());
        xyActionRadarParam.setIdName(decisionRadarParamsVO.getIdName());
        xyActionRadarParam.setVersions(jsonData.getString("versions"));
        xyActionRadarParam.setCode(jsonData.getString("code"));
        xyActionRadarParam.setUserId(userId);
        String resultDetail = jsonData.getString("result_detail");
        //判断决策是否命中,0是命中，1是未命中
        if (NumberUtils.toInt(code) == XyActionRadarCodeEnum.LOSE.getId()) {
            xyActionRadarParam.setConsfinOrgCount(0);
            xyActionRadarParam.setHistoryFailFee(0);
            xyActionRadarParam.setHistorySucFee(0);
            xyActionRadarParam.setLatestOneMonth(0);
            xyActionRadarParam.setLatestOneMonthFail(0);
            xyActionRadarParam.setLatestOneMonthSuc(0);
            xyActionRadarParam.setLatestSixMonth(0);
            xyActionRadarParam.setLatestThreeMonth(0);
            xyActionRadarParam.setLoansCashCount(0);
            xyActionRadarParam.setLoansCount(0);
            xyActionRadarParam.setLoansCredibility(0);
            xyActionRadarParam.setLoansSettleCount(0);
            xyActionRadarParam.setLoansScore(0);
            xyActionRadarParam.setLoansOrgCount(0);
            xyActionRadarParam.setLoansLongTime(0);
            xyActionRadarParam.setLoansOverdueCount(0);
        } else if (NumberUtils.toInt(code) == XyActionRadarCodeEnum.SUCCESS.getId()) {
            DecisionRadarDetailVO decisionRadarDetailVO = JSON.parseObject(resultDetail, DecisionRadarDetailVO.class);
            xyActionRadarParam.setConsfinOrgCount(decisionRadarDetailVO.getConsfinOrgCount());
            xyActionRadarParam.setHistoryFailFee(decisionRadarDetailVO.getHistoryFailFee());
            xyActionRadarParam.setHistorySucFee(decisionRadarDetailVO.getHistorySucFee());
            xyActionRadarParam.setLatestOneMonth(decisionRadarDetailVO.getLatestOneMonth());
            xyActionRadarParam.setLatestOneMonthFail(decisionRadarDetailVO.getLatestOneMonthFail());
            xyActionRadarParam.setLatestOneMonthSuc(decisionRadarDetailVO.getLatestOneMonthSuc());
            xyActionRadarParam.setLatestSixMonth(decisionRadarDetailVO.getLatestSixMonth());
            xyActionRadarParam.setLatestThreeMonth(decisionRadarDetailVO.getLatestThreeMonth());
            xyActionRadarParam.setLoansCashCount(decisionRadarDetailVO.getLoansCashCount());
            xyActionRadarParam.setLoansCount(decisionRadarDetailVO.getLoansCount());
            xyActionRadarParam.setLoansCredibility(decisionRadarDetailVO.getLoansCredibility());
            xyActionRadarParam.setLoansSettleCount(decisionRadarDetailVO.getLoansSettleCount());
            xyActionRadarParam.setLoansScore(decisionRadarDetailVO.getLoansScore());
            xyActionRadarParam.setLoansOrgCount(decisionRadarDetailVO.getLoansOrgCount());
            xyActionRadarParam.setLoansLongTime(decisionRadarDetailVO.getLoansLongTime());
            xyActionRadarParam.setLoansOverdueCount(decisionRadarDetailVO.getLoansOverdueCount());
            xyActionRadarParam.setLoansLatestTime(decisionRadarDetailVO.getLoansLatestTime());
        } else {
            return new Result(false, "接口异常查询失败");
        }
        xyRecord.setResult(JSON.toJSONString(xyActionRadarParam));
        // 插入接口请求调用记录
        xyService.insertRecord(xyRecord);
        //插入数据
        xyService.insertRadar(xyActionRadarParam);
        //插入消费记录
        consumeService.consume(company.getId(), company.getCreditPrice(), ReportTypeEnum.CREDIT, ConsumeTypeEnum.CONSUME, "查询多头信贷报告-" + decisionRadarParamsVO.getPhoneNo());
        // 将数据进行缓存
        cacheChannel.set("decision.radar.report.id", "radar_record_id_" + company.getId() + "_" + decisionRadarParamsVO.getIdNo() + "_" + decisionRadarParamsVO.getPhoneNo(), xyRecord.getId());
        logger.info("从行为雷达接口请求成功 ===> companyId_" + company.getId() + ",idNo_" + decisionRadarParamsVO.getIdNo());
        return new Result(true, "查询成功", xyActionRadarParam);
    }

    /**
     * 查询数据
     * @param token
     * @param type 1：淘宝 2：运营商
     * @return
     */
    public String getData(String token, int type, String orderNo){
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("apiUser", apiUser);
        params.put("apiEnc", SecureUtil.md5(apiUser+apiKey).toLowerCase());
        String ret = HttpKit.get("https://qz.xinyan.com/api/user/data", params);
        JSONObject jsonObj = JSON.parseObject(ret);
        String code = jsonObj.getString("code");
        if(StringUtils.equals(code, "20000")){
            if(type == 1){
                String fileName = "report/taobao/xy_taobao_original_"+DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+"_"+token+".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, ret);
                taoBaoOrderService.updateResult(1, fileName, orderNo);
            }
            else if(type == 2){
                String fileName = "report/mobile/xy_mobile_original_"+DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+"_"+token+".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, ret);
                mobileService.updateResult(1, fileName, orderNo);
            }
            return jsonObj.getString("detail");
        }
        logger.warn("新颜原始数据查询失败,type:{}, code:{},msg:{}", type, code, jsonObj.getString("msg"));
        return "";
    }

    /**
     * 查询报告数据
     * @param token
     * @param type 1：淘宝 2：运营商
     * @return
     */
    public String getReportData(String token, int type, String orderNo){
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("apiUser", apiUser);
        params.put("apiEnc", SecureUtil.md5(apiUser+apiKey).toLowerCase());
        Integer contactsFlag = (Integer) cacheChannel.get("decision.mobile.contacts", "xy_orderNo_"+orderNo).getValue();
        //运营商上传通讯录和紧急联系人
        if(type == 2 && contactsFlag != null && contactsFlag != 1){
            MobileOrder mobileOrder = mobileService.loadByOrderNo(orderNo);
            String phoneList = mobileOrder.getPhoneList();
            String emergContacts = mobileOrder.getEmergContacts();
            if(StringUtils.isNotBlank(phoneList) && StringUtils.isNotBlank(emergContacts)){
                params.put("contacts", aliOssUtils.readReport(reportBucket, phoneList));
                params.put("emergContacts", mobileOrder.getEmergContacts());
                String ret = HttpKit.get("https://qz.xinyan.com/api/user/data/report/upload/contact", params);
                JSONObject obj = JSON.parseObject(ret);
                if(StringUtils.equals(obj.getString("code"), "20000")){
                    cacheChannel.set("decision.mobile.contacts", "xy_orderNo_"+orderNo, "1");
                }
                return "";
            }
        }
        String ret = HttpKit.get("https://qz.xinyan.com/api/user/data/report/json", params);
        JSONObject jsonObj = JSON.parseObject(ret);
        String code = jsonObj.getString("code");
        if(StringUtils.equals(code, "20000")){
            if(type == 1){
                String fileName = "report/taobao/xy_taobao_report_"+DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+"_"+token+".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, ret);
                taoBaoOrderService.updateResult(2, fileName, orderNo);
            }
            else if(type == 2){
                String fileName = "report/mobile/xy_mobile_report_"+DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+"_"+token+".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, ret);
                mobileService.updateResult(2, fileName, orderNo);
                cacheChannel.evict("decision.mobile.contacts", "xy_orderNo_"+orderNo);
            }
            return jsonObj.getString("detail");
        }
        logger.warn("新颜报告数据查询失败,type:{}, code:{},msg:{}", type, code, jsonObj.getString("msg"));
        return "";
    }

    /**
     * 多头雷达
     * @param company
     * @param idNumber
     * @param name
     * @param userId
     * @return
     */
    public Result getRadarNew(Company company, String idNumber, String name, long userId){
        // 商户订单号
        String transId = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+ RandomStringUtils.randomNumeric(10);
        /* 组装参数 */
        Map<String, Object> arrayData = new HashMap<>();
        arrayData.put("member_id", memberId);
        arrayData.put("terminal_id", terminalId);
        arrayData.put("trans_id", transId);
        arrayData.put("trade_date",  DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss"));
        arrayData.put("id_no", SecureUtil.md5(idNumber));
        arrayData.put("id_name", SecureUtil.md5(name));
        arrayData.put("product_type", "DTLD");
        arrayData.put("versions", "1.3.0");
        arrayData.put("encrypt_type", "MD5");
        String xmlOrJson = JSON.toJSONString(arrayData);
        logger.info("多头雷达，请求明文:{}", xmlOrJson);
        /** base64 编码 **/
        String base64str =  Base64.encode(xmlOrJson);
        base64str=base64str.replaceAll("\r\n", "");//重要 避免出现换行空格符

        /** rsa加密 **/
        String classPath;
        try {
            classPath = ResourceUtils.getURL("classpath:").getPath();
            if(devModel){
                classPath = URLDecoder.decode(classPath, CharsetUtil.UTF_8);
            }
        } catch (FileNotFoundException e) {
            logger.error("获取文件url路径异常:", e);
            return new Result(false, "查询失败");
        }
        // 商户私钥
        String pfxPath= classPath + "ca/xinyan/" +pfxName;
        File pfxFile = new File(pfxPath);
        if (!pfxFile.exists()) {
            logger.warn("多头雷达，私钥文件不存在！");
            throw new RuntimeException("私钥文件不存在！");
        }
        String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxPath, pfxPwd);// 加密数据
        logger.info("多头雷达，加密串:{}", dataContent);

        /** ============== http 请求==================== **/
        Map<String, String> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("terminal_id", terminalId);
        params.put("data_type", "json");
        params.put("data_content", dataContent);
        String postString = HttpUtils.doPostByForm("https://api.xinyan-ai.com/product/credit/v1/unify", headers, params);
        XyRecord xyRecord = new XyRecord();
        xyRecord.setCompanyId(company.getId());
        xyRecord.setRequestParams(xmlOrJson);
        xyRecord.setUserId(userId);
        xyRecord.setType(XyRecordTypeEnum.NEW_RADAR.getId());
        xyRecord.setResult(postString);
        xyService.insertRecord(xyRecord);
        return new Result(true, "请求成功");
    }
}
