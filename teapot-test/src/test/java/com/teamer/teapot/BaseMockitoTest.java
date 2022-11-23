package com.teamer.teapot;

import com.teamer.teapot.util.H2DbSupportFactory;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author tanzj
 * @date 2022/9/19
 */
@RunWith(PowerMockRunner.class)
public abstract class BaseMockitoTest {

    protected static H2DbSupportFactory h2DbSupportFactory;

    /**
     * 用于为target注入对象，目前用于Mapper层的手工注入
     *
     * @param target     目标业务对象
     * @param field      字段
     * @param dependency 依赖的对象
     * @throws NoSuchFieldException   e
     * @throws IllegalAccessException e
     */
    public void setter(Object target, String field, Object dependency) throws NoSuchFieldException, IllegalAccessException {
        Field targetField = target.getClass().getField(field);
        if (!targetField.isAccessible()) {
            targetField.setAccessible(true);
        }
        targetField.set(target, dependency);
    }

    /**
     * 子类集成时，需要在方法增加@Before注解，用于提前打桩
     *
     * @throws Exception e
     * @see org.junit.Before
     */
    public abstract void before() throws Exception;

    /**
     * 子类集成时，需要在方法增加@BeforeClass注解，用于提前静态类初始化能力的集成
     *
     * @see org.junit.BeforeClass
     */
    public static void init() throws IOException, Exception {

    }

    ;


}
