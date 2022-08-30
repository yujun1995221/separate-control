package com.free.decision.server;

import net.oschina.j2cache.CacheChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author Xingyf
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Resource
    private CacheChannel cacheChannel;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

    @PreDestroy
    public void destroy() {

    }


}
