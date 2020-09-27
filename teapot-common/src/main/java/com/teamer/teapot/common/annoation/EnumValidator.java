package com.teamer.teapot.common.annoation;

import com.teamer.teapot.common.util.EnumValidatorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author :   tanzj
 * @date :     2019/12/24 0024 15:29:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EnumValidatorHandler.class)
public @interface EnumValidator {
    Class<?> value();

    String message() default "入参值不在正确枚举中";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
