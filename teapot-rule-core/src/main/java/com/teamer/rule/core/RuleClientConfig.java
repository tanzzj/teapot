package com.teamer.rule.core;

import com.teamer.rule.core.annotation.RuleResourceScanner;
import com.teamer.teapot.common.util.YmlLoader;

/**
 * @author tanzj
 * @date 2021/6/23
 */
public class RuleClientConfig {

    public static void setup() {
        String basePackageString = YmlLoader.loadYaml("teapot.rule.base-package", "application.yml").toString();
        String[] basePackageList = basePackageString.split(",");
        RuleResourceScanner ruleResourceScanner = RuleResourceScanner.getInstance();
        ruleResourceScanner.scan(basePackageList);
    }

}
