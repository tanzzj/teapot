package com.teamer.teapot.common.util;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.constant.TeapotConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单元测试工具类
 *
 * @author tanzj
 */
public class TestUtil {

    private final static Logger logger = LoggerFactory.getLogger(TestUtil.class);

    /**
     * 单元测试打log并断言成功
     *
     * @param object
     */
    public static void assertSuccess(Object object) {
        logger.info("执行结果：" + JSON.toJSONString(object));
        assert TeapotConstant.SUCCESS.equals(((Result) object).getResult());
    }

    /**
     * 单元测试打log并断言失败
     *
     * @param object
     */
    public static void assertFail(Object object) {
        logger.info("执行结果：" + JSON.toJSONString(object));
        assert TeapotConstant.FAIL.equals(((Result) object).getResult());
    }
}
