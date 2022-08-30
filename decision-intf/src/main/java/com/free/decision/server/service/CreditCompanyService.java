package com.free.decision.server.service;

import com.free.decision.server.model.CreditCompany;

import java.util.List;

/**
 * 回租系统商户
 */
public interface CreditCompanyService {

    public List<CreditCompany> loadAll();
}
