package com.free.decision.server.utils;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

public class ConvertBeanUtil {
    public static <T> T convertRecord(Record record, Class<T> clazz){
        if(record == null){
            return null;
        }
        return JSON.parseObject(record.toString(), clazz);
    }

    public static <T> List<T> convertRecordList(List<Record> records, Class<T> clazz){
        if(records == null || records.isEmpty()){
            return null;
        }
        List<T> list = new ArrayList<>();
        for(Record r : records){
            list.add(convertRecord(r, clazz));
        }
        return list;
    }

}
