package com.teamer.teapot.common.plugin.interceptor;

import com.teamer.teapot.common.annoation.SensitiveData;
import com.teamer.teapot.common.plugin.util.AESSensitive;
import com.teamer.teapot.common.plugin.util.SensitiveUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;


/**
 * 解密拦截器
 *
 * @author tanzj
 */
@Component
public class DecryptInterceptor implements Interceptor {

    @Autowired
    SensitiveUtil sensitiveUtil;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        if (resultObject instanceof ArrayList) {
            ArrayList resultList = (ArrayList) resultObject;
            if (!CollectionUtils.isEmpty(resultList) && needToDecrypt(resultList.get(0))) {
                for (Object result : resultList) {
                    //逐一解密
                    AESSensitive.getInstance().decrypt(result);
                }
            }
        } else {
            if (needToDecrypt(resultObject)) {
                sensitiveUtil.decrypt(resultObject);
            }
        }
        return resultObject;
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private boolean needToDecrypt(Object object) {
        Class<?> objectClass = object.getClass();
        SensitiveData sensitiveDataClass = AnnotationUtils.findAnnotation(objectClass, SensitiveData.class);
        return Objects.nonNull(sensitiveDataClass);
    }
}
