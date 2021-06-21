package com.teamer.rule.client.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author tanzj
 * @date 2021/6/16
 */
@Slf4j
public class RuleResourceScanner {

    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private static final Map<String, RuleResource> RESOURCE_MAP = new HashMap<>();

    /**
     * scan path and put resources into resourceMap
     *
     * @param basePackage classPath
     */
    public void scan(String... basePackage) {
        Arrays.stream(basePackage).forEach(eachPath -> {
            Set<Class<?>> candidates = scanCandidateStrategyResource(eachPath);
            candidates.forEach(eachCandidate -> {
                Field[] declaredFields = eachCandidate.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    RuleField ruleField = declaredField.getAnnotation(RuleField.class);
                    RESOURCE_MAP.put(
                            eachCandidate.getName(),
                            new RuleResource()
                                    .setClassName(eachCandidate.getName())
                                    .setFieldName(declaredField.getName())
                                    .setFieldType(declaredField.getType().getName())
                                    .setFieldDescription(ruleField.description())
                                    .setGroup(ruleField.group())
                                    .setNameSpace(ruleField.nameSpace())
                    );
                }
            });
        });
    }

    private Set<Class<?>> scanCandidateStrategyResource(String basePackage) {
        Set<Class<?>> candidates = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    Class<?> ruleClass = Class.forName(
                            new CachingMetadataReaderFactory()
                                    .getMetadataReader(resource)
                                    .getClassMetadata()
                                    .getClassName()
                    );
                    if (AnnotationUtils.findAnnotation(ruleClass, RuleData.class) != null) {
                        candidates.add(ruleClass);
                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    protected String resolveBasePackage(String basePackage) {
        return basePackage.replace('.', '/');
    }

    public Map<String, RuleResource> getResourceMap() {
        return RESOURCE_MAP;
    }
}
