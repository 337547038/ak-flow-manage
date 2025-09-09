package com.flow.ak.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.flow.ak.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author: 337547038
 * date: 2023-11
 * 作用：
 * 统一拦截打印接口请求入参和响应日志
 * 调用：
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("execution(public * com.flow.ak.controller.*.*(..))")
    public void log() {

    }

    private void printLog(String logType, String format, Object... arguments) {
        if (log.isDebugEnabled()) {
            if (Objects.equals(logType, "info")) {
                log.info(format, arguments);
            } else if (Objects.equals(logType, "error")) {
                log.error(format, arguments);
            } else if (Objects.equals(logType, "debug")) {
                log.error(format, arguments);
            }
        }
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().toLongString();
        Object[] args = point.getArgs();

        //序列化时过滤掉request和response，在controller使用HttpServletRequest时，这里args会带上request和response
        //添加对MultipartFile的过滤
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
        List<Object> logArgs = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse) && !(arg instanceof MultipartFile)))
                .collect(Collectors.toList());
        Object params = new Object();
        if (!logArgs.isEmpty()) {
            params = logArgs.get(0);
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        UUID uuid = UUID.randomUUID(); //用于关联响应结果，否则同时多个请求分不清响应是属于哪个请求
        printLog("info", "请求URL：{},\n 请求头信息：{},\n 请求方法全路径：{},\n 请求方法类型：{}, 请求IP：{}, 请求参数：{},\n UUID：{}",
                request.getRequestURL(),
                JSON.toJSONString(getHeaders(request.getHeaderNames(), request)),
                methodName,
                request.getMethod(), request.getRemoteAddr(), JSON.toJSONString(params), uuid);
        Object result = null;
        try {
            result = point.proceed();
        } catch (Exception e) {
            printLog("error", "异常 : {},请求方法类型：{}", e, methodName);
            // 这里不能直接RuntimeException，有些是使用了CustomException自定义的
            //throw new RuntimeException(e);
            throw e;
        }
        printLog("debug", "{}响应 :{}", uuid, JSON.toJSONString(result));
        CreatJsonFile(result, params, String.valueOf(request.getRequestURL()));
        return result;
    }

    private Map<String, Object> getHeaders(Enumeration<String> headerNames, HttpServletRequest request) {
        Map<String, Object> parameterNameAndValues = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            parameterNameAndValues.put(key, value);
        }
        return parameterNameAndValues;
    }

    /**
     * 为前端本地调试生成json文件
     */
    private static void CreatJsonFile(Object result, Object params, String urlPath) throws IOException {
        Pattern pattern = Pattern.compile("/api(.*)");
        Matcher matcher = pattern.matcher(urlPath);
        if (matcher.find()) {
            String path = matcher.group(1);
            JSONObject obj = JSONObject.parse(JSONObject.toJSONString(params));
            String id = "";
            if (obj.getString("id") != null) {
                id = obj.getString("id");
            }
            JSONObject body = new JSONObject();
            body.put("code", 200);
            try {
                JSONObject bodyContent = JSONObject.parse(JSONObject.toJSONString(result));
                if (bodyContent.get("body") != null) {
                    body.put("data", bodyContent.get("body"));
                } else {
                    body.put("data", bodyContent.get("data"));
                }
            } catch (Exception e) {
                body.put("data", result);
            }
            // 需带userid的
            String includesApi = "flow/my,flow/todo,flowRecord/done,flowRecord/copy";
            String userId = "";
            if (Arrays.asList(includesApi.split(",")).contains(path)) {
                userId = String.valueOf(Utils.getCurrentUserId());
            }

            String filePath = System.getProperty("user.dir") + "/mock" + path +userId+ id + ".json";
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
}