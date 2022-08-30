package com.free.decision.server.sms;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.enums.SmsChannelEnum;
import com.free.decision.server.enums.SmsContentEnum;
import com.free.decision.server.enums.SuccessStatusEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.SmsSend;
import com.free.decision.server.model.vo.SmsSendParamVO;
import com.free.decision.server.service.ConsumeService;
import com.free.decision.server.service.SmsSendService;
import com.free.decision.server.utils.HttpClientUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云短信
 */
@Service
public class YunDuanXinSmsSendStrategy extends SmsSendStrategy {

	private static Logger logger = LoggerFactory.getLogger(YunDuanXinSmsSendStrategy.class);

	// 变量短信地址
	private static String send_url = "http://api.1cloudsp.com/api/v2";

	@Resource
	private SmsSendService smsSendService;

	@Resource
	private ConsumeService consumeService;

	@Value("${devModel}")
	private boolean devModel;

	@Value("${last.day.msg.max}")
	private int dayMsgCountMax;

	@Value("${last.hour.msg.max}")
	private int hourMsgCountMax;

	@Value("${ydx_sms_account}")
	private String accesskey;

	@Value("${ydx_sms_password}")
	private String secret;

	private static Map<String, String> smsMap = new HashMap<>();
	// 云短信模板 key:templateId（短信模板ID）   value:短信内容
	static {
		smsMap.put("46235", "登录验证码：-，如非本人操作，请忽略此短信。");
		smsMap.put("97121", "-您好，您的借款今天到期，请您在中午前还款，如已还款请忽略，谢谢。回T退订");
		smsMap.put("97120", "-您好，您的借款明天到期，请您在到期当天中午前还款，如已还款请忽略，谢谢。回T退订");
		smsMap.put("97387", "您的借款已经通过，-已经到达您绑定的银行卡内，请注意查收，谢谢。回T退订");
		smsMap.put("47093", "尊敬的{1}客户，截止到{2}月{3}日{4}时，您的余额已不足{5}元。请您及时充值，以免余额不足而影响正常使用，谢谢。");
	}

	/**
	 * 根据短信内容类型 获取模板ID
	 * @param contentType
	 * @return
	 */
	private String getTemplateId (int contentType){
		if (SmsContentEnum.CODE.getId() == contentType) {
			return "46235";
		} else if (SmsContentEnum.TODAY.getId() == contentType) {
			return "97121";
		} else if (SmsContentEnum.TOMORROW.getId() == contentType) {
			return "97120";
		} else if (SmsContentEnum.LOAN.getId() == contentType) {
			return "97387";
		} else if (SmsContentEnum.BALANCE.getId() == contentType) {
			return "47093";
		}
		return "";
	}

	/**
	 * 根据模板Id获取短信内容
	 * @param templateId
	 * @return
	 */
	private static String getSmsContent(String templateId) {
		for (Map.Entry map : smsMap.entrySet()) {
			if (map.getKey().equals(templateId)) {
				return (String) map.getValue();
			}
		}
		return "";
	}

	/**
	 * 发送单条短信
	 * @param company
	 * @return
	 */
	@Override
	public Result send(SmsSendParamVO smsSendParamVO, Company company) {
		String templateId = getTemplateId(smsSendParamVO.getSmsType());
		String sign = smsSendParamVO.getSmsSign();
		String mobile = smsSendParamVO.getMobile();
		String content = smsSendParamVO.getContent();
		Date date = new Date();
		long lastHourMsgCount = smsSendService.getLastHourMsg(date, mobile,SmsChannelEnum.YUN_DUAN_XING_SMS.getId());
		if (lastHourMsgCount > hourMsgCountMax){
			logger.warn("短信条数超出小时限制，{}",mobile);
			return new Result(false, "发送失败");
		}
		long lastDayMsgCount = smsSendService.getLastDayMsg(date, mobile,SmsChannelEnum.YUN_DUAN_XING_SMS.getId());
		if (lastDayMsgCount > dayMsgCountMax){
			logger.warn("短信条数超出天限制，{}",mobile);
			return new Result(false, "发送失败");
		}
		Map<String, String> map = new HashMap<>();
		map.put("accesskey", accesskey);// API账号
		map.put("secret", secret);// API密码
		map.put("sign",sign);
		map.put("templateId",templateId);
		map.put("mobile",mobile);
		try {
			map.put("content",URLEncoder.encode(content.replace(",","##"), "utf-8"));
		} catch (Exception e) {
			logger.error("云短信编码异常,mobile:{},error:{}",mobile,e);
		}
        String ret = HttpClientUtils.executePostHttpClientUTF(send_url + "/single_send", map);
		JSONObject jsonObject = JSON.parseObject(ret);
		if (SmsContentEnum.CODE.getId() == smsSendParamVO.getSmsType()) {
			content = sign + getSmsContent(templateId).replace("-",content);
		} else if (SmsContentEnum.BALANCE.getId() == smsSendParamVO.getSmsType()) {
			String[] str = content.split(",");
			content = sign + "尊敬的" + str[0] + "客户，截止到" + str[1] + "月" + str[2] + "日" + str[3] + "时，您的余额已不足" + str[4] + "元。请您及时充值，以免余额不足而影响正常使用，谢谢。";
		}
		// 记录短信发送
		SmsSend smsSend = new SmsSend();
		smsSend.setChannel(SmsChannelEnum.YUN_DUAN_XING_SMS.getId());
		smsSend.setContent(content);
		smsSend.setIp(smsSendParamVO.getIp());
		smsSend.setMobile(mobile);
		smsSend.setCount(1);
		smsSend.setType(smsSendParamVO.getSmsType());
		smsSend.setParams(smsSendParamVO.getContent());
		smsSend.setRet(ret);
		smsSend.setChannel(SmsChannelEnum.YUN_DUAN_XING_SMS.getId());
		consumeService.consume(company.getId(), company.getSmsCodePrice(), ReportTypeEnum.SMS_CODE, ConsumeTypeEnum.CONSUME, SmsContentEnum.getEnum(smsSendParamVO.getSmsType()).getName()+"-"+mobile);
		if (StringUtils.equalsIgnoreCase(jsonObject.getString("code"), "0")) {
			smsSend.setStatus(SuccessStatusEnum.SENDING.getId());
			smsSendService.add(smsSend);
			return new Result(true, "发送成功");
		}
		smsSend.setStatus(SuccessStatusEnum.FAIL.getId());
		smsSendService.add(smsSend);
		return new Result(false, "发送失败", jsonObject.getString("errorMsg"));
	}

