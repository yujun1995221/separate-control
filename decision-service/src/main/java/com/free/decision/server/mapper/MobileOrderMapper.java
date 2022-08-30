package com.free.decision.server.mapper;

import com.free.decision.server.model.MobileOrder;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 运营商相关
 */
@Mapper
public interface MobileOrderMapper {

    /**
     * 新增运营商订单记录
     * @param mobileOrder
     * @return
     */
    @InsertProvider(type = MobileOrderSqlBuilder.class, method = "insert")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    public long insert(MobileOrder mobileOrder);

    /**
     * 更新状态
     * @param status
     * @param type 1：原始数据 2：报告数据
     * @param orderNo
     * @return
     */
    public int updateStatus(@Param("status") int status, @Param("type") int type, @Param("orderNo") String orderNo,
                            @Param("token") String token);


    /**
     * 查询进行中的订单
     * @return
     */
    public List<MobileOrder> getDoingOrder();

    /**
     * 查询用户最新的运营商认证
     * @param userId
     * @return
     */
    public MobileOrder queryLastByUserId(@Param("userId") long userId, @Param("channel") int channel);

    /**
     * 查询用户最新的运营商认证
     * @param phone
     * @return
     */
    public MobileOrder queryLastNoCacheByPhone(@Param("phone") String phone, @Param("channel") int channel);

    /**
     * 修改运营商订单结果
     * @param type 1：原始数据 2：报告数据
     * @param result
     * @param orderNo
     * @return
     */
    public int updateResult(@Param("type") int type, @Param("result") String result, @Param("orderNo") String orderNo);

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    public MobileOrder loadByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 更新获取次数
     * @param type
     * @param mobileOrderId
     * @return
     */
    public int updateTimes(@Param("type") int type, @Param("mobileOrderId") long mobileOrderId);


    class MobileOrderSqlBuilder{

        private static final String TABLE_NAME = "decision_mobile_order";

        public static String insert(MobileOrder mobileOrder){
            return new SQL(){{
                INSERT_INTO(TABLE_NAME);
                VALUES("company_id", "#{companyId}");
                VALUES("user_id", "#{userId}");
                VALUES("phone", "#{phone}");
                VALUES("id_number", "#{idNumber}");
                VALUES("phone_list", "#{phoneList}");
                VALUES("emerg_contacts", "#{emergContacts}");
                VALUES("channel", "#{channel}");
                VALUES("order_no", "#{orderNo}");
                VALUES("data_status", "#{dataStatus}");
                VALUES("report_status", "#{reportStatus}");
                VALUES("data_result", "#{dataResult}");
                VALUES("report_result", "#{reportResult}");
                VALUES("callback", "#{callback}");
                VALUES("cache_flag", "#{cacheFlag}");
                VALUES("create_time", "now()");
            }}.toString();
        }
    }

}
