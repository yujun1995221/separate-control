package com.free.decision.server.constants;

import java.math.BigDecimal;

/**
 * 配置常量
 */
public class ConfigConstant {

    /**
     * 接口调用的最少价格
     */
    public static final BigDecimal API_LEAST_PRICE = new BigDecimal(10);

    /**
     * 异步通知最多通知次数
     */
    public static final int TIMES_MAX = 8;

    /**
     * 报告有效期（天）
     */
    public static final int VALIDITY_PERIOD = 15;

}
