package com.teamer.teapot.common.plugin.util;

import com.teamer.teapot.common.annoation.SensitiveField;
import com.teamer.teapot.common.util.AESUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author tanzj
 */
public class AESSensitive implements SensitiveUtil {

    private static volatile AESSensitive instance;

    private AESSensitive() {
    }

    public static AESSensitive getInstance() {
        if (instance == null) {
            synchronized (AESSensitive.class) {
                if (instance == null) {
                    instance = new AESSensitive();
                }
            }
        }
        return instance;
    }

    /**
     * 加密
     *
     * @param declaredFields paramsObject所声明的字段
     * @param paramsObject   mapper中paramsType的实例
     * @return T
     * @throws IllegalAccessException 字段不可访问异常
     */
    @Override
    public <T> T encrypt(Field[] declaredFields, T paramsObject) throws IllegalAccessException {
        for (Field field : declaredFields) {
            //取出所有被EncryptDecryptField注解的字段
            SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = field.get(paramsObject);
                //暂时只实现String类型的加密
                if (object instanceof String) {
                    String value = (String) object;
                    //加密  这里我使用自定义的AES加密工具
                    field.set(paramsObject, new AESUtil().encrypt(value));
                }
            }
        }
        return paramsObject;
    }

    /**
     * 解密
     *
     * @param result resultType的实例
     * @return T
     * @throws IllegalAccessException 字段不可访问异常
     */
    @Override
    public <T> T decrypt(T result) throws IllegalAccessException {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        List<Field> fieldList = new ArrayList<>(Arrays.asList(resultClass.getDeclaredFields()));
        //如果存在父类的情况
        if (resultClass.getSuperclass() != null) {
            resultClass = resultClass.getSuperclass();
            fieldList.addAll(Arrays.asList(resultClass.getDeclaredFields()));
        }
        for (Field field : fieldList) {
            //取出所有被EncryptDecryptField注解的字段
            SensitiveField annotation = field.getAnnotation(SensitiveField.class);
            if (!Objects.isNull(annotation)) {
                field.setAccessible(true);
                Object object = field.get(result);
                //只支持String的解密
                if (object instanceof String) {
                    String value = (String) object;
                    //对字段进行解密
                    field.set(result, new AESUtil().decrypt(value));
                }
            }
        }
        return result;
    }
}
