package com.teamer.teapot.rbac.exception;

import com.teamer.teapot.common.exception.UserNotFoundException;

/**
 * 上下文无法获取用户异常
 *
 * @author ：tanzj
 * @date ：Created in 2020/1/2 10:53
 */
public class ContextUserNotFoundException extends UserNotFoundException {

    public ContextUserNotFoundException() {
        super("用户未登录");
    }

}
