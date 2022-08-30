package com.free.decision.server.apis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.enums.UdRecordTypeEnum;
import com.free.decision.server.enums.UdReturnTypeEnum;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.UdRecord;
import com.free.decision.server.model.vo.UdInterfaceParamsVO;
import com.free.decision.server.model.vo.UdResultVO;
import com.free.decision.server.service.UdService;
import com.free.decision.server.utils.HttpKit;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xingyf
 */
@Service
public class UdApi {
    private static Logger logger = LoggerFactory.getLogger(UdApi.class);

    @Value("${ud.pub.key}")
    private String pubKey;

    @Value("${ud.pub.secret}")
    private String pubSecret;

    @Resource
    private CacheChannel cacheChannel;

    @Resource
    private UdService udService;

    public Result getCreditReport(UdInterfaceParamsVO udInterfaceParamsVO, long companyId, long userId){
        String idNo = udInterfaceParamsVO.getIdNo();
        String mobile = udInterfaceParamsVO.getMobile();
        String orderId = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+RandomStringUtils.randomNumeric(10);
        Map<String, String> params = new HashMap<>();
        params.put("id_no", idNo);
        logger.info("即将开始执行UdApi接口 ===> companyId_"+companyId+"_idNo_"+idNo);

        // 获取缓存数据
        Long id = (Long)cacheChannel.get("decision.ud.report.id", "udRecord_id_"+companyId+"_"+idNo+"_"+mobile).getValue();
        Map<String, String> resultMap = new HashMap<>();
        String ret;
        if(id != null && id > 0){
            // 根据ID从数据库中读取Data
            ret = udService.getUdRecordResultById(id);
            if(StringUtils.isNotBlank(ret)){
                resultMap.put("orderId",orderId);
                resultMap.put("ret",ret);
                logger.info("结束执行UdApi接口,从缓存中读取成功并将接口请求记录插入成功 ===> companyId_"+companyId+"_idNo_"+idNo);
                return new Result(true,UdReturnTypeEnum.SUCCESS.getCode(),UdReturnTypeEnum.SUCCESS.getResultDesc(),resultMap);
            }
        }
        UdResultVO udResultVO;
        try {
            String signature = md5(JSON.toJSONString(params).concat("|").concat(pubSecret));
            String url = "https://api4.udcredit.com/dsp-front/4.1/dsp-front/default/pubkey/"+pubKey+"/product_code/Y1001005/out_order_id/"+orderId+"/signature/"+signature;
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            ret = HttpKit.post(url, null, JSON.toJSONString(params), headers);
            logger.info("有盾反欺诈报告返回:{}", ret);
            JSONObject jsonObject= JSON.parseObject(ret);
            String header = jsonObject.getString("header");
            udResultVO = JSON.parseObject(header, UdResultVO.class);
            // 用户调用接口记录
            UdRecord udRecord = new UdRecord();
            udRecord.setRequestParams(JSON.toJSONString(udInterfaceParamsVO));
            udRecord.setCompanyId(companyId);
            udRecord.setType(UdRecordTypeEnum.YUNHUIYAN.getId());
            udRecord.setResult(ret);
            udRecord.setUserId(userId);
            // 插入用户调用接口记录
            udService.insertUdRecord(udRecord);
            Long udRecordId = udRecord.getId();
            // 请求成功
            if(UdReturnTypeEnum.SUCCESS.getResultCode().equals(udResultVO.getRetCode())){
                resultMap.put("orderId",orderId);
                resultMap.put("ret",ret);
                // 将请求成功的数据进行缓存
                cacheChannel.set("decision.ud.report.id", "udRecord_id_"+companyId+"_"+idNo+"_"+mobile, udRecordId);
                logger.info("结束执行UdApi接口，从有盾反欺诈接口请求成功并将接口请求记录插入成功 ===> companyId_"+companyId+"_idNo_"+idNo);
                return new Result(true,UdReturnTypeEnum.SUCCESS.getCode(),UdReturnTypeEnum.SUCCESS.getResultDesc(),resultMap);
            }else if(UdReturnTypeEnum.REQUESTED_MESSAGE_FORMAT_ERROR.getResultCode().equals(udResultVO.getRetCode())){
                return new Result(false,UdReturnTypeEnum.REQUESTED_MESSAGE_FORMAT_ERROR.getCode(),UdReturnTypeEnum.REQUESTED_MESSAGE_FORMAT_ERROR.getResultDesc());
            }else if(UdReturnTypeEnum.THE_PICTURE_IS_NOT_GENERATED.getResultCode().equals(udResultVO.getRetCode())){
                return new Result(false,UdReturnTypeEnum.THE_PICTURE_IS_NOT_GENERATED.getCode(),UdReturnTypeEnum.THE_PICTURE_IS_NOT_GENERATED.getResultDesc());
            }else if(UdReturnTypeEnum.NO_USER_INFO.getResultCode().equals(udResultVO.getRetCode())){
                return new Result(false,UdReturnTypeEnum.NO_USER_INFO.getCode(),UdReturnTypeEnum.NO_USER_INFO.getResultDesc());
            }
        } catch (Exception e) {
            logger.error("请求有盾反欺诈接口异常", e);
            return new Result(false,UdReturnTypeEnum.ERROR.getCode(),UdReturnTypeEnum.ERROR.getResultDesc());
        }
        logger.error("请求有盾反欺诈接口失败，ret_code:  "+udResultVO.getRetCode()+"，ret_msg:"+udResultVO.getRetMsg()+"，请求参数：companyId_"+companyId+"_idNo_"+idNo);
        return new Result(false,UdReturnTypeEnum.ERROR.getCode(),UdReturnTypeEnum.ERROR.getResultDesc());
    }

