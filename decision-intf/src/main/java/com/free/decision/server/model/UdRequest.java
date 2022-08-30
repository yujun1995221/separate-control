package com.free.decision.server.model;

/**
 * 申请行为
 *
 * @author xuechenye
 * @create 2019-04-02 15:59
 **/
public class UdRequest {
    /**
     * 非银行信贷申请行为评分
     */
    private String request_score;
    /**
     * 历史时间内非银行信贷单平台申请认证成功次数平均值
     */
    private String ud_c_0001;
    /**
     * 最近30天内非银行信贷单平台申请认证成功总天数平均值
     */
    private String ud_c_0002;
    /**
     * 最近180天内非银行信贷单平台申请认证成功总天数平均值
     */
    private String ud_c_0003;
    /**
     * 最近360天内非银行信贷单平台申请认证成功总天数平均值
     */
    private String ud_c_0004;
    /**
     * 最近180天内非银行信贷相邻两个申请认证成功日期的最大间隔天数
     */
    private String ud_c_0005;
    /**
     * 在历史时间内非银行信贷首次申请认证成功距今天数
     */
    private String ud_c_0006;
    /**
     * 在历史时间内非银行信贷最近一次申请认证成功距今天数
     */
    private String ud_c_0007;
    /**
     * 最近180天内非银行信贷申请认证未完成的次数占申请认证次数的比例
     */
    private String ud_c_0008;
    /**
     * 最近30天内非银行信贷夜间申请认证成功次数占申请认证成功次数的比例
     */
    private String ud_c_0009;
    /**
     * 最近30天内非银行信贷申请认证天数
     */
    private String ud_c_0010;

    public void setRequest_score(String request_score) {
        this.request_score = request_score;
    }

    public String getRequest_score() {
        return request_score;
    }

    public void setUd_c_0001(String ud_c_0001) {
        this.ud_c_0001 = ud_c_0001;
    }

    public String getUd_c_0001() {
        return ud_c_0001;
    }

    public void setUd_c_0002(String ud_c_0002) {
        this.ud_c_0002 = ud_c_0002;
    }

    public String getUd_c_0002() {
        return ud_c_0002;
    }

    public void setUd_c_0003(String ud_c_0003) {
        this.ud_c_0003 = ud_c_0003;
    }

    public String getUd_c_0003() {
        return ud_c_0003;
    }

    public void setUd_c_0004(String ud_c_0004) {
        this.ud_c_0004 = ud_c_0004;
    }

    public String getUd_c_0004() {
        return ud_c_0004;
    }

    public void setUd_c_0005(String ud_c_0005) {
        this.ud_c_0005 = ud_c_0005;
    }

    public String getUd_c_0005() {
        return ud_c_0005;
    }

    public void setUd_c_0006(String ud_c_0006) {
        this.ud_c_0006 = ud_c_0006;
    }

    public String getUd_c_0006() {
        return ud_c_0006;
    }

    public void setUd_c_0007(String ud_c_0007) {
        this.ud_c_0007 = ud_c_0007;
    }

    public String getUd_c_0007() {
        return ud_c_0007;
    }

    public void setUd_c_0008(String ud_c_0008) {
        this.ud_c_0008 = ud_c_0008;
    }

    public String getUd_c_0008() {
        return ud_c_0008;
    }

    public void setUd_c_0009(String ud_c_0009) {
        this.ud_c_0009 = ud_c_0009;
    }

    public String getUd_c_0009() {
        return ud_c_0009;
    }

    public void setUd_c_0010(String ud_c_0010) {
        this.ud_c_0010 = ud_c_0010;
    }

    public String getUd_c_0010() {
        return ud_c_0010;
    }
}
