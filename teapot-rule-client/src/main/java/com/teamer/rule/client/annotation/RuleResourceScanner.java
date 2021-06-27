package com.teamer.rule.client.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanzj
 * @date 2021/6/16
 */
@Slf4j
public class RuleResourceScanner {

    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private static final Map<String, List<RuleResource>> RULE_RESOURCE_MAP = new HashMap<>();

    /**
     * The cache CLASS_FIELDS_CACHE
     */
    private static final Map<Class<?>, Field[]> CLASS_FIELDS_CACHE = new ConcurrentHashMap<>();

    private static RuleResourceScanner instance;

    private RuleResourceScanner() {
    }

    public static RuleResourceScanner getInstance() {
        if (instance == null) {
            synchronized (RuleResourceScanner.class) {
                if (instance == null) {
                    instance = new RuleResourceScanner();
                }
            }
        }
        return instance;
    }

    /**
     * scan path and put resources into resourceMap
     *
     * @param basePackage classPath
     */
    public void scan(String... basePackage) {
        Arrays.stream(basePackage).forEach(eachPath -> {
            Set<Class<?>> candidates = scanCandidateStrategyResource(eachPath);
            candidates.forEach(eachCandidate -> {
                Field[] allFields = getAllFields(eachCandidate);
                for (Field declaredField : allFields) {
                    RuleField ruleField = declaredField.getAnnotation(RuleField.class);
                    RULE_RESOURCE_MAP
                            .computeIfAbsent(eachCandidate.getName(), key -> new ArrayList<>())
                            .add(new RuleResource()
                                    .setClassName(eachCandidate.getName())
                                    .setFieldName(declaredField.getName())
                                    .setFieldType(declaredField.getType().getName())
                                    .setGroup(ruleField.group())
                                    .setNameSpace(ruleField.nameSpace())
                                    .setFieldDescription(StringUtils.isEmpty(ruleField.description()) ?
                                            declaredField.getName() : ruleField.description()
                                    ));
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

    public Map<String, List<RuleResource>> getResourceMap() {
        return RULE_RESOURCE_MAP;
    }

    public static Field[] getAllFields(final Class<?> targetClazz) {
        if (targetClazz == Object.class || targetClazz.isInterface()) {
            return new Field[0];
        }

        // get from the cache
        Field[] fields = CLASS_FIELDS_CACHE.get(targetClazz);
        if (fields != null) {
            return fields;
        }

        // load current class declared fields
        fields = targetClazz.getDeclaredFields();
        LinkedList<Field> fieldList = new LinkedList<>(Arrays.asList(fields));

        // remove the static or synthetic fields
        fieldList.removeIf(f -> Modifier.isStatic(f.getModifiers()) || f.isSynthetic());

        // load super class all fields, and add to the field list
        Field[] superFields = getAllFields(targetClazz.getSuperclass());
        if (!CollectionUtils.isEmpty(Arrays.asList(superFields))) {
            fieldList.addAll(Arrays.asList(superFields));
        }

        // list to array
        Field[] resultFields;
        if (!fieldList.isEmpty()) {
            resultFields = fieldList.toArray(new Field[0]);
        } else {
            // reuse the EMPTY_FIELD_ARRAY
            resultFields = new Field[0];
        }

        // set cache
        CLASS_FIELDS_CACHE.put(targetClazz, resultFields);

        return resultFields;
    }
}
