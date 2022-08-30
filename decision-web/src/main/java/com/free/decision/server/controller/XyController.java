package com.free.decision.server.controller;

import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.DecisionRadarParamsVO;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.XyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 行为雷达分析
 */
@RestController
@RequestMapping("/xy")
public class XyController {

    @Resource
    private CompanyService companyService;

    @Resource
    private XyService xyService;

    /**
     * 获取行为雷达报告
     *
     * @param decisionRadarParamsVO
     * @return
     */
    @PostMapping("getDecisionRadar")
    public Result getDecisionRadar(@RequestBody DecisionRadarParamsVO decisionRadarParamsVO) {
        Result result = companyService.checkCompanyApi(decisionRadarParamsVO.getApiKey(), decisionRadarParamsVO.getSign(), decisionRadarParamsVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        Company company = result.getData();
        if (StringUtils.isBlank(decisionRadarParamsVO.getIdName()) || decisionRadarParamsVO.getIdName().trim().length() > 50) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "姓名为空或格式不正确");
        }
        if (StringUtils.isBlank(decisionRadarParamsVO.getIdNo()) || decisionRadarParamsVO.getIdNo().trim().length() > 30) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "身份证号为空或格式不正确");
        }
        if (StringUtils.isBlank(decisionRadarParamsVO.getPhoneNo()) || !ReUtil.isMatch("^1\\d{10}$", decisionRadarParamsVO.getPhoneNo())) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "手机号为空或格式不正确");
        }
        if (StringUtils.isBlank(decisionRadarParamsVO.getBankcardNo()) || decisionRadarParamsVO.getBankcardNo().trim().length() > 30) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "银行卡号为空或格式不正确");
        }
        result = xyService.getRadarReport(decisionRadarParamsVO, company);
        if(result.isSuccess()){
            Map<String, Object> dataMap = JSON.parseObject(JSON.toJSONString(result.getData()), Map.class);
            dataMap.put("storeTime", dataMap.get("createTime"));
            dataMap.remove("createTime");
            dataMap.remove("code");
            dataMap.remove("userId");
            dataMap.remove("versions");
            dataMap.remove("id");
            return new Result(true, ResultCodeEnum.SUCCESS.getId(), "查询成功", dataMap);
        }
        return new Result(false, ResultCodeEnum.FAIL.getId(),"查询失败");
    }
}
