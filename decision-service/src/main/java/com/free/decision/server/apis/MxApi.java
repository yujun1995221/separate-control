package com.free.decision.server.apis;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.enums.MxRecordTypeEnum;
import com.free.decision.server.model.MobileOrder;
import com.free.decision.server.model.MxRecord;
import com.free.decision.server.model.Result;
import com.free.decision.server.service.MobileService;
import com.free.decision.server.service.MxService;
import com.free.decision.server.utils.AliOssUtils;
import com.free.decision.server.utils.HttpKit;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 魔蝎api
 */
@Component
public class MxApi {

    private static Logger logger = LoggerFactory.getLogger(MxApi.class);

    @Value("${mx.appid}")
    private String appId;

    @Value("${mx.token}")
    private String mxToken;

    @Value("${mx.public.key}")
    private String publicKey;

    @Value("${mx.private.key}")
    private String privateKey;

    @Value("${mx.token}")
    private String token;

    @Value("${oss.report.bucket}")
    private String reportBucket;

    @Resource
    MxService mxService;

    @Resource
    AliOssUtils aliOssUtils;

    @Resource
    MobileService mobileService;

    /**
     * 魔杖分-标准版
     */
    public String mozhangStandard(String name, String mobile, String idNumber, long companyId, long userId){
        try {
            MxRecord mxRecord = mxService.loadByPhoneAndType(mobile, MxRecordTypeEnum.MO_ZHANG_STANDARD.getId());
            if(mxRecord != null && new DateTime(mxRecord.getCreateTime()).plusDays(3).isAfterNow()){
                //有缓存
                JSONObject retObj = JSON.parseObject(mxRecord.getResult());
                if(retObj.getBoolean("success") != null && retObj.getBooleanValue("success")
                        && "0000".equalsIgnoreCase(retObj.getString("code"))){
                    return retObj.getString("data");
                }
            }
            String orderNo = DateUtil.format(new Date(), "yyyyMMddHHmmssS")+ RandomUtil.randomNumbers(10);
            Map<String, String> params = new HashMap<>();
            params.put("app_id", appId);
            params.put("format", "JSON");
            params.put("method", "moxie.api.risk.magicwand3.standard");
            params.put("version", "1.0");
            params.put("sign_type", "RSA");
            params.put("biz_content", StrUtil.format("{\"name\":\"{}\",\"idcard\":\"{}\",\"mobile\":\"{}\",\"user_id\":\"{}\"}", name, idNumber, mobile, orderNo));
            params.put("timestamp", String.valueOf(new Date().getTime()));
            String sortStr = getSortMapParamStr(params);
            Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA, privateKey, publicKey);
            String signStr = Base64.encode(sign.sign(sortStr.getBytes()));
            params.put("sign", signStr);
            String ret = HttpKit.get("https://api.51datakey.com/risk-gateway/api/gateway", params);
            //保存请求记录
            mxRecord = new MxRecord();
            mxRecord.setCompanyId(companyId);
            mxRecord.setUserId(userId);
            mxRecord.setIdNumber(idNumber);
            mxRecord.setPhone(mobile);
            mxRecord.setOrderNo(orderNo);
            mxRecord.setRequestParams(JSON.toJSONString(params));
            mxRecord.setResult(ret);
            mxRecord.setType(MxRecordTypeEnum.MO_ZHANG_STANDARD.getId());
            mxService.addRecord(mxRecord);
            JSONObject retObj = JSON.parseObject(ret);
            if(retObj.getBoolean("success") != null && retObj.getBooleanValue("success")
                    && "0000".equalsIgnoreCase(retObj.getString("code"))){
                return retObj.getString("data");
            }
        } catch (Exception e) {
            logger.error("魔蝎魔杖分异常", e);
        }
        return null;
    }

    /**
     * 佩琪分
     * @param name
     * @param mobile
     * @param idNumber
     * @param companyId
     * @param userId
     * @return
     */
    public Result peiQiFen(String name, String mobile, String idNumber, long companyId, long userId){
        try {
            MxRecord mxRecord = mxService.loadByPhoneAndType(mobile, MxRecordTypeEnum.PEI_QI_PEN.getId());
            if(mxRecord != null && new DateTime(mxRecord.getCreateTime()).plusDays(3).isAfterNow()){
                //有缓存
                JSONObject retObj = JSON.parseObject(mxRecord.getResult());
                if(retObj.getBoolean("success") != null && retObj.getBooleanValue("success")
                        && "0000".equalsIgnoreCase(retObj.getString("code"))){
                    JSONObject data = retObj.getJSONObject("data");
                    if(data.getIntValue("returnCode") == 0){
                        return new Result(true, "success", Dict.create().set("highRiskHit", data.getString("high_risk_hit")).set("score", Math.floor(NumberUtils.toDouble(data.getString("score")))));
                    }
                }
            }
            String orderNo = DateUtil.format(new Date(), "yyyyMMddHHmmssS")+ RandomUtil.randomNumbers(10);
            Map<String, String> params = new HashMap<>();
            params.put("app_id", appId);
            params.put("format", "JSON");
            params.put("method", "moxie.api.risk.magicscore.peppa");
            params.put("version", "3.2");
            params.put("sign_type", "RSA");
            params.put("biz_content", StrUtil.format("{\"name\":\"{}\",\"idcard\":\"{}\",\"mobile\":\"{}\",\"apply_time\":\"{}\",\"product_no\":\"{}\"}", name, idNumber, mobile, DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "PEPPA_MODEL_V3.2"));
            params.put("timestamp", String.valueOf(new Date().getTime()));
            String sortStr = getSortMapParamStr(params);
            Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA, privateKey, publicKey);
            String signStr = Base64.encode(sign.sign(sortStr.getBytes()));
            params.put("sign", signStr);
            String ret = HttpKit.get("https://api.51datakey.com/risk-gateway/api/gateway", params);
            //保存请求记录
            mxRecord = new MxRecord();
            mxRecord.setCompanyId(companyId);
            mxRecord.setUserId(userId);
            mxRecord.setIdNumber(idNumber);
            mxRecord.setPhone(mobile);
            mxRecord.setOrderNo(orderNo);
            mxRecord.setRequestParams(JSON.toJSONString(params));
            mxRecord.setResult(ret);
            mxRecord.setType(MxRecordTypeEnum.PEI_QI_PEN.getId());
            mxService.addRecord(mxRecord);
            JSONObject retObj = JSON.parseObject(ret);
            if(retObj.getBoolean("success") != null && retObj.getBooleanValue("success")
                    && "0000".equalsIgnoreCase(retObj.getString("code"))){
                JSONObject data = retObj.getJSONObject("data");
                if(data.getIntValue("returnCode") == 0){
                    return new Result(true, "success", Dict.create().set("highRiskHit", data.getString("high_risk_hit")).set("score", Math.floor(NumberUtils.toDouble(data.getString("score")))));
                }
            }
        } catch (Exception e) {
            logger.error("魔蝎佩琪分异常", e);
        }
        return new Result(false, "fail");
    }

    private static String getSortMapParamStr(Map<String, String> data) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        for (String key : data.keySet()) {
            if ("sign".equals(key)) {
                continue;
            }
            if (data.get(key)!=null && data.get(key).trim()!="") {
                map.put(key, data.get(key));
            }
        }
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder str = new StringBuilder();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = map.get(key);
            str.append(key).append("=").append(value).append("&");
        }
        if (str.length() > 0) {
            return str.substring(0, str.length() - 1);
        }
        return "";
    }

    /**
     * 查询数据
     * @param token
     * @param type 1：淘宝 2：运营商
     * @return
     */
    public String getData(String token, int type, String orderNo, String phone){
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "/");
        headers.put("Accept-Encoding", "none");
        headers.put("Authorization", "token "+mxToken);
        Map<String, String> params = new HashMap<>();
        params.put("task_id", token);
        try {
            String ret = HttpKit.get("https://api.51datakey.com/carrier/v3/mobiles/"+phone+"/mxdata-ex", params, headers);
            if(type == 2){
                String fileName = "report/mobile/mx_mobile_original_" + DateUtil.format(new Date(), "yyyyMMddHHmmssS")+"_"+token+".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, ret);
                mobileService.updateResult(1, fileName, orderNo);
            }
            return ret;
        } catch (Exception e) {
            logger.error("查询魔蝎数据异常", e);
        }
        return "";
    }

    /**
     * 查询报告数据
     * @param token
     * @param type 1：淘宝 2：运营商
     * @return
     */
    public String getReportData(String token, int type, String orderNo, String phone, String idNumber){
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token "+mxToken);
        Map<String, String> params = new HashMap<>();
        params.put("task_id", token);
        params.put("mobile", phone);
        params.put("idcard", idNumber);
        MobileOrder mobileOrder = mobileService.loadByOrderNo(orderNo);
        String emergContacts = mobileOrder.getEmergContacts();
        if(StrUtil.isNotBlank(emergContacts)){
            params.put("contact", emergContacts);
        }
        try {
            String ret = HttpKit.getGzip("https://api.51datakey.com/carrier/v3/mobiles/"+phone+"/mxreport", params, headers);
            if(type == 2){
                String fileName = "report/mobile/mx_mobile_report_" + DateUtil.format(new Date(), "yyyyMMddHHmmssS")+"_"+token+".txt";
                aliOssUtils.uploadReport(reportBucket, fileName, ret);
                mobileService.updateResult(2, fileName, orderNo);
            }
            return ret;
        } catch (Exception e) {
            logger.error("查询魔蝎数据异常", e);
        }
        return "";
    }
}
