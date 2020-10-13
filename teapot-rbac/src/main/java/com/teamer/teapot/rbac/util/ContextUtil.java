package com.teamer.teapot.rbac.util;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.rbac.exception.ContextUserNotFoundException;
import com.teamer.teapot.rbac.model.TeapotUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Slf4j
public class ContextUtil {

    public static final String USER_PREFIX = "user";

    public static TeapotUser getUserFromContext(HttpServletRequest request) {
        Object user = request.getSession().getAttribute(USER_PREFIX);
        if (user instanceof TeapotUser) {
            return (TeapotUser) user;
        } else {
            log.info("user:{}", JSON.toJSONString(user));
            throw new ContextUserNotFoundException();
        }
    }
}