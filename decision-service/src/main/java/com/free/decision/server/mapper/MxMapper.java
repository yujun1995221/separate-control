package com.free.decision.server.mapper;

import cn.hutool.core.date.DateUtil;
import com.free.decision.server.model.MxRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * 魔蝎
 */
@Mapper
public interface MxMapper {

    /**
     * 插入
     * @param mxRecord
     * @return
     */
    @InsertProvider(type = MxSqlBuilder.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public long insert(MxRecord mxRecord);

    /**
     * 根据userId和type查询
     * @param phone
     * @param type
     * @return
     */
    @SelectProvider(type = MxSqlBuilder.class, method = "loadByPhoneAndType")
    public MxRecord loadByPhoneAndType(@Param("phone") String phone, @Param("type") int type);

    class MxSqlBuilder{

        private static final String TABLE_NAME = "decision_mx_record";

        public static String insert(MxRecord mxRecord){
          return new SQL(){{
                INSERT_INTO(TABLE_NAME);
                VALUES("company_id", "#{companyId}");
                VALUES("user_id", "#{userId}");
                VALUES("id_number", "#{idNumber}");
                VALUES("phone", "#{phone}");
                VALUES("order_no", "#{orderNo}");
                VALUES("request_params", "#{requestParams}");
                VALUES("result", "#{result}");
                VALUES("type", "#{type}");
                VALUES("create_time", "'"+DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"'");
          }}.toString();
        }

        public static String loadByPhoneAndType(@Param("phone") String phone, @Param("type") int type){
            return "select result,create_time as createTime from decision_mx_record where phone = #{phone} and type = #{type} order by id desc limit 1";
        }
    }
}
