package com.free.decision.server.model;

import java.io.Serializable;
import java.util.Date;

public class NoticeRecord implements Serializable {

    private Long id;

    /**
     * 报告类型  1:运营商 2：多投 3：反欺诈 4：决策
     */
    private Integer reportType;

    /**
     * 回调地址
     */
    private String url;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 通知次数
     */
    private Integer times = 1;

    /**
     * 1：返回成功 0：继续通知
     */
    private Integer flag;

    private Date createTime;

    private Date updateTime;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getReportType() { return reportType; }

    public void setReportType(Integer reportType) { this.reportType = reportType; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public Integer getTimes() { return times; }

    public void setTimes(Integer times) { this.times = times; }

    public Integer getFlag() { return flag; }

    public void setFlag(Integer flag) { this.flag = flag; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime() { return updateTime; }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    @Override
    public String toString() {
        return "NoticeRecord{" +
                "id=" + id +
                ", reportType=" + reportType +
                ", url='" + url + '\'' +
                ", result='" + result + '\'' +
                ", times=" + times +
                ", flag=" + flag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
