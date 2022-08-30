package com.free.decision.server.utils;

import com.alibaba.fastjson.JSON;
import com.free.decision.server.model.Result;

public class HttpResultCallBack implements RetryUtils.ResultCheck{

    private boolean matching = false;

    private String json;

    private Result result;

    public HttpResultCallBack(String str){
        Result result = JSON.parseObject(str, Result.class);
        this.matching = result.isSuccess();
        this.json = str;
        this.result = result;
    }

    @Override
    public boolean matching() {
        return matching;
    }

    @Override
    public String getJson() {
        return json;
    }

    @Override
    public Object getObj() {
        return result;
    }
}
