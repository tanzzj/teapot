package com.teamer.teapot.rbac;

import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.rbac.exception.ContextUserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tanzj
 */
@Slf4j
@RestControllerAdvice
public class RBACExceptionHandler {

    /**
     * 捕捉无法从上下文获取用户异常
     *
     * @param e Exception
     * @return BaseModel
     */
    @ExceptionHandler(ContextUserNotFoundException.class)
    public Result contextUserNotFoundException(ContextUserNotFoundException e) {
        log.debug("无法从上下文获取到用户" + e);
        return Result.fail(e.getMessage(), "401");
    }


}
