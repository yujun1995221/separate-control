package com.free.decision.server.controller;

import cn.hutool.core.util.ReUtil;
import com.free.decision.server.enums.UdReturnTypeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.UdInterfaceParamsVO;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.UdService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("ud")
public class UdController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UdController.class);

    @Resource
    private UdService udService;

    @Resource
    private CompanyService companyService;

    /**
     * 有盾反欺诈接口
     * @param udInterfaceParamsVO
     * @return
     */
    @PostMapping("getCreditReport")
    public Result getCreditReport(@RequestBody UdInterfaceParamsVO udInterfaceParamsVO){
        Result result = validate(udInterfaceParamsVO);
        if(!result.isSuccess()){
            logger.warn("请求反欺诈接口失败， msg:{}, udCreditParamVO:{}", result.getMsg(), udInterfaceParamsVO);
            return result;
        }
        return udService.getCreditReport(udInterfaceParamsVO,result.getData());
    }

    /**
     * 校验用户参数
     * @param udInterfaceParamsVO
     * @return
     */
    private Result validate(UdInterfaceParamsVO udInterfaceParamsVO){
        // 校验公司接口权限和验签
        Result result = companyService.checkCompanyApi(udInterfaceParamsVO.getApiKey(), udInterfaceParamsVO.getSign(), udInterfaceParamsVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        Company company = result.getData();
        if(StringUtils.isBlank(udInterfaceParamsVO.getName())){
            return new Result(false, UdReturnTypeEnum.ERROR.getCode(),"姓名不能为空");
        }
        if(udInterfaceParamsVO.getName().trim().length() > 50){
            return new Result(false, UdReturnTypeEnum.ERROR.getCode(),"姓名长度最大不能超过50位字符");
        }
        if(StringUtils.isBlank(udInterfaceParamsVO.getMobile())){
            return new Result(false, UdReturnTypeEnum.ERROR.getCode(),"手机号不能为空");
        }
        if(!ReUtil.isMatch("^1\\d{10}$", udInterfaceParamsVO.getMobile())){
            return new Result(false, UdReturnTypeEnum.ERROR.getCode(),"手机号格式不正确");
        }
        if(StringUtils.isBlank(udInterfaceParamsVO.getIdNo())){
            return new Result(false, UdReturnTypeEnum.ERROR.getCode(),"身份证号不能为空");
        }
        if(udInterfaceParamsVO.getIdNo().trim().length() > 30){
            return new Result(false, UdReturnTypeEnum.ERROR.getCode(),"身份证号格式不正确");
        }
        return new Result(true,"成功", company);
    }

}
