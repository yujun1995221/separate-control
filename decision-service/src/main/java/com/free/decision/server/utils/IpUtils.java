package com.free.decision.server.utils;


import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP
 * @author Xingyf
 */
public class IpUtils {
    public static String getRealIp(HttpServletRequest request) {
        String ipForwarded = (String)request.getHeader("x-forwarded-for");
        if (ipForwarded == null) {
            ipForwarded = request.getRemoteAddr();
        } else {
            String ips[] = ipForwarded.split(",");
            ipForwarded = ips[ips.length - 1].trim();
        }
        if(StringUtils.equals(ipForwarded, "0:0:0:0:0:0:0:1"))
            return "127.0.0.1";
        return ipForwarded;
    }

    public static String getRealIpV2(HttpServletRequest request) {
        String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP)
            return request.getRemoteAddr();
        return accessIP;
    }
}
