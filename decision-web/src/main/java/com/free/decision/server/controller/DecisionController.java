package com.free.decision.server.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.DecisionParamsVO;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.DecisionService;
import com.free.decision.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 决策
 * @author Xingyf
 */
@RestController
@RequestMapping("/decision")
public class DecisionController {

    private static Logger logger = LoggerFactory.getLogger(DecisionController.class);

    @Resource
    private DecisionService decisionService;

    @Resource
    private CompanyService companyService;

    @Resource
    private UserService userService;

    /**
     * 全自动决策接口
     * @param decisionParamsVO
     * @return
     */
    @PostMapping("decision")
    public Result decision(@RequestBody DecisionParamsVO decisionParamsVO){
        logger.info("请求决策，phone:{}", decisionParamsVO.getPhone());
        Result result = companyService.checkCompanyApi(decisionParamsVO.getApiKey(), decisionParamsVO.getSign(), decisionParamsVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        if(StringUtils.isBlank(decisionParamsVO.getName()) || decisionParamsVO.getName().trim().length() > 50){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "姓名为空或格式不正确");
        }
        if(StringUtils.isBlank(decisionParamsVO.getIdNumber()) || decisionParamsVO.getIdNumber().trim().length() > 30 ){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "身份证号为空或格式不正确");
        }
        if(StringUtils.isBlank(decisionParamsVO.getPhone()) || !ReUtil.isMatch("1\\d{10}", decisionParamsVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "手机号为空或格式不正确");
        }
        if(decisionParamsVO.getMobileChannel() == null){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "运营商渠道不能为空");
        }
        if(decisionParamsVO.getFrontDecisionFlag() == null){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "是否需要前置决策参数不能为空");
        }
        if(StrUtil.isBlank(decisionParamsVO.getCallback())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "回调地址不能为空");
        }
        Company company = result.getData();
       /* Long userId = userService.loadUserIdByMobileAndIdNumber(company.getId(), decisionParamsVO.getPhone(), decisionParamsVO.getIdNumber());
        if(userId == null){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }*/
        long userId = userService.getUserIdByMobileAndIdNumber(company.getId(), decisionParamsVO.getPhone(), decisionParamsVO.getIdNumber(), decisionParamsVO.getName());
        decisionParamsVO.setUserId(userId);
        //生成全自动决策报告
        return decisionService.decision(decisionParamsVO, company);
    }
}
