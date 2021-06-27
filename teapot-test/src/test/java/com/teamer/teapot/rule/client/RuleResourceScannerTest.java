package com.teamer.teapot.rule.client;

import com.alibaba.fastjson.JSON;
import com.teamer.rule.client.RuleClientConfig;
import com.teamer.rule.client.annotation.RuleResourceScanner;
import org.junit.Test;

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
}
