package com.teamer.teapot.rbac.exception;

/** 上下文无法获取用户异常
 * @author ：luje
 * @date ：Created in 2020/1/2 10:53
 */
public class ContextUserNotFoundException extends RuntimeException {

    public ContextUserNotFoundException(){
        super("用户未登录");
    }

}
