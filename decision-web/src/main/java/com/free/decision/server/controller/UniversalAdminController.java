package com.free.decision.server.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.free.decision.server.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 万能管理员登录
 */
@RestController
@RequestMapping("universalAdmin")
public class UniversalAdminController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UniversalAdminController.class);

    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public Result universalAdminLogin(HttpServletRequest request){
        try {
            String data = IoUtil.read(request.getInputStream(), CharsetUtil.UTF_8);
            JSONObject jsonObject = JSON.parseObject(data);
            String loginName = jsonObject.getString("loginName");
            String password = jsonObject.getString("password");
            if("superman".equalsIgnoreCase(loginName) && StrUtil.equals(SecureUtil.md5("S@8pE#mA6n"), password)){
                logger.info("管理员密码校验成功");
                return new Result(true);
            }
        } catch (IOException e) {
            logger.error("登录失败", e);
        }
        return new Result(false);
    }

}
