package com.flow.ak.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeomController {
    @RequestMapping("/hello12")
    public JSONObject dict() {
        String obj = "{'1':'类别1','2':'类别2','3':'类别3','4':'类别4','5':'类别5'}";
        return JSONObject.parseObject(obj);
    }
}
