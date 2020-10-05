package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import static com.teamer.teapot.common.constant.TeapotConstant.*;

/**
 * @author : tanzj
 * @date : 2020/7/30.
 */
@Data
@Accessors(chain = true)
public class Result {

    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;
    /**
     * 返回代码
     */
    private String code;
    /**
     * 操作结果
     */
    private String result;

    private Result() {
    }

    public static Result success(String message, Object data) {
        return new Result()
                .setCode(SUCCESS_CODE)
                .setMessage(message)
                .setResult(SUCCESS)
                .setData(data);
    }

    public static Result success(Object data) {
        return new Result()
                .setCode(SUCCESS_CODE)
                .setMessage("operation success")
                .setResult(SUCCESS)
                .setData(data);
    }

    public static Result successWithoutData(String message) {
        return new Result()
                .setCode(SUCCESS_CODE)
                .setResult(SUCCESS)
                .setMessage(message);
    }

    public static Result fail(String message) {
        return new Result()
                .setCode(FAIL_CODE)
                .setResult(FAIL)
                .setMessage(message);
    }

    public static Result fail(String message, Object data) {
        return new Result()
                .setCode(FAIL_CODE)
                .setResult(FAIL)
                .setData(data)
                .setMessage(message);
    }

    public static Result fail(String message, String code) {
        return new Result()
                .setCode(FAIL_CODE)
                .setResult(FAIL)
                .setMessage(message);
    }
}
