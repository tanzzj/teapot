package com.teamer.rule.core.annotation;

import java.lang.annotation.*;

/**
 * @author tanzj
 * @date 2021/6/16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RuleData {

    String nameSpace() default "DEFAULT";

    String group() default "DEFAULT";

    String description();

}
