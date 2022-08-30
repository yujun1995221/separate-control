package com.free.decision.server.interceptors;

import com.alibaba.fastjson.JSON;
import com.free.decision.server.utils.IpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 全局拦截器 日志打点
 * @author Xingyf
 */
public class GlobalInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Value("${devModel}")
    private boolean devModel;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> params = request.getParameterMap();
        StringBuilder query = new StringBuilder();
        for(Map.Entry<String, String[]> p : params.entrySet()){
            query.append(p.getKey()+"="+ JSON.toJSONString(p.getValue())+"&");
        }
        logger.info("打点日志[{}]: URI:{},QUERY:{},IP:{}", DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss,S"),
                request.getRequestURI(), StringUtils.removeEnd(query.toString(), "&"), IpUtils.getRealIp(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null){
            request.setAttribute("devModel", devModel);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
