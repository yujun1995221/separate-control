package com.free.decision.server.service;

import com.free.decision.server.model.Company;
import com.free.decision.server.model.Result;
import com.free.decision.server.model.vo.BankCardAuthVO;

public interface BankCardAuthService {

    /**
     * 新版银行卡鉴权
     * @author pj
     * @return
     */
    public Result bankCardAuth(BankCardAuthVO bankCardAuthVO, Company company);

}
