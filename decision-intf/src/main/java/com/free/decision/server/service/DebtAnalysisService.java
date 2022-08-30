package com.free.decision.server.service;

import com.free.decision.server.model.CreditCompany;
import com.free.decision.server.model.LiabilitiesParam;

public interface DebtAnalysisService {

    public void loadDebtAnalysisData();

    public void getOrderRecord(CreditCompany database, String UUID);

    public void getCustomerRecord(CreditCompany database, String UUID);

    public LiabilitiesParam getLiabilitiesReport(String idNumber);

    public int getNumExpire(String idNumber);
}
