package com.free.decision.server.controller;

import cn.hutool.crypto.SecureUtil;
import com.free.decision.server.enums.TaoBaoOrderStatusEnum;
import com.free.decision.server.model.vo.XyCallBackParamsVO;
import com.free.decision.server.service.TaoBaoOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 淘宝回调
 */
@RestController
@RequestMapping("taobao/callback")
public class TaoBaoCallBackController {

    @Value("${decision.xy.apiUser}")
    private String apiUser;

    @Value("${decision.xy.apiKey}")
    private String apiKey;

    @Resource
    private TaoBaoOrderService taoBaoOrderService;

    @RequestMapping("xinyan")
    public String xinyan(@RequestBody XyCallBackParamsVO xyTaoBaoCallBackParamsVO){
        //验签
        String apiEnc = SecureUtil.md5(apiUser+apiKey+xyTaoBaoCallBackParamsVO.getToken());
        if(!apiEnc.equalsIgnoreCase(xyTaoBaoCallBackParamsVO.getApiEnc())){
            return "fail";
        }
        if(StringUtils.equals(xyTaoBaoCallBackParamsVO.getApiName(), "taobaoweb")){
            //淘宝
            if(StringUtils.equals(xyTaoBaoCallBackParamsVO.getType(), "report")){
                //淘宝报告
                taoBaoOrderService.updateStatus(TaoBaoOrderStatusEnum.DOING.getId(), 2, xyTaoBaoCallBackParamsVO.getTaskId(), xyTaoBaoCallBackParamsVO.getToken());
            }else{
                //原始数据
                taoBaoOrderService.updateStatus(TaoBaoOrderStatusEnum.DOING.getId(), 1, xyTaoBaoCallBackParamsVO.getTaskId(), xyTaoBaoCallBackParamsVO.getToken());
            }
        }
        return "success";
    }
}
