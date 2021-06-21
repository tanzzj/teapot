package com.teamer.rule.client.annotation;

import java.lang.annotation.*;

/**
 * @author tanzj
 * @date 2021/6/16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface RuleField {

    String nameSpace() default "DEFAULT";

    String group() default "DEFAULT";

    String description();


}
