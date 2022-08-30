package com.free.decision.server.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.CustomerInfoVO;
import com.free.decision.server.model.vo.LiabilityDataVO;
import com.free.decision.server.model.vo.OrderInfoVO;
import com.free.decision.server.model.vo.PersistOrderInfoVO;
import com.free.decision.server.service.CompanyService;
import com.free.decision.server.service.CustomerInfoService;
import com.free.decision.server.service.OrderInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 回租信息推送
 */
@RestController
@RequestMapping("/credit/push")
public class CreditPushController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(CreditPushController.class);

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private CustomerInfoService customerInfoService;

    @Resource
    private CompanyService companyService;

    /**
     * 保存订单信息
     * @param orderInfoVO
     * @return
     */
    @PostMapping("orderInfo")
    public Result orderInfo(@RequestBody OrderInfoVO orderInfoVO){
        Result result = validateOrderInfo(orderInfoVO);
        if(!result.isSuccess()){
            logger.warn("保存订单信息失败，msg:{}, orderInfoVO:{}", result.getMsg(), orderInfoVO);
            return result;
        }
        Company company = result.getData();
        orderInfoVO.setCompanyId(company.getId());
        orderInfoService.push(orderInfoVO);
        return new Result(true, ResultCodeEnum.SUCCESS.getId(),"成功");
    }

    /**
     * 保存客户信息
     * @param customerInfoVO
     * @return
     */
    @PostMapping("customerInfo")
    public Result customerInfo(@RequestBody CustomerInfoVO customerInfoVO){
        Result result = validateCustomerInfo(customerInfoVO);
        if(!result.isSuccess()){
            logger.warn("保存客户信息失败，msg:{}, orderInfoVO:{}", result.getMsg(), customerInfoVO);
            return result;
        }
        Company company = result.getData();
        customerInfoVO.setCompanyId(company.getId());
        customerInfoService.push(customerInfoVO);
        return new Result(true, ResultCodeEnum.SUCCESS.getId(),"成功");
    }

    /**
     * 删除客户
     * @param customerInfoVO
     * @return
     */
    @PostMapping("deleteCustomer")
    public Result deleteCustomer(@RequestBody CustomerInfoVO customerInfoVO){
        if (StringUtils.isBlank(customerInfoVO.getApiKey()) || StringUtils.isBlank(customerInfoVO.getSign()) || StringUtils.isBlank(customerInfoVO.getTimestamp())) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        Company company = companyService.loadIdByApiKey(customerInfoVO.getApiKey());
        if (company == null) {
            logger.warn("deleteCustomer apiKey 不存在,{}", customerInfoVO.getApiKey());
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        //验签
        String sign = SecureUtil.md5(SecureUtil.md5(company.getApiSecret().concat(customerInfoVO.getTimestamp())));
        if (!StringUtils.equalsIgnoreCase(sign, customerInfoVO.getSign())) {
            logger.info("deleteCustomer 验签失败");
            return new Result(false, ResultCodeEnum.FAIL.getId(), "验签失败");
        }
        if(StringUtils.isBlank(customerInfoVO.getIdNumber())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号不能为空");
        }
        if(customerInfoVO.getIdNumber().trim().length() > 30){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号格式不正确");
        }
        customerInfoService.deleteCustomer(customerInfoVO.getIdNumber(), company.getId());
        return new Result(true, ResultCodeEnum.SUCCESS.getId(),"成功");
    }

    /**
     * 校验订单参数
     * @param orderInfoVO
     * @return
     */
    private Result validateOrderInfo(OrderInfoVO orderInfoVO){
        if (StringUtils.isBlank(orderInfoVO.getApiKey()) || StringUtils.isBlank(orderInfoVO.getSign()) || StringUtils.isBlank(orderInfoVO.getTimestamp())) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        Company company = companyService.loadIdByApiKey(orderInfoVO.getApiKey());
        if (company == null) {
            logger.warn("validateOrderInfo apiKey 不存在,{}", orderInfoVO.getApiKey());
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        //验签
        String sign = SecureUtil.md5(SecureUtil.md5(company.getApiSecret().concat(orderInfoVO.getTimestamp())));
        if (!StringUtils.equalsIgnoreCase(sign, orderInfoVO.getSign())) {
            logger.info("validateOrderInfo 验签失败");
            return new Result(false, ResultCodeEnum.FAIL.getId(), "验签失败");
        }
        if(StringUtils.isBlank(orderInfoVO.getIdNumber())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号不能为空");
        }
        if(orderInfoVO.getIdNumber().trim().length() > 30){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号格式不正确");
        }
        if(StringUtils.isBlank(orderInfoVO.getOrderNumber())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"订单编号不能为空");
        }
        return new Result(true,"成功", company);
    }

    /**
     * 校验客户参数
     * @param customerInfoVO
     * @return
     */
    private Result validateCustomerInfo(CustomerInfoVO customerInfoVO){
        if (StringUtils.isBlank(customerInfoVO.getApiKey()) || StringUtils.isBlank(customerInfoVO.getSign()) || StringUtils.isBlank(customerInfoVO.getTimestamp())) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        Company company = companyService.loadIdByApiKey(customerInfoVO.getApiKey());
        if (company == null) {
            logger.warn("validateCustomerInfo apiKey 不存在,{}", customerInfoVO.getApiKey());
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        //验签
        String sign = SecureUtil.md5(SecureUtil.md5(company.getApiSecret().concat(customerInfoVO.getTimestamp())));
        if (!StringUtils.equalsIgnoreCase(sign, customerInfoVO.getSign())) {
            logger.info("validateCustomerInfo 验签失败");
            return new Result(false, ResultCodeEnum.FAIL.getId(), "验签失败");
        }
        if(StringUtils.isBlank(customerInfoVO.getName())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"姓名不能为空");
        }
        if(customerInfoVO.getName().trim().length() > 50){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"姓名长度最大不能超过50位字符");
        }
        if(StringUtils.isBlank(customerInfoVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"手机号不能为空");
        }
        if(!ReUtil.isMatch("^1\\d{10}$", customerInfoVO.getPhone())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"手机号格式不正确");
        }
        if(StringUtils.isBlank(customerInfoVO.getIdNumber())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号不能为空");
        }
        if(customerInfoVO.getIdNumber().trim().length() > 30){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号格式不正确");
        }
        return new Result(true,"成功", company);
    }

    /**
     * 查询负债共享数据
     * @param customerInfoVO
     * @return
     */
    @PostMapping("getLiabilityData")
    public Result getLiabilityData(@RequestBody CustomerInfoVO customerInfoVO){
        if (StringUtils.isBlank(customerInfoVO.getApiKey()) || StringUtils.isBlank(customerInfoVO.getSign()) || StringUtils.isBlank(customerInfoVO.getTimestamp())) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        Company company = companyService.loadIdByApiKey(customerInfoVO.getApiKey());
        if (company == null) {
            logger.warn("getLiabilityData apiKey 不存在,{}", customerInfoVO.getApiKey());
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        //验签
        String sign = SecureUtil.md5(SecureUtil.md5(company.getApiSecret().concat(customerInfoVO.getTimestamp())));
        if (!StringUtils.equalsIgnoreCase(sign, customerInfoVO.getSign())) {
            logger.info("getLiabilityData 验签失败");
            return new Result(false, ResultCodeEnum.FAIL.getId(), "验签失败");
        }
        if(StringUtils.isBlank(customerInfoVO.getIdNumber())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号不能为空");
        }
        if(customerInfoVO.getIdNumber().trim().length() > 30){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号格式不正确");
        }
        LiabilityDataVO liabilityDataVO = orderInfoService.getOrderInfo(customerInfoVO.getIdNumber(), company.getId());
        return new Result(true, "查询成功", liabilityDataVO);
    }

    /**
     * 续期订单信息
     * @param persistOrderInfoVO
     * @return
     */
    @PostMapping("persistOrderInfo")
    public Result persistOrderInfo(@RequestBody PersistOrderInfoVO persistOrderInfoVO){
        if (StringUtils.isBlank(persistOrderInfoVO.getApiKey()) || StringUtils.isBlank(persistOrderInfoVO.getSign()) || StringUtils.isBlank(persistOrderInfoVO.getTimestamp())) {
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        Company company = companyService.loadIdByApiKey(persistOrderInfoVO.getApiKey());
        if (company == null) {
            logger.warn("persistOrderInfo apiKey 不存在,{}", persistOrderInfoVO.getApiKey());
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        //验签
        String sign = SecureUtil.md5(SecureUtil.md5(company.getApiSecret().concat(persistOrderInfoVO.getTimestamp())));
        if (!StringUtils.equalsIgnoreCase(sign, persistOrderInfoVO.getSign())) {
            logger.info("persistOrderInfo 验签失败");
            return new Result(false, ResultCodeEnum.FAIL.getId(), "验签失败");
        }
        if(StringUtils.isBlank(persistOrderInfoVO.getIdNumber())){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号不能为空");
        }
        if(persistOrderInfoVO.getIdNumber().trim().length() > 30){
            return new Result(false, ResultCodeEnum.FAIL.getId(),"身份证号格式不正确");
        }
        persistOrderInfoVO.setCompanyId(company.getId());
        orderInfoService.pushPersistOrderInfo(persistOrderInfoVO);
        return new Result(true, "查询成功");
    }
}
