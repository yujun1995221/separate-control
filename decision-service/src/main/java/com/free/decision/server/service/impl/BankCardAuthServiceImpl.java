package com.free.decision.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.apis.UdApi;
import com.free.decision.server.apis.XyApi;
import com.free.decision.server.enums.BankCardTypeEnum;
import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ReportTypeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.BankCardAuthVO;
import com.free.decision.server.service.BankCardAuthService;
import com.free.decision.server.service.ConsumeService;
import com.free.decision.server.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 银行卡鉴权实现类
 * @author
 */
@Service
public class BankCardAuthServiceImpl implements BankCardAuthService {

    private static Logger logger = LoggerFactory.getLogger(BankCardAuthServiceImpl.class);

    /**
     * 用户信息接口类
     */
    @Resource
    private UserService userService;

    @Resource
    private XyApi xyApi;

    @Resource
    private UdApi udApi;

    /**
     * 消费记录接口类
     */
    @Resource
    private ConsumeService consumeService;

    @Value("${decision.bankCardAuth}")
    private Integer bankType;

    private static Map<String,String> xyBankMap = new HashMap<>();

    static {
        xyBankMap.put("ICBC", "中国工商银行");
        xyBankMap.put("ABC", "中国农业银行");
        xyBankMap.put("BOC", "中国银行");
        xyBankMap.put("CCB", "中国建设银行");
        xyBankMap.put("CMB", "招商银行");
        xyBankMap.put("PSBC", "中国邮政储蓄银行");
        xyBankMap.put("CITIC", "中信银行");
        xyBankMap.put("CEB", "光大银行");
        xyBankMap.put("BCOM", "交通银行");
        xyBankMap.put("CIB", "兴业银行");
        xyBankMap.put("CMBC", "民生银行");
        xyBankMap.put("PAB", "平安银行");
        xyBankMap.put("GDB", "广发银行");
        xyBankMap.put("BOB", "北京银行");
        xyBankMap.put("HXB", "华夏银行");
        xyBankMap.put("SPDB", "浦东发展银行");
        xyBankMap.put("SHB", "上海银行");
    }

    /**
     * 新版银行卡鉴权
     * @param bankCardAuthVO
     * @param company
     * @return
     */
    @Override
    public Result bankCardAuth(BankCardAuthVO bankCardAuthVO, Company company) {
        // 获取用户userId
        Long userId = userService.getUserIdByMobileAndIdNumber(company.getId(), bankCardAuthVO.getMobile(), bankCardAuthVO.getIdCard(), bankCardAuthVO.getIdHolder());
        Result result;
        logger.info("银行卡鉴权请求参数 {}" ,JSON.toJSONString(bankCardAuthVO));
        if(BankCardTypeEnum.UDBANK_AUTH.getId()==bankType){
            result = udApi.udBankVerify(bankCardAuthVO.getIdHolder(),bankCardAuthVO.getIdCard(),bankCardAuthVO.getAccNo(),bankCardAuthVO.getMobile(),company.getId(), userId);
            logger.info("有盾银行卡鉴权返回参数 {}" ,JSON.toJSONString(result));
            if (result.isSuccess()) {
                String udResult = result.getData();
                if(StringUtils.isNotBlank(udResult)){
                    JSONObject udResultData = JSON.parseObject(udResult);
                    String body = udResultData.getString("body");
                    JSONObject bodyData = JSON.parseObject(body);
                    String bankName = bodyData.getString("bank_name");
                    result.setData(bankName);
                }
            }
            //插入消费记录
            consumeService.consume(company.getId(), company.getBankAuthPrice(), ReportTypeEnum.BANK_AUTH, ConsumeTypeEnum.CONSUME, "银行卡鉴权-" + bankCardAuthVO.getMobile());
        }else{
            result = xyApi.bank(userId,bankCardAuthVO,company);
            logger.info("新颜银行卡鉴权返回参数 {}" ,JSON.toJSONString(result));
            if (StringUtils.isNotBlank(result.getData())) {
                String xyResult = result.getData();
                JSONObject xyResultData = JSON.parseObject(xyResult);
                //String code = xyResultData.getString("code");
                String bankName;
                String bankId = xyResultData.getString("bank_id");
                if(StringUtils.isBlank(bankId)){
                    return new Result(false, "暂不支持该银行卡，请更换重试");
                }
                bankId = bankId.toUpperCase();
                if(xyBankMap.containsKey(bankId)){
                    bankName = xyBankMap.get(bankId);
                }else{
                    bankName = xyResultData.getString("bank_description");
                }
                //插入消费记录
                consumeService.consume(company.getId(), company.getBankAuthPrice(), ReportTypeEnum.BANK_AUTH, ConsumeTypeEnum.CONSUME, "银行卡鉴权-" + bankCardAuthVO.getMobile());
                result.setData(bankName);
            }
        }
        return result;
    }
}
