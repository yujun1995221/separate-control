package com.free.decision.server.controller;

import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.MobileParamsVO;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.MobileService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 运营商认证
 */
@RestController
@RequestMapping("/mobile")
public class MobileController extends BaseController{

    @Resource
    private MobileService mobileService;

    @Resource
    private CompanyService companyService;

    @RequestMapping("createOrder")
    public Result createOrder(@RequestBody MobileParamsVO mobileParamsVO){
        return new Result(false, ResultCodeEnum.FAIL.getId(), "请求失败");
      /*  Result result = companyService.checkCompanyApi(mobileParamsVO.getApiKey(), mobileParamsVO.getSign(), mobileParamsVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        if(StringUtils.isBlank(mobileParamsVO.getName()) || mobileParamsVO.getName().trim().length() > 50){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "姓名为空或格式不正确");
        }
        if(StringUtils.isBlank(mobileParamsVO.getIdNumber()) || mobileParamsVO.getIdNumber().trim().length() > 30 ){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "身份证号为空或格式不正确");
        }
        if(StringUtils.isBlank(mobileParamsVO.getPhone()) || !ReUtil.isMatch("1\\d{10}", mobileParamsVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "手机号为空或格式不正确");
        }
        if(StringUtils.isBlank(mobileParamsVO.getCallback())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "回调地址不能为空");
        }
        Company company = result.getData();
        return mobileService.createOrder(mobileParamsVO, company);*/
    }

    /**
     * 运营商从缓存认证
     * @param mobileParamsVO
     * @return
     */
    @RequestMapping("cacheData")
    public Result cacheData(@RequestBody MobileParamsVO mobileParamsVO){
        return new Result(false, ResultCodeEnum.FAIL.getId(), "请求失败");
      /*  Result result = companyService.checkCompanyApi(mobileParamsVO.getApiKey(), mobileParamsVO.getSign(), mobileParamsVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        if(StringUtils.isBlank(mobileParamsVO.getName()) || mobileParamsVO.getName().trim().length() > 50){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "姓名为空或格式不正确");
        }
        if(StringUtils.isBlank(mobileParamsVO.getIdNumber()) || mobileParamsVO.getIdNumber().trim().length() > 30 ){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "身份证号为空或格式不正确");
        }
        if(StringUtils.isBlank(mobileParamsVO.getPhone()) || !ReUtil.isMatch("1\\d{10}", mobileParamsVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "手机号为空或格式不正确");
        }
        Company company = result.getData();
        return mobileService.cacheData(mobileParamsVO, company);*/
    }
}
