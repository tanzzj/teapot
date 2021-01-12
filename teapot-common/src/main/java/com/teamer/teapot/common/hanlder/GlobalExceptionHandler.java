package com.teamer.teapot.common.hanlder;

import com.teamer.teapot.common.exception.BusinessException;
import com.teamer.teapot.common.exception.UserNotFoundException;
import com.teamer.teapot.common.exception.ValidationException;
import com.teamer.teapot.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉类
 *
 * @author ：tanzj
 * @date ：Created in 2019/12/25 10:30
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 捕捉传参校验不通过的异常处理
     *
     * @param e MethodArgumentNotValidException
     * @return BaseModel
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result paramValidationException(MethodArgumentNotValidException e) {
        log.error("参数校验失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        if (error == null) {
            return Result.fail("参数校验失败");
        }
        String field = error.getField();
        return Result.fail(field + "参数异常");
    }

    /**
     * 捕获空值异常
     *
     * @param e ValidationException
     * @return BaseModel
     */
    @ExceptionHandler(ValidationException.class)
    public Result validationException(ValidationException e) {
        log.error("参数为空，{}", e.getMessage());
        return Result.fail(e.getMessage());
    }


    /**
     * 捕捉传参校验不通过的异常处理
     *
     * @param e HttpMessageNotReadableException
     * @return BaseModel
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数校验失败", e);
        return Result.fail("参数类型错误");
    }


    /**
     * 捕获所有异常
     *
     * @param e Exception
     * @return BaseModel
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error("出现异常", e);
        return Result.fail("服务异常");
    }


    /**
     * 捕捉无法从上下文获取用户异常
     *
     * @param e Exception
     * @return BaseModel
     */
    @ExceptionHandler(UserNotFoundException.class)
    public Result contextUserNotFoundException(UserNotFoundException e) {
        log.debug("无法从上下文获取到用户" + e);
        return Result.fail(e.getMessage(), "401");
    }


    /**
     * 捕捉无法从上下文获取用户异常
     *
     * @param e Exception
     * @return BaseModel
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException e) {
        log.error("business exception" + e);
        return Result.fail(e.getMessage(), "500");
    }

}
