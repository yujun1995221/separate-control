package com.free.decision.server.service;


import com.free.decision.server.model.BlackList;

import java.util.List;

/**
 * 黑名单库
 */
public interface BlackListService {

    /**
     * 根据身份证号查询
     * @param idNumber
     * @return
     */
    public long loadCountByIdNumber(String idNumber);

    /**
     * 插入黑名单库
     * @param blackLists
     * @return
     */
    public long batchInsertBlackList(List<BlackList> blackLists);

    /**
     * 回租系统黑名单分析
     */
    public void blackAnAnalysis();
}
