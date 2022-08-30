package com.free.decision.server.model;

/**
 * 社交行为
 * @author xuechenye
 * @create 2019-04-02 16:03
 **/
public class UdSocial {
    /**
     * 社交行为评分
     */
    private String social_score;
    /**
     * 最近150天内社交地域活跃度
     */
    private String ud_e_0001;
    /**
     * 最近150天内夜间社交活跃度
     */
    private String ud_e_0002;
    /**
     * 最近150天内社交亲密度
     */
    private String ud_e_0003;
    /**
     * 30天内与贷款公司的关系活跃度
     */
    private String ud_e_0004;
    /**
     * 最近150天内工作日通话天数
     */
    private String ud_e_0005;

    public void setSocial_score(String social_score) {
        this.social_score = social_score;
    }
    public String getSocial_score() {
        return social_score;
    }

    public void setUd_e_0001(String ud_e_0001) {
        this.ud_e_0001 = ud_e_0001;
    }
    public String getUd_e_0001() {
        return ud_e_0001;
    }

    public void setUd_e_0002(String ud_e_0002) {
        this.ud_e_0002 = ud_e_0002;
    }
    public String getUd_e_0002() {
        return ud_e_0002;
    }

    public void setUd_e_0003(String ud_e_0003) {
        this.ud_e_0003 = ud_e_0003;
    }
    public String getUd_e_0003() {
        return ud_e_0003;
    }

    public void setUd_e_0004(String ud_e_0004) {
        this.ud_e_0004 = ud_e_0004;
    }
    public String getUd_e_0004() {
        return ud_e_0004;
    }

    public void setUd_e_0005(String ud_e_0005) {
        this.ud_e_0005 = ud_e_0005;
    }
    public String getUd_e_0005() {
        return ud_e_0005;
    }

}
