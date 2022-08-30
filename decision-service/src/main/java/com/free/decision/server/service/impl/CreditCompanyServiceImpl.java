package com.free.decision.server.service.impl;

import com.free.decision.server.mapper.CreditCompanyMapper;
import com.free.decision.server.model.CreditCompany;
import com.free.decision.server.service.CreditCompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 回租系统商户
 */
@Service
public class CreditCompanyServiceImpl implements CreditCompanyService {

    @Resource
    private CreditCompanyMapper creditCompanyMapper;

    @Override
    public List<CreditCompany> loadAll() {
        return creditCompanyMapper.loadAll();
    }
}
