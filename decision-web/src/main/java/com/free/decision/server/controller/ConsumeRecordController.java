package com.free.decision.server.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Consume;
import com.free.decision.server.model.Result;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.ConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 消费记录
 */
@RestController
@RequestMapping("consume")
public class ConsumeRecordController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ConsumeRecordController.class);

    /**
     * 消费记录接口类
     */
    @Resource
    private ConsumeService consumeService;

    @Resource
    private CompanyService companyService;

    /**
     * 查询公司余额
     * @return
     */
    @RequestMapping("getAmount")
    public Result getAmount(HttpServletRequest request) {
        String content = "";
        try {
            content = IoUtil.read(request.getInputStream(), CharsetUtil.UTF_8);
        } catch (IOException e) {
            logger.error("getAmount is error!", e);
        }
        if(StrUtil.isBlank(content)){
            return null;
        }
        JSONObject paramObj = JSON.parseObject(content);
        Result result = companyService.checkSign(paramObj.getString("apiKey"), paramObj.getString("sign"), paramObj.getString("timestamp"));
        if(result.isSuccess()){
            Company company = result.getData();
            return new Result(true, "余额查询成功", company.getAmount());
        }
        return result;
    }

    /**
     * 查询消费记录
     * @return
     */
    @RequestMapping("getConsumeRecord")
    public Result getConsumeRecord(HttpServletRequest request){
        String content = "";
        try {
            content = IoUtil.read(request.getInputStream(), CharsetUtil.UTF_8);
        } catch (IOException e) {
            logger.error("getConsumeRecord is error!", e);
        }
        if(StrUtil.isBlank(content)){
            return null;
        }
        JSONObject paramObj = JSON.parseObject(content);
        Result result = companyService.checkSign(paramObj.getString("apiKey"), paramObj.getString("sign"), paramObj.getString("timestamp"));
        if(!result.isSuccess()){
            return result;
        }
        Company company = result.getData();
        List<Consume> dataList = consumeService.loadConsumeRecord(company.getId(), paramObj.getString("startTime"), paramObj.getString("endTime"), paramObj.getInteger("type"));
        return new Result(true, ResultCodeEnum.SUCCESS.getId(), "查询成功", dataList);
    }
}
