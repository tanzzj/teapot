package com.teamer.teapot.common.util;

import com.teamer.teapot.common.annoation.EnumValidator;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author :   tanzj
 * @date :     2019/12/24 0024 15:30
 */
@Slf4j
public class EnumValidatorHandler implements ConstraintValidator<EnumValidator, Object>, Annotation {
    private List<Object> values = new ArrayList<>();

    @Override
    public void initialize(EnumValidator enumValidator) {
        Class<?> clz = enumValidator.value();
        Object[] ojects = clz.getEnumConstants();
        try {
            Method method = clz.getMethod("getValue");
            if (Objects.isNull(method)) {
                throw new RuntimeException(String.format("枚举对象{}缺少字段名为value的字段", clz.getName()));
            }
            Object value = null;
            for (Object obj : ojects) {
                value = method.invoke(obj);
                values.add(value);
            }
        } catch (Exception e) {
            log.error("[处理枚举校验异常]", e);
        }
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(value) || values.contains(value);
    }
}
