package com.free.decision.server.controller;

import cn.hutool.crypto.SecureUtil;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 缓存
 * @author Xingyf
 */
@RestController
@RequestMapping("cache")
public class CacheController {

    @Resource
    private CacheChannel cacheChannel;

    /**
     * 清缓存
     * @param region
     * @param key
     * @param secret
     * @return
     */
    @RequestMapping("delKey")
    public String delKey(@RequestParam String region, @RequestParam String key, @RequestParam String secret){
        if(StringUtils.isBlank(region) || StringUtils.isBlank(key) || StringUtils.isBlank(secret)){
            return "fail";
        }
        String[] arr = StringUtils.split(secret,"-");
        if(arr == null || arr.length != 2){
            return "fail";
        }
        String date = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
        if(!StringUtils.equalsIgnoreCase(date, arr[0])){
            return "fail";
        }
        if(!StringUtils.equalsIgnoreCase(SecureUtil.md5("decision"+date), arr[1])){
            return "fail";
        }
        cacheChannel.evict(region, key);
        return "ok";
    }

}
