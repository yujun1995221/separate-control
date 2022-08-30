package com.free.decision.server.model.vo;


import com.free.decision.server.model.*;

import java.io.Serializable;
import java.util.List;

/**
 * 反欺诈报告
 */
public class UdCreditVO implements Serializable {

    private UdCredit udCredit;

    //关联设备
    private List<UdCreditDevices> udCreditDevices;

    //用户特征值描述
    private List<UdFeaturesDic> udFeaturesDicList;

    //用户特征
    private List<UdCreditUserFeature> udCreditUserFeatureList;

    //有盾行业借款详情
    private List<UdCreditLoanIndustry> udCreditLoanIndustryList;

    public UdCredit getUdCredit() {return udCredit;}

    public void setUdCredit(UdCredit udCredit) {this.udCredit = udCredit;}

    public List<UdCreditDevices> getUdCreditDevices() {return udCreditDevices;}

    public void setUdCreditDevices(List<UdCreditDevices> udCreditDevices) {this.udCreditDevices = udCreditDevices;}

    public List<UdFeaturesDic> getUdFeaturesDicList() {return udFeaturesDicList;}

    public void setUdFeaturesDicList(List<UdFeaturesDic> udFeaturesDicList) {this.udFeaturesDicList = udFeaturesDicList;}

    public List<UdCreditUserFeature> getUdCreditUserFeatureList() {return udCreditUserFeatureList;}

    public void setUdCreditUserFeatureList(List<UdCreditUserFeature> udCreditUserFeatureList) {this.udCreditUserFeatureList = udCreditUserFeatureList;}

    public List<UdCreditLoanIndustry> getUdCreditLoanIndustryList() {return udCreditLoanIndustryList;}

    public void setUdCreditLoanIndustryList(List<UdCreditLoanIndustry> udCreditLoanIndustryList) {this.udCreditLoanIndustryList = udCreditLoanIndustryList;}

    @Override
    public String toString() {
        return "UdCreditVO{" +
                "udCredit=" + udCredit +
                ", udCreditDevices=" + udCreditDevices +
                ", udFeaturesDicList=" + udFeaturesDicList +
                ", udCreditUserFeatureList=" + udCreditUserFeatureList +
                ", udCreditLoanIndustryList=" + udCreditLoanIndustryList +
                '}';
    }
}
