package com.free.decision.server.apis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.enums.BldRecordTypeEnum;
import com.free.decision.server.model.BldRecord;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.service.BldService;
import com.free.decision.server.utils.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 宝莲灯
 */
@Component
public class BldApi {

    private static Logger logger = LoggerFactory.getLogger(BldApi.class);

    @Resource
    private BldService bldService;

    private static final String secrect = "34551e8e-f3f7-47d0-80fd-1349c08544ba";

    public Result black(long userId, String name, String mobile, String idNumber, Company company){
        BldRecord bldRecord = bldService.loadBlackByPhoneAndType(mobile, BldRecordTypeEnum.BLACK.getId());
        if(bldRecord != null){
            logger.info("宝莲灯黑名单命中缓存,phone:{}", mobile);
            return new Result(true, "命中黑名单");
        }
        int phoneHit = 0;
        int idNumberHit = 0;
        long timestamp = System.currentTimeMillis();
        Map<String, String> params = new HashMap<>();
        params.put("phone", mobile);
        params.put("name", name);
        params.put("id_card", idNumber);
        params.put("timestamp", String.valueOf(timestamp));
        String sign = SecureUtil.md5(StrUtil.format("id_card={}&name={}&phone={}&timestamp={}{}", idNumber, name, mobile, timestamp, secrect));
        params.put("sign", sign);
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        String ret = HttpKit.post("https://www.goodyzy.cn/api/v1/union/apps/6d02bfb9-6db0-4eab-87ad-45864d763b1e/risk/hit", JSON.toJSONString(params), header);
        logger.info("宝莲灯接口返回,phone:{},ret:{}", mobile, ret);
        JSONObject retObj = JSON.parseObject(ret);
        //保存记录
        bldRecord = new BldRecord();
        bldRecord.setCompanyId(company.getId());
        bldRecord.setName(name);
        bldRecord.setIdNumber(idNumber);
        bldRecord.setPhone(mobile);
        bldRecord.setUserId(userId);
        bldRecord.setRequestParams(JSON.toJSONString(params));
        bldRecord.setType(BldRecordTypeEnum.BLACK.getId());
        bldRecord.setResult(ret);
        if(StrUtil.equals(retObj.getString("code"), "0")){
            JSONObject resultObj = retObj.getJSONObject("result");
            phoneHit = resultObj.getBooleanValue("phone_hit")?1:0;
            idNumberHit = resultObj.getBooleanValue("id_card_hit")?1:0;
            if(phoneHit == 1 || idNumberHit == 1){
                bldRecord.setPhoneHitBlack(phoneHit);
                bldRecord.setIdNumberHitBlack(idNumberHit);
                bldService.insert(bldRecord);
                return new Result(true, "命中黑名单");
            }
        }
        bldRecord.setPhoneHitBlack(phoneHit);
        bldRecord.setIdNumberHitBlack(idNumberHit);
        bldService.insert(bldRecord);
        return new Result(false, "未命中黑名单");
    }
}
