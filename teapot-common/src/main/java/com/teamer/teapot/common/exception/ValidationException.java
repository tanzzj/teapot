package com.teamer.teapot.common.exception;

/**
 * 校验失败异常
 * @author tanzj
 */
public class ValidationException extends RuntimeException{

	public ValidationException(String message) {
		super(message);
	}
}
