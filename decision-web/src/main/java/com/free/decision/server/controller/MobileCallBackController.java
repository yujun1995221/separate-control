package com.free.decision.server.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.enums.MobileOrderStatusEnum;
import com.free.decision.server.model.vo.XyCallBackParamsVO;
import com.free.decision.server.service.MobileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 运营商回调
 */
@RestController
@RequestMapping("mobile/callback")
public class MobileCallBackController {

    private static Logger logger = LoggerFactory.getLogger(MobileCallBackController.class);

    @Value("${decision.xy.apiUser}")
    private String xyApiUser;

    @Value("${decision.xy.apiKey}")
    private String xyApiKey;

    @Value("${mx.secret}")
    private String mxSecret;

    @Resource
    private MobileService mobileService;

    /**
     * 新颜运营商回调
     * @param xyCallBackParamsVO
     * @return
     */
    @RequestMapping("xinyan")
    public String xinyan(@RequestBody XyCallBackParamsVO xyCallBackParamsVO){
        //验签
        String apiEnc = SecureUtil.md5(xyApiUser+xyApiKey+xyCallBackParamsVO.getToken());
        logger.info("新颜运营商回调:{}", JSON.toJSONString(xyCallBackParamsVO));
        if(!apiEnc.equalsIgnoreCase(xyCallBackParamsVO.getApiEnc())){
            return "fail";
        }
        if(StringUtils.equals(xyCallBackParamsVO.getApiName(), "carrier")){
            //运营商
            if(StringUtils.equals(xyCallBackParamsVO.getType(), "report")){
                //运营商报告
                mobileService.updateStatus(MobileOrderStatusEnum.DOING.getId(), 2, xyCallBackParamsVO.getTaskId(), xyCallBackParamsVO.getToken());
            }else{
                //原始数据
                mobileService.updateStatus(MobileOrderStatusEnum.DOING.getId(), 1, xyCallBackParamsVO.getTaskId(), xyCallBackParamsVO.getToken());
            }
        }
        return "success";
    }

    /**
     * 魔蝎运营商回调
     * @param request
     * @return
     */
    @RequestMapping("mx")
    public String mx(HttpServletRequest request, HttpServletResponse response){
        String body = "";
        try {
            body = IoUtil.read(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            logger.error("read mx mobile data error!", e);
        }
        logger.info("魔蝎运营商回调,body:{}", body);
        if(StrUtil.isBlank(body)){
            return "fail";
        }
        //验签
        HMac mac = new HMac(HmacAlgorithm.HmacSHA256, mxSecret.getBytes());
        String newSignature = Base64.encode(mac.digest(body.getBytes()));
        String signature = request.getHeader("X-Moxie-Signature");
        if(!StrUtil.equalsIgnoreCase(newSignature, signature)){
            logger.warn("魔蝎回调验签不通过，body:{}, signature:{}", body, signature);
            return "fail";
        }
        logger.info("header X-Moxie-Type:{},X-Moxie-Event:{}", request.getHeader("X-Moxie-Type"), request.getHeader("X-Moxie-Event"));
        if(!StrUtil.equalsIgnoreCase("carrier", request.getHeader("X-Moxie-Type"))){
            logger.warn("魔蝎回调失败，X-Moxie-Type:{}",request.getHeader("X-Moxie-Type"));
            return "fail";
        }
        JSONObject jsonObj = JSON.parseObject(body);
        if(StrUtil.equalsIgnoreCase("report", request.getHeader("X-Moxie-Event"))){
            //报告数据
            if(!jsonObj.getBooleanValue("result")){
                response.setStatus(201);
                logger.warn("魔蝎返回失败,body:{}", body);
                return "fail";
            }
            mobileService.updateStatus(MobileOrderStatusEnum.DOING.getId(), 2, jsonObj.getString("user_id"), jsonObj.getString("task_id"));
        }else if(StrUtil.equalsIgnoreCase("bill", request.getHeader("X-Moxie-Event"))){
            //账单数据
            mobileService.updateStatus(MobileOrderStatusEnum.DOING.getId(), 1, jsonObj.getString("user_id"), jsonObj.getString("task_id"));
        }
        response.setStatus(201);
        return "success";
    }
}
