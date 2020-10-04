package com.teamer.teapot.common.annoation;

import java.lang.annotation.*;

/**
 * 注解敏感信息类的注解
 *
 * @author tanzj
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveField {
}
