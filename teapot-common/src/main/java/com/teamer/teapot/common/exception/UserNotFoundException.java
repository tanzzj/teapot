package com.teamer.teapot.common.exception;

/**
 * @author tanzj
 * @date 2021/1/12
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
