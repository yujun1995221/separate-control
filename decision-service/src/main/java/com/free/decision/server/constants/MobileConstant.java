package com.free.decision.server.constants;

/**
 * 运营商数据常量
 */
public class MobileConstant {

    public static final String LOGIN_URL = "https://credit.baiqishi.com/clweb/api/mno/login";
    public static final String VERIFY_AUTH_SMS_CODE = "https://credit.baiqishi.com/clweb/api/mno/verifyauthsms";
    public static final String SEND_AUTH_CODE = "https://credit.baiqishi.com/clweb/api/mno/sendauthsms";
    public static final String SEND_SMS_CODE = "https://credit.baiqishi.com/clweb/api/mno/sendloginsms";
    public static final String GET_ORIGINAL_DATA= "https://credit.baiqishi.com/clweb/api/mno/getoriginal";
    public static final String GET_REPORT_DATA= "https://credit.baiqishi.com/clweb/api/mno/getreport";

    public static final String MX_GET_ORIGINAL_DATA = "https://api.51datakey.com/carrier/v3/mobiles/{mobile}/mxdata-ex";
    public static final String MX_GET_REPORT_DATA = "https://api.51datakey.com/carrier/v3/mobiles/{mobile}/mxreport";
    public static final String MX_POST_UPLOAD_PHONE_LIST = "https://api.51datakey.com/carrier/v1/mobile/{mobile}/contacts";
}
