package com.free.decision.server.service;

import com.free.decision.server.enums.ConsumeTypeEnum;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;

import java.math.BigDecimal;
import java.util.List;

/**
 * 公司信息接口
 *
 * @author yxs
 */
public interface CompanyService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Company loadById(long id);

    /**
     * 验签
     * @param apiKey
     * @param sign
     * @param timestamp
     * @return
     */
    public Result checkSign(String apiKey, String sign, String timestamp);

    /**
     * 校验公司接口权限和验签
     * @param apiKey
     * @param sign
     * @param timestamp
     * @return
     */
    public Result checkCompanyApi(String apiKey, String sign, String timestamp);

    /**
     * 根据 api key查询公司信息
     *
     * @param apiKey
     * @return
     */
    public Company loadIdByApiKey(String apiKey);

    /**
     * 更新公司余额
     *
     * @param amount
     * @param consumeType
     * @return
     */
    public int updateAmount(long companyId, BigDecimal amount, ConsumeTypeEnum consumeType);

    /**
     * 查询余额
     *
     * @return
     */
    public BigDecimal getAmount(long companyId);

    /**
     * 获取公司余额小于500的公司id mcl
     *
     * @return
     */
    public List<Long> getAllCompanyIds();

}
