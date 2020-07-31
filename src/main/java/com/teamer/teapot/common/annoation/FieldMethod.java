package com.teamer.teapot.common.annoation;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * 函数式接口，只接收无参get/is方法
 * 继承Serializable以获取lambda实现类的数据
 *
 * @param <T>
 * @author luoyk
 */
@FunctionalInterface
public interface FieldMethod<T> extends Serializable {

    T getParam();

    default SerializedLambda getSerializedLambda() throws Exception {
        Method write = this.getClass().getDeclaredMethod("writeReplace");
        write.setAccessible(true);
        return (SerializedLambda) write.invoke(this);
    }

    default String getImplClass() {
        try {
            return getSerializedLambda().getImplClass();
        } catch (Exception e) {
            return null;
        }
    }

    default String getImplMethodName() {
        try {
            return getSerializedLambda().getImplMethodName();
        } catch (Exception e) {
            return null;
        }
    }
}
