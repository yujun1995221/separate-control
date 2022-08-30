package com.free.decision.server.mapper;

import com.free.decision.server.model.CreditCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 回租系统商户
 */
@Mapper
public interface CreditCompanyMapper {

    public List<CreditCompany> loadAll();

}
