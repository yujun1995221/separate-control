package com.free.decision.server.apis;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.free.decision.server.enums.MobileChannelEnum;
import com.free.decision.server.enums.MobileTypeEnum;
import com.free.decision.server.enums.UdReturnTypeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.MobileOrder;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.ZhiMiRecord;
import com.free.decision.server.model.vo.DecisionParamsVO;
import com.free.decision.server.service.MobileService;
import com.free.decision.server.service.ZhiMiService;
import com.free.decision.server.utils.AliOssUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * 指迷分控
 */
@Component
public class ZhiMiApi {

    private static final String modelName = "cfzz_v1";

    private static Logger logger = LoggerFactory.getLogger(ZhiMiApi.class);

    @Resource
    private MobileService mobileService;

    @Resource
    private ZhiMiService zhiMiService;

    @Resource
    private AliOssUtils aliOssUtils;

    @Resource
    private CacheChannel cacheChannel;

    @Value("${oss.report.bucket}")
    private String reportBucket;

    public Result decision(long userId, DecisionParamsVO decisionParamsVO, Company company){

        // 获取缓存数据
        Double score = (Double) cacheChannel.get("decision.zhimi.id", "score_id_"+decisionParamsVO.getIdNumber()+"_"+decisionParamsVO.getPhone()).getValue();
        if(score != null){
            logger.info("结束执行ZhiMiApi接口,从缓存中读取成功 ===> companyId_"+company.getId()+"_idNo_"+decisionParamsVO.getIdNumber());
            return new Result(true, UdReturnTypeEnum.SUCCESS.getCode(),UdReturnTypeEnum.SUCCESS.getResultDesc(), score);
        }

        JSONObject extend = JSON.parseObject(decisionParamsVO.getExtend());
        String channel = extend.getString("channelId");
        String apply_time = extend.getString("applyTime");
        String mobile = decisionParamsVO.getPhone();
        String name = decisionParamsVO.getName();
        String idcard = decisionParamsVO.getIdNumber();
        String phone_os = extend.getString("phoneOs");
       // String user_address = sampleObject.getString("user_address");
        List<EmergencyContact> e_contacts = new ArrayList<>();
        e_contacts.add(new EmergencyContact(decisionParamsVO.getContactList().get(0).getName(), decisionParamsVO.getContactList().get(0).getPhone()));
        e_contacts.add(new EmergencyContact(decisionParamsVO.getContactList().get(1).getName(), decisionParamsVO.getContactList().get(1).getPhone()));

        //新颜运营商
        MobileOrder mobileOrder = mobileService.queryLastByUserId(userId, decisionParamsVO.getMobileChannel());
        if(mobileOrder == null){
            logger.warn("指迷分控运营商信息不存在,userId:{}", userId);
            return new Result(false, "fail");
        }
        //运营商
        Map<String, String> carrier_data = new HashMap<>();
        if(decisionParamsVO.getMobileChannel() == MobileChannelEnum.XIN_YAN.getId()){
            carrier_data.put("xinyan_report", aliOssUtils.readReport(reportBucket, mobileOrder.getReportResult()));
            carrier_data.put("xinyan_raw", aliOssUtils.readReport(reportBucket, mobileOrder.getDataResult()));
        }else if(decisionParamsVO.getMobileChannel() == MobileChannelEnum.MO_XIE.getId()){
            carrier_data.put("mx_report", aliOssUtils.readReport(reportBucket, mobileOrder.getReportResult()));
            carrier_data.put("mx_raw", aliOssUtils.readReport(reportBucket, mobileOrder.getDataResult()));
        }

        List<Contact> contact = new ArrayList<>();
       // JSONArray contactArray = extend.getJSONArray("calls");
        if(CollUtil.isNotEmpty(decisionParamsVO.getPhoneList())){
            for(DecisionParamsVO.Phone phone : decisionParamsVO.getPhoneList()){
                contact.add(new Contact(phone.getName(), phone.getPhone(), phone.getUpdateTime()));
            }
        }
        ZhimiRiskRequest request = new ZhimiRiskRequest();
        request.setModel_name(modelName);
        request.setProduct(String.valueOf(company.getId()));
        request.setChannel(channel);
        request.setApply_time(apply_time);
        request.setMobile(mobile);
        request.setName(name);
        request.setIdcard(idcard);
        request.setPhone_os(phone_os);
        //request.setUser_address(user_address);
        request.setCarrier_data(carrier_data);
        request.setE_contacts(e_contacts);
        request.setContact(contact);

        String requestStr = JSON.toJSONString(request, SerializerFeature.WriteMapNullValue);
        String url = "http://47.93.185.26/risk/gzip/";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setEntity(new ByteArrayEntity(gzip(requestStr)));
        JSONObject retObj = null;
        try {
            HttpResponse response = client.execute(post);
            String responseStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            //String responseStr = "{\"score\":580.5515677707799,\"history_apply\":{\"idcard_3h_cnt\":4,\"idcard_1d_cnt\":4,\"idcard_7d_cnt\":4,\"idcard_60d_cnt\":4,\"mobile_60d_cnt\":4,\"idcard_30d_cnt\":4,\"idcard_14d_cnt\":4,\"mobile_3d_cnt\":4,\"mobile_3h_cnt\":4,\"idcard_3d_cnt\":4,\"mobile_12h_cnt\":4,\"mobile_1d_cnt\":4,\"idcard_12h_cnt\":4,\"mobile_30d_cnt\":4,\"mobile_1h_cnt\":4,\"idcard_1h_cnt\":4,\"mobile_14d_cnt\":4,\"mobile_7d_cnt\":4},\"return_info\":\"success\",\"return_code\":0,\"request_id\":\"20190611233307-35f347f0-8c5e-11e9-89ea-00163e063d48\"}";
            retObj = JSON.parseObject(responseStr);
            if(retObj.getIntValue("return_code") == 0){
                // 将请求成功的数据进行缓存
                cacheChannel.set("decision.zhimi.id", "score_id_"+decisionParamsVO.getIdNumber()+"_"+decisionParamsVO.getPhone(), Math.floor(NumberUtils.toDouble(retObj.getString("score"))));
                return new Result(true, "success", Math.floor(NumberUtils.toDouble(retObj.getString("score"))));
            }else{
                logger.warn("指迷分控请求失败,userId:{},ret:{}", userId, responseStr);
                return new Result(false, "fail");
            }
        } catch(IOException e){
            logger.error("指迷分控异常,userId:"+userId, e);
            return new Result(false, "fail");
        }finally {
            ZhiMiRecord zhiMiRecord = new ZhiMiRecord();
            zhiMiRecord.setUserId(userId);
            if(retObj != null){
                zhiMiRecord.setRequestId(retObj.getString("request_id"));
                zhiMiRecord.setResult(retObj.toJSONString());
            }
            zhiMiService.add(zhiMiRecord);
        }
    }

    private byte[] gzip(String str){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if(gzip != null){
                try{
                    gzip.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return out.toByteArray();
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmergencyContact{
    private String contact_name;
    private String contact_phone;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Contact{
    private String contact_name;
    private String contact_phone;
    private String update_time;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ZhimiRiskRequest {
    private String model_name;
    private String product;
    private String channel;
    private String apply_time;
    private String mobile;
    private String name;
    private String idcard;
    private String phone_os;
    private String user_address;
    private List<EmergencyContact> e_contacts;
    private Map<String, String> carrier_data;
    private List<Contact> contact;
}

