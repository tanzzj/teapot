package com.teamer.rule.core.api;

import com.teamer.rule.core.annotation.RuleResource;
import com.teamer.rule.core.annotation.RuleResourceScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tanzj
 * @date 2021/6/28
 */
@Slf4j
public final class RuleResourceUtil {

    public static List<RuleResource> getRuleResource(String targetClazzName) {
        Map<String, List<RuleResource>> cachedResourceMap = RuleResourceScanner.getInstance().getResourceMap();
        if (StringUtils.isEmpty(targetClazzName) || cachedResourceMap.size() == 0) {
            log.warn("no resources found, please init cachedResourceMap first and make sure targetClazzName is not empty");
            return new ArrayList<>(0);
        }

        if (cachedResourceMap.get(targetClazzName) == null) {
            log.error("target clazz {} is empty", targetClazzName);
            return new ArrayList<>(0);
        }
        return cachedResourceMap.get(targetClazzName);
    }

    private RuleResourceUtil() {
    }
}
