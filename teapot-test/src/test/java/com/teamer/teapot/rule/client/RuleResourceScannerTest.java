package com.teamer.teapot.rule.client;

import com.alibaba.fastjson.JSON;
import com.teamer.rule.core.RuleClientConfig;
import com.teamer.rule.core.annotation.RuleResource;
import com.teamer.rule.core.annotation.RuleResourceScanner;
import com.teamer.rule.core.api.RuleResourceUtil;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author tanzj
 * @date 2021/6/18
 */
public class RuleResourceScannerTest {

    @Test
    public void scanTest() {
        RuleClientConfig.setup();
        RuleResourceScanner ruleResourceScanner = RuleResourceScanner.getInstance();
        System.out.println(JSON.toJSONString(ruleResourceScanner.getResourceMap()));
    }

    @Test
    public void getRuleResourcesTest() {
        RuleClientConfig.setup();
        List<RuleResource> existResource = RuleResourceUtil.getRuleResource("com.teamer.teapot.rule.client.DatabaseTemp");
        List<RuleResource> notfound = RuleResourceUtil.getRuleResource("NOTFOUND");
        System.out.println(JSON.toJSONString(existResource));
        assert !CollectionUtils.isEmpty(existResource);
        assert CollectionUtils.isEmpty(notfound);
    }

    @Test
    public void annotation() {
        Method[] methods = RuleResourceUtil.class.getMethods();
        for (Method method : methods) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            System.out.println(JSON.toJSONString(parameterAnnotations));
        }
    }
}
