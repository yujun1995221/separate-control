package com.free.decision.server.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.mapper.CompanyMapper;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.service.CompanyService;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 公司信息
 * @author yxs
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Resource
    private CacheChannel cacheChannel;

    @Resource
    private Redisson redisson;

    /**
     * 公司接口类
     */
    @Resource
    private CompanyMapper companyMapper;

    /**
     * 根据ID查询公司信息
     * @param id
     * @return
     */
    @Override
    public Company loadById(long id) {
        return companyMapper.loadById(id);
    }

    @Override
    public Result checkSign(String apiKey, String sign, String timestamp) {
        if(StringUtils.isBlank(apiKey) || StringUtils.isBlank(sign) || StringUtils.isBlank(timestamp)){
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        RLock lock = redisson.getLock("company_api_"+apiKey);
        lock.lock(20, TimeUnit.SECONDS);
        Company company = loadIdByApiKey(apiKey);
        if(company == null){
            lock.unlock();
            logger.warn("api key 不存在,{}", apiKey);
            return new Result(false, ResultCodeEnum.FAIL.getId(), "参数不正确");
        }
        //验签
        String a = SecureUtil.md5(SecureUtil.md5(company.getApiSecret()+timestamp));
        if(!StringUtils.equalsIgnoreCase(a, sign)){
            lock.unlock();
            logger.info("验签失败");
            return new Result(false, ResultCodeEnum.FAIL.getId(), "验签失败");
        }
        lock.unlock();
        return new Result(true, ResultCodeEnum.SUCCESS.getId(), "校验通过", company);
    }

    /**
     * 校验公司权限
     * @param apiKey
     * @param sign
     * @param timestamp
     * @return
     */
    @Override
    public Result checkCompanyApi(String apiKey, String sign, String timestamp) {
        Result result = checkSign(apiKey, sign, timestamp);
        if(result.isSuccess()){
            Company company = result.getData();
            BigDecimal amount = getAmount(company.getId());
            if(amount.add(company.getCreditAmount()).compareTo(BigDecimal.ZERO) <= 0){
                logger.info("余额不足");
                return new Result(false, ResultCodeEnum.FAIL.getId(), "余额不足");
            }
        }
        return result;
    }

    /**
     * 根据Key查询公司信息
     * @param apiKey
     * @return
     */
    @Override
    public Company loadIdByApiKey(String apiKey) {
      /*  CacheObject cacheObject = cacheChannel.get("decision.company", "apiKey_" + apiKey);
        Company company = (Company)cacheObject.getValue();
        if(company == null){
            company = companyMapper.loadByApiKey(apiKey);
            cacheChannel.set("decision.company", "apiKey_"+apiKey, company);
        }*/
        Company company = companyMapper.loadByApiKey(apiKey);
        return company;
    }

    /**
     * 更新公司余额
     * @param companyId
     * @param amount
     * @param consumeType
     * @return
     */
    @Override
    public int updateAmount(long companyId, BigDecimal amount, ConsumeTypeEnum consumeType) {
        return companyMapper.updateAmount(companyId, amount, consumeType.getId());
    }


    /**
     * 查询公司余额
     * @param companyId
     * @return
     */
    @Override
    public BigDecimal getAmount(long companyId) {
        return companyMapper.getAmount(companyId);
    }

    @Override
    public List<Long> getAllCompanyIds() {
        return companyMapper.getAllCompanyIds();
    }
}
