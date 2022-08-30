package com.free.decision.server.model;

/**
 * 云相消费行为
 *
 * @author xuechenye
 * @create 2019-04-02 15:47
 **/

public class UdConsume {
    /**
     * 消费行为评分
     */
    private String consume_score;
    /**
     * 最近150天内首次消费距今天数
     */
    private String ud_d_0001;
    /**
     * 最近150天内最近一次消费距今天数
     */
    private String ud_d_0002;
    /**
     * 最近150天内单次消费金额最大值
     */
    private String ud_d_0003;
    /**
     * 最近150天内单次消费金额平均值
     */
    private String ud_d_0004;
    /**
     * 最近150天内虚拟物品消费占消费总额比例
     */
    private String ud_d_0005;
    /**
     * 最近150天内虚拟物品消费总金额
     */
    private String ud_d_0006;
    /**
     * 最近150天内单次手机充值金额平均值
     */
    private String ud_d_0007;
    /**
     * 最近150天内单月手机充值金额最小值
     */
    private String ud_d_0008;

    public void setConsume_score(String consume_score) {
        this.consume_score = consume_score;
    }

    public String getConsume_score() {
        return consume_score;
    }

    public void setUd_d_0001(String ud_d_0001) {
        this.ud_d_0001 = ud_d_0001;
    }

    public String getUd_d_0001() {
        return ud_d_0001;
    }

    public void setUd_d_0002(String ud_d_0002) {
        this.ud_d_0002 = ud_d_0002;
    }

    public String getUd_d_0002() {
        return ud_d_0002;
    }

    public void setUd_d_0003(String ud_d_0003) {
        this.ud_d_0003 = ud_d_0003;
    }

    public String getUd_d_0003() {
        return ud_d_0003;
    }

    public void setUd_d_0004(String ud_d_0004) {
        this.ud_d_0004 = ud_d_0004;
    }

    public String getUd_d_0004() {
        return ud_d_0004;
    }

    public void setUd_d_0005(String ud_d_0005) {
        this.ud_d_0005 = ud_d_0005;
    }

    public String getUd_d_0005() {
        return ud_d_0005;
    }

    public void setUd_d_0006(String ud_d_0006) {
        this.ud_d_0006 = ud_d_0006;
    }

    public String getUd_d_0006() {
        return ud_d_0006;
    }

    public void setUd_d_0007(String ud_d_0007) {
        this.ud_d_0007 = ud_d_0007;
    }

    public String getUd_d_0007() {
        return ud_d_0007;
    }

    public void setUd_d_0008(String ud_d_0008) {
        this.ud_d_0008 = ud_d_0008;
    }

    public String getUd_d_0008() {
        return ud_d_0008;
    }
}
