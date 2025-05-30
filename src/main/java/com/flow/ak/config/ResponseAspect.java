package com.flow.ak.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class ResponseAspect {
    @Around("execution(* com.flow.ak.controller.*.*(..))")

    public Object interceptResponse(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = "";
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            url = request.getRequestURI();
        }

        JSONArray array = JSONArray.of(joinPoint.getArgs());
        JSONObject obj = array.getJSONObject(0);
        String id = obj.getString("id");

        Object body = joinPoint.proceed();
        JSONObject content = JSON.parseObject(JSON.toJSONString(body));
        Object bodyStr = content.get("body");
        CreatJsonFile(bodyStr, id, url);
        return body;
    }

    private static void CreatJsonFile(Object content, String id, String path) throws IOException {
        if (id == null) {
            id = "";
        }
        JSONObject body = new JSONObject();
        body.put("code", 200);
        body.put("data", content);
        String filePath = System.getProperty("user.dir") + path.replace("/api", "/mock") + id + ".json";
        File file = new File(filePath);
        // 检查路径的每个部分，并在需要时创建它
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 如果文件不存在，则创建它
        if (!file.exists()) {
            file.createNewFile();
        }
        // 使用FileWriter将内容写入文件
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(String.valueOf(body));
            System.out.println("内容已成功写入文件: " + filePath);
        }
    }
}
