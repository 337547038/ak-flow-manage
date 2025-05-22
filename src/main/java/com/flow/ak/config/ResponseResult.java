package com.flow.ak.config;

import lombok.Data;

/**
 * 作用：自定义code和msg返回格式
 * 引用：
 * public ResponseResult String test(){
 * return ResponseResult.success("string","message",code)
 * }
 *
 * @param <T>
 */
@Data
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;
    private long timestamp;

    public ResponseResult() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseResult<T> success(T data) {
        return success(data, "操作成功", 200);
    }

    public static <T> ResponseResult<T> success(T data, String message) {
        return success(data, message, 200);
    }

    public static <T> ResponseResult<T> success(T data, String message, Integer code) {
        ResponseResult<T> resultData = new ResponseResult<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setData(data);
        return resultData;
    }


}
