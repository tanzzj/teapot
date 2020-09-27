package com.teamer.teapot.common.plugin.interceptor;

import com.teamer.teapot.common.annoation.SensitiveData;
import com.teamer.teapot.common.plugin.util.AESSensitive;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.*;


/**
 * @author tanzj
 */
@Slf4j
@Component
@Intercepts(value = {
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
})
public class EncryptInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //拦截 ParameterHandler 的 setParameters 方法 动态设置参数
        if (invocation.getTarget() instanceof ParameterHandler) {
            ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
            // 反射获取参数对像 即 mapper 中 paramsType 的实例
            Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
            parameterField.setAccessible(true);
            //取出实例
            Object parameterObject = parameterField.get(parameterHandler);
            if (Objects.nonNull(parameterObject)) {
                Class<?> parameterObjectClass = parameterObject.getClass();
                SensitiveData sensitiveDataClass = AnnotationUtils.findAnnotation(parameterObjectClass, SensitiveData.class);
                if (Objects.nonNull(sensitiveDataClass)) {
                    //先取出自身的字段
                    List<Field> fieldList = new ArrayList<>(Arrays.asList(parameterObjectClass.getDeclaredFields()));
                    //存在父类则递归去取
                    while (parameterObjectClass.getSuperclass() != null) {
                        parameterObjectClass = parameterObjectClass.getSuperclass();
                        fieldList.addAll(Arrays.asList(parameterObjectClass.getDeclaredFields()));
                    }

                    Field[] declaredFieldList = new Field[fieldList.size()];
                    AESSensitive.getInstance().encrypt(fieldList.toArray(declaredFieldList), parameterObject);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
