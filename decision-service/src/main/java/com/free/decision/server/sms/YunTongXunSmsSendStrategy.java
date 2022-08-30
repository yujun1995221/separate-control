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
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 235创蓝云通讯
 */
@Service
public class YunTongXunSmsSendStrategy extends SmsSendStrategy {

	private static Logger logger = LoggerFactory.getLogger(YunTongXunSmsSendStrategy.class);

	// 变量短信地址
	private static String send_url = "http://smssh1.253.com/msg/variable/json";

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

	private static Map<String, String> smsMap = new HashMap<>();
	// 短信模板 key:templateId（短信模板ID）   value:短信内容
	static {
		smsMap.put("1", "登录验证码：{$var}，如非本人操作，请忽略此短信。");
		smsMap.put("2", "{$var}您好，您的借款今天到期，请您在中午前还款，如已还款请忽略，谢谢。");
		smsMap.put("3", "{$var}您好，您的借款明天到期，请您在到期当天中午前还款，如已还款请忽略，谢谢。");
		smsMap.put("4", "您的借款已经通过，{$var}已经到达您绑定的银行卡内，请注意查收，谢谢。");
		smsMap.put("5", "尊敬的{$var}客户，截止到{$var}月{$var}日{$var}时，您的余额已不足{$var}元。请您及时充值，以免余额不足而影响正常使用，谢谢。");
	}

	/**
	 * 根据短信内容类型 获取模板ID
	 * @param contentType
	 * @return
	 */
	private String getTemplateId (int contentType){
		if (SmsContentEnum.CODE.getId() == contentType) {
			return "1";
		} else if (SmsContentEnum.TODAY.getId() == contentType) {
			return "2";
		} else if (SmsContentEnum.TOMORROW.getId() == contentType) {
			return "3";
		} else if (SmsContentEnum.LOAN.getId() == contentType) {
			return "4";
		} else if (SmsContentEnum.BALANCE.getId() == contentType) {
			return "5";
		}
		return "";
	}

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

	@Value("${cl_sms_account}")
	private String account;

	@Value("${cl_sms_password}")
	private String password;

	@Value("${cl_hk_account}")
	private String hkAccount;

	@Value("${cl_hk_password}")
	private String hkPassword;

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
		long lastHourMsgCount = smsSendService.getLastHourMsg(date, mobile, SmsChannelEnum.YUN_TONG_XUN_SMS.getId());
		if (lastHourMsgCount > hourMsgCountMax){
			logger.warn("短信条数超出小时限制，{}",mobile);
			return new Result(false, "发送失败");
		}
		long lastDayMsgCount = smsSendService.getLastDayMsg(date, mobile, SmsChannelEnum.YUN_TONG_XUN_SMS.getId());
		if (lastDayMsgCount > dayMsgCountMax){
			logger.warn("短信条数超出天限制，{}",mobile);
			return new Result(false, "发送失败");
		}
		Map<String, String> map = new HashMap<>();
		if (SmsContentEnum.CODE.getId() == smsSendParamVO.getSmsType()) {
			map.put("account", account);// API账号
			map.put("password", password);// API密码
		} else {
			map.put("account", hkAccount);// API账号
			map.put("password", hkPassword);// API密码
		}
		map.put("msg", sign + getSmsContent(templateId));// 短信内容
		map.put("params", mobile + "," + content);// 手机号+ 短信参数,每个参数逗号隔开
		map.put("extend", RandomStringUtils.randomNumeric(3));
		JSONObject jsonMap = (JSONObject) JSONObject.toJSON(map);
        String ret = HttpClientUtils.executeJsonHttpClientUTF(send_url, jsonMap.toString());
		JSONObject jsonObject = JSON.parseObject(ret);

		if (SmsContentEnum.CODE.getId() == smsSendParamVO.getSmsType()) {
			content = sign + getSmsContent(templateId).replace("{$var}",content);
		} else if (SmsContentEnum.BALANCE.getId() == smsSendParamVO.getSmsType()) {
			String[] str = content.split(",");
			content = sign + "尊敬的" + str[0] + "客户，截止到" + str[1] + "月" + str[2] + "日" + str[3] + "时，您的余额已不足" + str[4] + "元。请您及时充值，以免余额不足而影响正常使用，谢谢。";
		}
		// 记录短信发送
		SmsSend smsSend = new SmsSend();
		smsSend.setChannel(SmsChannelEnum.YUN_TONG_XUN_SMS.getId());
		smsSend.setContent(content);
		smsSend.setIp(smsSendParamVO.getIp());
		smsSend.setMobile(mobile);
		smsSend.setCount(1);
		smsSend.setType(smsSendParamVO.getSmsType());
		smsSend.setParams(smsSendParamVO.getContent());
		smsSend.setRet(ret);
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
            String param = "";
            for(int j = 0; j < dataList.size(); j++){
                param += dataList.get(j).getStr("key")+","+dataList.get(j).getStr("value") + ";";
            }
            Map<String, String> smsSendMap = new HashMap<>();
            smsSendMap.put("account", hkAccount);// API账号
            smsSendMap.put("password", hkPassword);// API密码
            smsSendMap.put("msg", smsSendParamVO.getSmsSign() + getSmsContent(templateId));// 短信内容
            smsSendMap.put("params", StringUtils.removeEnd(param, ";"));
            smsSendMap.put("extend", RandomStringUtils.randomNumeric(3));
            JSONObject jsonMap = (JSONObject) JSONObject.toJSON(smsSendMap);
            String ret = HttpClientUtils.executeJsonHttpClientUTF(send_url, jsonMap.toString());
            JSONObject jsonObject = JSON.parseObject(ret);
            for (int n = 0; n < dataList.size(); n++){
                SmsSend smsSend = new SmsSend();
                smsSend.setChannel(SmsChannelEnum.YUN_TONG_XUN_SMS.getId());
                smsSend.setContent(smsSendParamVO.getSmsSign() + getSmsContent(templateId).replace("{$var}", dataList.get(n).getStr("value")));
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