    /**
     * 云相接口
     * @param companyId
     * @param userId
     * @return
     */
    public Result getYunReport(String idNo, String mobile, String name, long companyId, long userId){
        String orderId = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+RandomStringUtils.randomNumeric(10);
        Map<String, String> params = new HashMap<>();
        params.put("id_no", idNo);
        params.put("id_name", name);
        params.put("mobile", mobile);
        logger.info("即将开始执行UdApi接口 ===> companyId_"+companyId+"_idNo_"+idNo);

        // 获取缓存数据
        Double score = (Double) cacheChannel.get("decision.ud.cloud.id", "udRecord_id_"+idNo+"_"+mobile).getValue();
        String ret;
        if(score != null){
            logger.info("结束执行UdApi接口,从缓存中读取成功 ===> companyId_"+companyId+"_idNo_"+idNo);
            return new Result(true, UdReturnTypeEnum.SUCCESS.getCode(),UdReturnTypeEnum.SUCCESS.getResultDesc(), score);
        }
        UdResultVO udResultVO;
        try {
            String signature = md5(JSON.toJSONString(params).concat("|").concat(pubSecret));
            String url = "https://api4.udcredit.com/dsp-front/4.1/dsp-front/default/pubkey/"+pubKey+"/product_code/Y1001006/out_order_id/"+orderId+"/signature/"+signature;
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            ret = HttpKit.post(url, null, JSON.toJSONString(params), headers);
            //ret = "{\"body\":{\"comp_score\":\"528\",\"consume\":{\"consume_score\":\"437\",\"ud_d_0001\":\"\",\"ud_d_0002\":\"\",\"ud_d_0003\":\"\",\"ud_d_0004\":\"\",\"ud_d_0005\":\"\",\"ud_d_0006\":\"\",\"ud_d_0007\":\"(50-100]\",\"ud_d_0008\":\"(50-100]\",\"ud_d_0009\":\"(0.2-0.3]\",\"ud_d_0010\":\"[0-2]\",\"ud_d_0011\":\"[0-2]\"},\"loan\":{\"loan_score\":\"405\",\"ud_b_0001\":\"0\",\"ud_b_0002\":\"0\",\"ud_b_0003\":\"[1-3]\",\"ud_b_0004\":\"[4-6]\",\"ud_b_0005\":\"[21-30]\",\"ud_b_0006\":\"0\",\"ud_b_0007\":\"0\",\"ud_b_0008\":\"[40000-60000]\",\"ud_b_0009\":\"[51-70]\",\"ud_b_0010\":\"0\"},\"request\":{\"request_score\":\"142\",\"ud_c_0001\":\"(1.2-1.4]\",\"ud_c_0002\":\"\",\"ud_c_0003\":\"\",\"ud_c_0004\":\"(1.2-1.4]\",\"ud_c_0005\":\"\",\"ud_c_0006\":\"[181-360]\",\"ud_c_0007\":\"[61-90]\",\"ud_c_0008\":\"(0-0.2]\",\"ud_c_0009\":\"\",\"ud_c_0010\":\"0\"},\"social\":{\"social_score\":\"650\",\"ud_e_0008\":\"[24-36]\",\"ud_e_0009\":\"1200及以上\",\"ud_e_0006\":\"[25-28]\",\"ud_e_0007\":\"[38-41]\",\"ud_e_0004\":\"08\",\"ud_e_0005\":\"[61-90]\",\"ud_e_0002\":\"02\",\"ud_e_0003\":\"08\",\"ud_e_0001\":\"02\"},\"ud_order_no\":\"392399417749602304\"},\"header\":{\"req_time\":\"2018-11-23 10:30:09\",\"resp_time\":\"2018-11-23 10:30:10\",\"ret_code\":\"000000\",\"ret_msg\":\"操作成功\",\"version\":\"4.1\"},\"sign\":\"7C4A0E6C903D30C1DE1A718F570721E6\"}";
            logger.info("有盾云相报告返回:{}", ret);
            JSONObject jsonObject= JSON.parseObject(ret);
            String header = jsonObject.getString("header");
            udResultVO = JSON.parseObject(header, UdResultVO.class);
            // 用户调用接口记录
            UdRecord udRecord = new UdRecord();
            udRecord.setRequestParams(JSON.toJSONString(params));
            udRecord.setCompanyId(companyId);
            udRecord.setType(UdRecordTypeEnum.YUNXIANG.getId());
            udRecord.setResult(ret);
            udRecord.setUserId(userId);
            // 插入用户调用接口记录
            udService.insertUdRecord(udRecord);
            // 请求成功
            if(UdReturnTypeEnum.SUCCESS.getResultCode().equals(udResultVO.getRetCode())){
                score = Math.floor(NumberUtils.toDouble(jsonObject.getJSONObject("body").getString("comp_score")));
                // 将请求成功的数据进行缓存
                cacheChannel.set("decision.ud.cloud.id", "udRecord_id_"+idNo+"_"+mobile, score);
                logger.info("结束执行UdApi接口，从有盾云相接口请求成功并将接口请求记录插入成功 ===> companyId_"+companyId+"_idNo_"+idNo);
                return new Result(true,UdReturnTypeEnum.SUCCESS.getCode(),UdReturnTypeEnum.SUCCESS.getResultDesc(),  score);
            }else if(UdReturnTypeEnum.REQUESTED_MESSAGE_FORMAT_ERROR.getResultCode().equals(udResultVO.getRetCode())){
                return new Result(false,UdReturnTypeEnum.REQUESTED_MESSAGE_FORMAT_ERROR.getCode(),UdReturnTypeEnum.REQUESTED_MESSAGE_FORMAT_ERROR.getResultDesc());
            }else if(UdReturnTypeEnum.THE_PICTURE_IS_NOT_GENERATED.getResultCode().equals(udResultVO.getRetCode())){
                return new Result(false,UdReturnTypeEnum.THE_PICTURE_IS_NOT_GENERATED.getCode(),UdReturnTypeEnum.THE_PICTURE_IS_NOT_GENERATED.getResultDesc());
            }else if(UdReturnTypeEnum.NO_USER_INFO.getResultCode().equals(udResultVO.getRetCode())){
                return new Result(false,UdReturnTypeEnum.NO_USER_INFO.getCode(),UdReturnTypeEnum.NO_USER_INFO.getResultDesc());
            }
        } catch (Exception e) {
            logger.error("请求有盾云相接口异常",e);
            return new Result(false,UdReturnTypeEnum.ERROR.getCode(),UdReturnTypeEnum.ERROR.getResultDesc());
        }
        logger.error("请求有盾云相接口失败，ret_code:  "+udResultVO.getRetCode()+"，ret_msg:"+udResultVO.getRetMsg()+"，请求参数：companyId_"+companyId+"_idNo_"+idNo);
        return new Result(false,UdReturnTypeEnum.ERROR.getCode(),UdReturnTypeEnum.ERROR.getResultDesc());
    }

