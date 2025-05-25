package com.flow.ak.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    /**
     * 公共分页处理方法
     */
    public static Map<String, Object> pagination(Map<String, Object> pages) {
        //其他参数包含了pageNum当前第几页,pageSize每页分几条,sort排序,columns指定字段
        Object params = pages.get("extend");
        Object query = pages.get("query");//查询条件
        if (params == null) {
            params = new Object();
        }
        if (query == null) {
            query = new Object();
        }
        //根据pageNum计算pageIndex,并对pageSize设置初始值
        JSONObject obj = JSON.parseObject(JSON.toJSONString(params));
        int pageNum = obj.getIntValue("pageNum", 1);
        int pageSize = obj.getIntValue("pageSize", -1);
        int pageIndex = (pageNum - 1) * pageSize;
        obj.put("pageIndex", pageIndex);
        obj.put("pageSize", pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("extend", obj);
        map.put("query", query);
        return map;
    }

    /**
     * 返回当前登录用户id
     * @return 返回当前登录用户id
     */
    public static Integer getCurrentUserId(){
        return 4;
    }
}
