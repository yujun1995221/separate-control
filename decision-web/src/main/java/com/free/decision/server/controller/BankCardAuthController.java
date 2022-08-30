package com.free.decision.server.controller;

import cn.hutool.core.util.ReUtil;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.BankCardAuthVO;
import com.free.decision.server.service.BankCardAuthService;
import com.free.decision.server.service.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 银行卡鉴权
 * @author
 */
@RestController
@RequestMapping("/bankcard")
public class BankCardAuthController extends BaseController {

    @Resource
    private CompanyService companyService;

    @Resource
    private BankCardAuthService bankCardAuthService;

    /**
     * 新版银行卡鉴权
     * @author
     * @param bankCardAuthVO
     * @return
     */
    @PostMapping("bankcardAuth")
    public Result bankcardAuth(@RequestBody BankCardAuthVO bankCardAuthVO){
        Result result = companyService.checkCompanyApi(bankCardAuthVO.getApiKey(), bankCardAuthVO.getSign(), bankCardAuthVO.getTimestamp());
        if(!result.isSuccess()){
            return result;
        }
        Company company = result.getData();
        if(StringUtils.isBlank(bankCardAuthVO.getIdHolder())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "证件姓名不能为空");
        }
        if(StringUtils.isBlank(bankCardAuthVO.getIdCard())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "证件号码不能为空");
        }
        if(bankCardAuthVO.getIdCard().trim().length() > 30){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "证件号码格式不正确");
        }
        if(StringUtils.isBlank(bankCardAuthVO.getAccNo())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "银行卡号不能为空");
        }
        if(bankCardAuthVO.getAccNo().trim().length() > 30){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "银行卡号格式不正确");
        }
        if(StringUtils.isBlank(bankCardAuthVO.getMobile())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "预留手机号码不能为空");
        }
        if(!ReUtil.isMatch("^1\\d{10}$", bankCardAuthVO.getMobile())){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "预留手机号码格式不正确");
        }
        return bankCardAuthService.bankCardAuth(bankCardAuthVO,company);
    }

}