	/**
	 * 发送批量短信
	 * @param smsSendParamVO
	 * @param company
	 * @return
	 */
	@Override
	public Result batchSend(SmsSendParamVO smsSendParamVO, Company company) {
        String templateId = getTemplateId(smsSendParamVO.getSmsType());
		Map<String, String> contentMap = JSONObject.parseObject(smsSendParamVO.getContent(), HashedMap.class);
		List<Dict> list = new ArrayList<>();
		contentMap.forEach((key, value) -> list.add(Dict.create().set("key", key).set("value", value)));
		// 每页大小
		int pageSize = 800;
		// 页码
		int page = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		for (int i = 1; i <= page; i++) {
			int start = (i - 1) * pageSize;
			int end = i * pageSize - 1;
			if (end >= list.size()) {
				end = list.size() - 1;
			}
			List<Dict> dataList = list.subList(start, end + 1);
			Map<String,String> map = new HashMap<>();
			for(int j = 0; j < dataList.size(); j++){
				map.put(dataList.get(j).getStr("key"),dataList.get(j).getStr("value"));
			}
            Map<String, String> smsSendMap = new HashMap<>();
            smsSendMap.put("accesskey", accesskey);// API账号
            smsSendMap.put("secret", secret);// API密码
            smsSendMap.put("sign", smsSendParamVO.getSmsSign());
            smsSendMap.put("templateId", templateId);
            try {
				smsSendMap.put("data", URLEncoder.encode(JSON.toJSONString(map), "utf-8"));
			} catch (Exception e) {
				logger.error("云短信编码异常,mobile:{},error:{}", contentMap.toString(), e);
			}
			String ret = HttpClientUtils.executePostHttpClientUTF(send_url + "/send", smsSendMap);
			JSONObject jsonObject = JSON.parseObject(ret);
			for (int n = 0; n < dataList.size(); n++){
				SmsSend smsSend = new SmsSend();
				smsSend.setChannel(SmsChannelEnum.YUN_DUAN_XING_SMS.getId());
				smsSend.setContent(smsSendParamVO.getSmsSign() + getSmsContent(templateId).replace("-", dataList.get(n).getStr("value")));
				smsSend.setIp(smsSendParamVO.getIp());
				smsSend.setMobile(dataList.get(n).getStr("key"));
				smsSend.setCount(1);
				smsSend.setRet(ret);
				smsSend.setType(smsSendParamVO.getSmsType());
				smsSend.setParams(dataList.get(n).getStr("key")+","+dataList.get(n).getStr("value"));
				if (StringUtils.equalsIgnoreCase(jsonObject.getString("code"), "0")) {
					smsSend.setStatus(SuccessStatusEnum.SENDING.getId());
				}else {
                    smsSend.setStatus(SuccessStatusEnum.FAIL.getId());
                }
                smsSendService.add(smsSend);
            }
        }
        consumeService.consume(company.getId(), company.getSmsNoticePrice().multiply(new BigDecimal(list.size())), ReportTypeEnum.BATCH_SMS,
                ConsumeTypeEnum.CONSUME, "批量发送通知类短信-" + list.size() + "条");
        return new Result(true, "发送成功");
    }

}
