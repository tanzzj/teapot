package com.teamer.teapot.common.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Optional;
import java.util.Properties;

/**
 * @author tanzj
 * @date 2021/6/23
 */
public final class YmlLoader {
    private YmlLoader() {
    }

    public static Object loadYaml(String key, String ymlPath) {
        Resource resource = new ClassPathResource(ymlPath);
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource);
        Properties properties = yamlPropertiesFactoryBean.getObject();
        return Optional.ofNullable(properties.get(key)).orElseThrow(RuntimeException::new);
    }
}