    /**
     * 有盾银行卡四要素验证
     * @param idName 身份证姓名
     * @param idNo 身份证号码
     * @param bankCardNo 银行卡号
     * @param mobile 手机号码
     * @return
     */
    public Result udBankVerify(String idName, String idNo, String bankCardNo, String mobile, long companyId, long userId){
        //参数
        Map<String, String> params = new HashMap<>();
        params.put("id_name", idName);
        params.put("id_no", idNo);
        params.put("bank_card_no", bankCardNo);
        params.put("mobile", mobile);
        params.put("req_type", "02");
        String signature;
        try {
            signature = md5(JSON.toJSONString(params).concat("|").concat(pubSecret));
        } catch (NoSuchAlgorithmException e) {
            logger.error("有盾接口md5加密出错", e);
            return new Result(false, "验证失败", "参数加密错误");
        }
        String outOrderId = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssS")+RandomStringUtils.randomNumeric(10);
        String url = "https://api4.udcredit.com/dsp-front/4.1/dsp-front/default/pubkey/".concat(pubKey)
                .concat("/product_code/O1001S0401/out_order_id/").concat(outOrderId).concat("/signature/").concat(signature);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        String ret = HttpKit.post(url, null, JSON.toJSONString(params), headers);
        logger.info("有盾银行卡鉴权返回:{}", ret);
        //保存调用记录
        UdRecord udRecord = new UdRecord();
        udRecord.setCompanyId(companyId);
        udRecord.setUserId(userId);
        udRecord.setRequestParams(JSON.toJSONString(params));
        udRecord.setResult(ret);
        udRecord.setType(UdRecordTypeEnum.BANK_AUTH.getId());
        udService.insertUdRecord(udRecord);
        JSONObject object = JSON.parseObject(ret);
        JSONObject headersObj = object.getJSONObject("header");
        String retCode = headersObj.getString("ret_code");
        if(!StringUtils.equalsIgnoreCase("000000", retCode)){
            return new Result(false, "验证失败", headersObj.getString("ret_msg"));
        }
        JSONObject body = object.getJSONObject("body");
        if(body != null){
            String bankName = body.getString("bank_name");
            if(!StringUtils.equalsIgnoreCase(body.getString("status"), "1")){
                return new Result(false, body.getString("message"));
            }
            if(StringUtils.isBlank(bankName)){
                return new Result(false, "验证不通过");
            }
            return new Result(true, "验证通过", ret);
        }else{
            return new Result(false, "验证不通过");
        }
    }

    private String md5(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] ch) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < ch.length; i++)
            ret.append(byteToHex(ch[i]));
        return ret.toString();
    }
    /**
     * 字节转换为16进制字符串
     */
    private String byteToHex(byte ch) {
        String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
        return str[ch >> 4 & 0xF] + str[ch & 0xF];
    }

}
