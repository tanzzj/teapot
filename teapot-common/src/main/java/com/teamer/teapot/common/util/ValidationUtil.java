package com.teamer.teapot.common.util;

import com.teamer.teapot.common.annoation.FieldMethod;
import com.teamer.teapot.common.annoation.FieldName;
import com.teamer.teapot.common.exception.ValidationException;
import com.teamer.teapot.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用校验器
 *
 * @author tanzj
 */
@Slf4j
public class ValidationUtil {

    /**
     * 变量get前缀
     */
    public static final String FIELD_PREFIX_GET = "get";

    /**
     * Boolean变量is前缀
     */
    public static final String FIELD_PREFIX_IS = "is";

    /**
     * 邮箱规则
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");
    /**
     * 手机号规则
     */
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^((861)|(1))\\d{10}$");

    /**
     * 密码规则
     */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[\\x01-\\x7f]*$");


    /**
     * 正则校验
     *
     * @param expressionString 规则
     * @param info             需要匹配的信息
     * @return boolean 是否匹配
     */
    public static boolean checkRegExpression(String expressionString, String info) {
        Pattern pattern = Pattern.compile(expressionString);
        Matcher matcher = pattern.matcher(info);
        return matcher.matches();
    }

    /**
     * 字符串通配方法,只能匹配/api/* 类型
     *
     * @param patternString 通配符 如/api/*
     * @param content       需要匹配的内容 如/api/test
     * @return boolean
     */
    public static boolean stringMatcher(String patternString, String content) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);
        return matcher.lookingAt();
    }

    /**
     * 验证手机号码是否符合基本格式
     *
     * @param mobile 待验证的手机号
     * @return boolean
     */
    public static boolean checkMobileNo(String mobile) {
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        boolean resultTrue = matcher.matches();
        if (resultTrue) {
            return resultTrue;
        } else {
            throw new ValidationException("手机号码不符合格式");
        }

    }

    /**
     * 验证邮箱是否符合格式
     *
     * @param email 待校验的邮箱
     * @return boolean
     */
    public static boolean checkEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        boolean resultTrue = matcher.matches();
        if (resultTrue) {
            return resultTrue;
        } else {
            throw new ValidationException("电子邮箱不符合格式");
        }
    }


    /**
     * 验证是否为密码格式,特殊字符也可以作为密码
     *
     * @param password 密码
     * @return boolean
     */
    public static boolean checkPassword(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        boolean resultTrue = matcher.matches();
        if (resultTrue) {
            return resultTrue;
        } else {
            throw new ValidationException("不支持中文密码");
        }
    }

    /**
     * 对象空值校验器
     *
     * @param targetObject 传入的目标对象
     * @param params       需要校验的参数
     * @return Result success/fail
     */
    public static Result validateParamsBlankAndNull(Object targetObject, String... params) {
        Class clazz = targetObject.getClass();
        StringBuilder stringBuilder = null;
        for (String param : params) {
            param = param.trim();
            Method method;
            try {
                //将param首字母大写
                method = clazz.getMethod(FIELD_PREFIX_GET + param.substring(0, 1).toUpperCase() + param.substring(1));
            } catch (NoSuchMethodException e) {
                try {
                    method = clazz.getSuperclass().getMethod(FIELD_PREFIX_GET + param.substring(0, 1).toUpperCase() + param.substring(1));
                } catch (NoSuchMethodException e1) {
                    throw new ValidationException("参数" + param + "异常");
                }
            }
            Object result;
            try {
                //取得getter执行结果
                result = method.invoke(targetObject);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
                throw new ValidationException("方法不可执行");
            } catch (InvocationTargetException e) {
                log.error(e.getMessage());
                throw new ValidationException("传入对象异常");
            }
            if (ObjectUtils.isEmpty(result)) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                FieldName fieldName;
                try {
                    fieldName = clazz.getDeclaredField(param).getAnnotation(FieldName.class);
                } catch (NoSuchFieldException e) {
                    try {
                        fieldName = clazz.getSuperclass().getDeclaredField(param).getAnnotation(FieldName.class);
                    } catch (NoSuchFieldException e1) {
                        throw new ValidationException("参数" + param + "异常");
                    }
                }
                if (fieldName != null) {
                    stringBuilder.append(fieldName.value()).append("参数异常;");
                } else {
                    stringBuilder.append(param).append("参数异常;");
                }
            }
        }
        if (stringBuilder == null) {
            return Result.successWithoutData("校验通过");
        } else {
            throw new ValidationException(stringBuilder.toString());
        }
    }

    /**
     * 对象空值校验器
     *
     * @param fieldMethods 传入方法引用
     * @return Result success/fail
     */
    public static Result validateParamsBlankAndNull(FieldMethod... fieldMethods) {
        StringBuilder stringBuilder = null;
        for (FieldMethod fieldMethod : fieldMethods) {
            if (ObjectUtils.isEmpty(fieldMethod.getParam())) {
                //只有get/is方法才执行
                String methodName = fieldMethod.getImplMethodName();
                if (methodName.startsWith(FIELD_PREFIX_GET)) {
                    methodName = fieldMethod.getImplMethodName().substring(3);
                } else if (methodName.startsWith(FIELD_PREFIX_IS)) {
                    methodName = fieldMethod.getImplMethodName().substring(2);
                } else {
                    log.error(methodName + "方法不可执行");
                    throw new ValidationException("方法不可执行");
                }
                String paramName = Introspector.decapitalize(methodName);
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                String className = fieldMethod.getImplClass().replace("/", ".");
                Class clazz;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage());
                    throw new ValidationException("对象" + className + "不存在");
                }
                FieldName fieldName;
                try {
                    fieldName = clazz.getDeclaredField(paramName).getAnnotation(FieldName.class);
                } catch (NoSuchFieldException e) {
                    log.error(e.getMessage());
                    throw new ValidationException("参数" + paramName + "异常");
                }
                stringBuilder.append(fieldName != null ?
                        fieldName.value() :
                        paramName).append("不能为空;");
            }
        }
        if (stringBuilder == null) {
            return Result.successWithoutData("校验通过");
        } else {
            throw new ValidationException(stringBuilder.toString());
        }
    }
}
