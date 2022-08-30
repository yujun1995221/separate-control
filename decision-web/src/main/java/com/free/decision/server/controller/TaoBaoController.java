package com.free.decision.server.controller;

import cn.hutool.core.util.ReUtil;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.TaoBaoParamsVO;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.TaoBaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 淘宝认证
 */
@RestController
@RequestMapping("/taobao")
public class TaoBaoController extends BaseController{

    @Resource
    private TaoBaoService taoBaoService;

    @Resource
    private CompanyService companyService;

    @RequestMapping("createOrder")
    public Result createOrder(@RequestBody TaoBaoParamsVO taoBaoParamsVO){
        Result result = companyService.checkCompanyApi(taoBaoParamsVO.getApiKey(), taoBaoParamsVO.getSign(), taoBaoParamsVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        if(StringUtils.isBlank(taoBaoParamsVO.getName()) || taoBaoParamsVO.getName().trim().length() > 50){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "姓名为空或格式不正确");
        }
        if(StringUtils.isBlank(taoBaoParamsVO.getIdNumber()) || taoBaoParamsVO.getIdNumber().trim().length() > 30 ){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "身份证号为空或格式不正确");
        }
        if(StringUtils.isBlank(taoBaoParamsVO.getPhone()) || !ReUtil.isMatch("1\\d{10}", taoBaoParamsVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "手机号为空或格式不正确");
        }
        if(StringUtils.isBlank(taoBaoParamsVO.getCallback())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "回调地址不能为空");
        }
        Company company = result.getData();
        return taoBaoService.createOrder(taoBaoParamsVO, company);
    }
}
