package com.free.decision.server.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyResultRequest;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyResultResponse;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyTokenRequest;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyTokenResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.RealNameChannelEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.AliFace;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.RealNameParamVO;
import com.free.decision.server.model.vo.ReportCommonParamsVO;
import com.free.decision.server.service.AliService;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.ConsumeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 实名认证
 */
@RestController
@RequestMapping("/verify")
public class VerifiedController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(VerifiedController.class);

    @Resource
    private CompanyService companyService;

    @Resource
    private ConsumeService consumeService;

    @Value("${realName.channel}")
    private Integer realNameChannel;

    @Value("${aliyun.oss.accessKeyId}")
    private String aliAccessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Resource
    private AliService aliService;

    @Value("${ali.face.biz.type}")
    private String aliFaceBizId;

    /*@PostMapping("/realName")
    public Result getRealName(@RequestBody RealNameParamVO realNameParamVO) {
        Result result = companyService.checkCompanyApi(realNameParamVO.getApiKey(), realNameParamVO.getSign(), realNameParamVO.getTimestamp());
        logger.info("实名认证，{}",realNameParamVO.getPhone());
        if (!result.isSuccess()) {
            return result;
        }
        logger.info("返回数据成功,{}",JSON.toJSONString(result));
        Company company = result.getData();
        if (StringUtils.isBlank(realNameParamVO.getUdResult())) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "有盾json为空");
        }
        if(StringUtils.isBlank(realNameParamVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "手机号不能为空");
        }
        if(!ReUtil.isMatch("^1\\d{10}$", realNameParamVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "手机号格式不正确");
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(realNameParamVO.getUdResult());
        } catch (Exception e) {
            logger.error("有盾返回json格式不正确", e);
            return new Result(false, ResultCodeEnum.EXCEPTION.getId(), "实名认证失败");
        }
        if (!StringUtils.equalsIgnoreCase(jsonObject.getString("ret_code"), "000000")) {
            logger.warn("有盾实名验证不通过，code:{},msg:{}", jsonObject.getString("ret_code"), jsonObject.getString("ret_msg"));
            return new Result(false, ResultCodeEnum.FAIL.getId(), "实名认证失败", jsonObject.getString("ret_msg"));
        }
        return udService.getRealName(realNameParamVO.getUdResult(),realNameParamVO.getPhone(),jsonObject.getString("id_no"),jsonObject.getString("id_name"),company);
    }*/

    /**
     * 校验实名认证权限
     * @param reportCommonParamsVO
     * @return
     */
    @RequestMapping("checkRealName")
    public Result checkRealName(@RequestBody ReportCommonParamsVO reportCommonParamsVO){
        Result result = companyService.checkCompanyApi(reportCommonParamsVO.getApiKey(), reportCommonParamsVO.getSign(), reportCommonParamsVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        Company company = result.getData();
        if(realNameChannel == RealNameChannelEnum.ALIYUN.getId()){
            DefaultProfile profile = DefaultProfile.getProfile(
                    "cn-hangzhou",          // 您的可用区ID
                    aliAccessKeyId,      // 您的Access Key ID
                    accessKeySecret); // 您的Access Key Secret
            try {
                DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Cloudauth", "cloudauth.aliyuncs.com");
                IAcsClient client = new DefaultAcsClient(profile);
                DescribeVerifyTokenRequest request = new DescribeVerifyTokenRequest();
                String bizId = DateUtil.format(new Date(), "yyyyMMddHHmmssS")+ RandomUtil.randomNumbers(8);
                request.setBizId(bizId);
                request.setBizType(aliFaceBizId);
                DescribeVerifyTokenResponse response = client.getAcsResponse(request);
                String verifyToken = response.getVerifyToken();
                //保存记录
                AliFace aliFace = new AliFace();
                aliFace.setCompanyId(company.getId());
                aliFace.setBizId(bizId);
                aliFace.setVerifyToken(verifyToken);
                aliService.addAliFace(aliFace);
                return new Result(true, ResultCodeEnum.SUCCESS.getId(), "success", Dict.create().set("bizId", bizId).set("verifyToken", verifyToken));
            } catch (ClientException e) {
                logger.error("阿里云实人认证初始化异常", e);
                return new Result(false, ResultCodeEnum.FAIL.getId(), "初始化失败");
            }
        }
        return new Result(true, ResultCodeEnum.SUCCESS.getId(), "success");
    }

    /**
     * 实名认证
     * @param realNameParamVO
     * @return
     */
    @PostMapping("/realName")
    public Result getRealName(@RequestBody RealNameParamVO realNameParamVO) {
        Result result = companyService.checkCompanyApi(realNameParamVO.getApiKey(), realNameParamVO.getSign(), realNameParamVO.getTimestamp());
        logger.info("实名认证，{}",realNameParamVO.getPhone());
        if (!result.isSuccess()) {
            return result;
        }
        Company company = result.getData();
        logger.info("返回数据成功,{}",JSON.toJSONString(result));
        if(realNameChannel == RealNameChannelEnum.ALIYUN.getId()){
            if (StringUtils.isBlank(realNameParamVO.getResult())) {
                logger.warn("阿里云实名认证返回result为空");
                return new Result(false, ResultCodeEnum.FAIL.getId(), "认证失败");
            }
            DefaultProfile profile = DefaultProfile.getProfile(
                    "cn-hangzhou",          // 您的可用区ID
                    aliAccessKeyId,      // 您的Access Key ID
                    accessKeySecret); // 您的Access Key Secret
            try {
                String bizId = realNameParamVO.getResult();
                DescribeVerifyResultRequest verifyResultRequest = new DescribeVerifyResultRequest();
                verifyResultRequest.setBizId(bizId);
                verifyResultRequest.setBizType(aliFaceBizId);
                IAcsClient client = new DefaultAcsClient(profile);
                DescribeVerifyResultResponse verifyResultResponse = client.getAcsResponse(verifyResultRequest);
                //插入消费记录
                consumeService.consume(company.getId(), company.getRealNamePrice(), ReportTypeEnum.REAL_NAME, ConsumeTypeEnum.CONSUME, "实名认证-" + realNameParamVO.getPhone());
                if(verifyResultResponse.getVerifyStatus() == null){
                    return new Result(false, ResultCodeEnum.FAIL.getId(), "认证不通过");
                }
                String verifyResult = JSON.toJSONString(verifyResultResponse);
                aliService.updateFaceByBizId(bizId, verifyResult);

                if(verifyResultResponse.getVerifyStatus() != 1){
                    return new Result(false, ResultCodeEnum.FAIL.getId(), "认证不通过");
                }
                return new Result(true, ResultCodeEnum.SUCCESS.getId(), "认证成功",
                        Dict.create().set("channel", RealNameChannelEnum.ALIYUN.getId()).set("data", verifyResultResponse));
            } catch (ClientException e) {
                logger.error("阿里云实名认证异常", e);
                return new Result(false, ResultCodeEnum.FAIL.getId(), "认证失败");
            }
        }
        return new Result(false, ResultCodeEnum.FAIL.getId(), "认证失败");
    }

}
