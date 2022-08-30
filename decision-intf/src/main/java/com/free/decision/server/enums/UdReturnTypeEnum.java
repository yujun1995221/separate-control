package com.free.decision.server.enums;

/**
 * 有盾接口返回值
 * @author yxs
 */
public enum UdReturnTypeEnum {

    SUCCESS(200,"000000", "成功"),
    ERROR(201,"100000 ", "失败"),
    REQUESTED_MESSAGE_FORMAT_ERROR(301,"100005", "请求报文格式错误"),
    NO_USER_INFO(302,"100048", "商户无此用户信息"),
    THE_PICTURE_IS_NOT_GENERATED(303,"100052", "画像暂未生成，请稍后重试");

    private Integer code;
    private String resultCode;
    private String resultDesc;

    UdReturnTypeEnum(Integer code, String resultCode, String resultDesc) {
        this.code = code;
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    public static UdReturnTypeEnum getEnum(String resultCode) {
        for (UdReturnTypeEnum statusEnum : UdReturnTypeEnum.values()) {
            if (statusEnum.getResultCode().equals(resultCode)) {
                return statusEnum;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
