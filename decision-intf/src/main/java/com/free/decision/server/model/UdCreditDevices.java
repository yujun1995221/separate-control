package com.free.decision.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *关联设备信息
 */
public class UdCreditDevices implements Serializable {

    private Long id;

    //有盾报告ID
    private Long udCreditId;

    //设备名
    private String deviceName;

    //设备ID
    private String deviceId;

    //设备关联用户数
    private Integer  deviceLinkIdCount;

    //是否安装作弊软件 大于0 表示有
    private Integer cheatsDevice;

    //是否root 1是 0不是
    private Integer isRooted;

    //可疑设备 1是 0不是
    private Integer fraudDevice;

    //是否使用代理 1是 0不是
    private Integer isUsingProxyPort;

    //借贷APP安装数量
    private Integer appInstalmentCount;

    //网络类型 WIFI 等
    private String networkType;

    //有无活体攻击行为 1表示有 0表示无
    private Integer livingAttack;

    //设备最后使用日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date deviceLastUseDate;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUdCreditId() { return udCreditId; }

    public void setUdCreditId(Long udCreditId) { this.udCreditId = udCreditId; }

    public String getDeviceName() { return deviceName; }

    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getDeviceId() { return deviceId; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public Integer getDeviceLinkIdCount() {
        return deviceLinkIdCount;
    }

    public void setDeviceLinkIdCount(Integer deviceLinkIdCount) { this.deviceLinkIdCount = deviceLinkIdCount; }

    public Integer getCheatsDevice() { return cheatsDevice; }

    public void setCheatsDevice(Integer cheatsDevice) { this.cheatsDevice = cheatsDevice; }

    public Integer getIsRooted() { return isRooted; }

    public void setIsRooted(Integer isRooted) { this.isRooted = isRooted; }

    public Integer getFraudDevice() { return fraudDevice; }

    public void setFraudDevice(Integer fraudDevice) { this.fraudDevice = fraudDevice; }

    public Integer getIsUsingProxyPort() { return isUsingProxyPort; }

    public void setIsUsingProxyPort(Integer isUsingProxyPort) { this.isUsingProxyPort = isUsingProxyPort; }

    public Integer getAppInstalmentCount() { return appInstalmentCount; }

    public void setAppInstalmentCount(Integer appInstalmentCount) { this.appInstalmentCount = appInstalmentCount; }

    public String getNetworkType() { return networkType; }

    public void setNetworkType(String networkType) { this.networkType = networkType; }

    public Integer getLivingAttack() { return livingAttack; }

    public void setLivingAttack(Integer livingAttack) { this.livingAttack = livingAttack; }

    public Date getDeviceLastUseDate() { return deviceLastUseDate; }

    public void setDeviceLastUseDate(Date deviceLastUseDate) { this.deviceLastUseDate = deviceLastUseDate; }

    @Override
    public String toString() {
        return "UdCreditDevices{" +
                "id=" + id +
                ", udCreditId=" + udCreditId +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceLinkIdCount=" + deviceLinkIdCount +
                ", cheatsDevice=" + cheatsDevice +
                ", isRooted=" + isRooted +
                ", fraudDevice=" + fraudDevice +
                ", isUsingProxyPort=" + isUsingProxyPort +
                ", appInstalmentCount=" + appInstalmentCount +
                ", networkType='" + networkType + '\'' +
                ", livingAttack=" + livingAttack +
                ", deviceLastUseDate=" + deviceLastUseDate +
                '}';
    }
}
