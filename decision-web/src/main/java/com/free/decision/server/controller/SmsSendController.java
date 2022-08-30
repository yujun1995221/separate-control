package com.free.decision.server.controller;

import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.SmsSendParamVO;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.SmsSendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 短信发送
 */
@RestController
@RequestMapping("/smsSend")
public class SmsSendController extends BaseController {

	@Resource
	private CompanyService companyService;

	@Resource
	private SmsSendService smsSendService;
	/**
	 * 短信发送
	 * @param smsSendParamVO
	 * @return
	 */
	@PostMapping("/send")
	public Result send(@RequestBody SmsSendParamVO smsSendParamVO) {
		Result result = companyService.checkCompanyApi(smsSendParamVO.getApiKey(), smsSendParamVO.getSign(), smsSendParamVO.getTimestamp());
		if (!result.isSuccess()) {
			return result;
		}
		Company company = result.getData();
		// 调用实现发送短信接口的方法
		result = smsSendService.send(smsSendParamVO, company);
		if (!result.isSuccess()) {
			return new Result(false, ResultCodeEnum.FAIL.getId(), result.getMsg());
		}
		return new Result(true, ResultCodeEnum.SUCCESS.getId(), "发送成功");
	}

	/**
	 * 短信批量发送
	 * @param smsSendParamVO
	 * @return
	 */
	@PostMapping("/batchSend")
	public Result batchSend(@RequestBody SmsSendParamVO smsSendParamVO) {
		Result result = companyService.checkCompanyApi(smsSendParamVO.getApiKey(), smsSendParamVO.getSign(), smsSendParamVO.getTimestamp());
		if (!result.isSuccess()) {
			return result;
		}
		Company company = result.getData();
		// 调用实现发送短信接口的方法
		result = smsSendService.batchSend(smsSendParamVO, company);
		if (!result.isSuccess()) {
			return new Result(false, ResultCodeEnum.FAIL.getId(), result.getMsg());
		}
		return new Result(true, ResultCodeEnum.SUCCESS.getId(), "发送成功");
	}
}
