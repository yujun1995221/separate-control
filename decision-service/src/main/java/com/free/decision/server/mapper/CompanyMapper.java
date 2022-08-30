package com.free.decision.server.mapper;

import cn.hutool.core.util.StrUtil;
import com.free.decision.server.model.Company;
import com.free.decision.server.model.vo.CompanyDataVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.math.BigDecimal;
import java.util.List;

/**
 * 公司信息
 */
@Mapper
public interface CompanyMapper {

    /**
     * 根据ID查询商户信息
     * @param id
     * @return
     */
    @SelectProvider(type = CompanySqlBuilder.class, method = "loadById")
    public Company loadById(@Param("id") long id);

    /**
     * 根据api key查询公司信息
     * @param apiKey
     * @return
     */
    @SelectProvider(type = CompanySqlBuilder.class, method = "loadByApiKey")
    public Company loadByApiKey(@Param("apiKey") String apiKey);

    /**
     * 更新公司余额
     * @param amount
     * @param type
     * @return
     */
    @UpdateProvider(type = CompanySqlBuilder.class, method = "updateAmount")
    public int updateAmount(@Param("companyId") long companyId, @Param("amount") BigDecimal amount, @Param("type") int type);

    /**
     * 查询余额
     * @param companyId
     * @return
     */
    @Select("select amount from decision_company where id = #{companyId}")
    public BigDecimal getAmount(@Param("companyId") long companyId);

    /**
     * 获取所有公司id
     * @return
     */
    @Select("select id from decision_company where `status` = 1")
    public List<Long> getAllCompanyIds();



    class CompanySqlBuilder{

        private static final String BASE_COLUMN = "id,company_name as companyName,app_name as appName,contact_name as contactName," +
                "contact_mobile as contactMobile, amount,mobile_price as mobilePrice, credit_price as creditPrice, " +
                "decision_price as decisionPrice,taobao_price as taoBaoPrice,real_name_price as realNamePrice," +
                "sms_code_price as smsCodePrice,sms_notice_price as smsNoticePrice, bank_auth_price as bankAuthPrice," +
                "anti_fraud_price as antiFraudPrice,bld_black_price as bldBlackPrice,api_key as apiKey,api_secret as apiSecret,black_flag as blackFlag," +
                "decision_channel as decisionChannel,force_reject_flag as forceRejectFlag,credit_flag as creditFlag," +
                "mozhang_flag as mozhangFlag,mozhang_score as mozhangScore,credit_amount as creditAmount,bld_black_flag as bldBlackFlag," +
                "create_time as createTime,update_time as updateTime,liability_price as liabilityPrice";

        public String loadById(@Param("id") long id){
            return "select ".concat(BASE_COLUMN).concat(" from decision_company where id = #{id} and `status` = 1 limit 1");
        }

        public String loadByApiKey(@Param("apiKey") String apiKey){
            return "select ".concat(BASE_COLUMN).concat(" from decision_company where api_key = #{apiKey} and `status` = 1 limit 1");
        }

        public String updateAmount(@Param("companyId") long companyId, @Param("amount") BigDecimal amount, @Param("type") int type){
            if(type == 1){
                return "update decision_company set amount = amount-${amount} where id = #{companyId} and `status` = 1";
            }else if(type == 2){
                return "update decision_company set amount = amount+${amount} where id = #{companyId} and `status` = 1";
            }
            return null;
        }
    }
}
