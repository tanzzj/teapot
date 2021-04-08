package com.teamer.teapot.core.envs;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.teamer.teapot.core.env.SystemEnvEnum.*;

/**
 * @author tanzj
 * @date 2021/1/13
 */
public class SystemEnvConstant {

    public static final List<Map<String, String>> ENV_LIST = newArrayList(
            ImmutableMap.of(DEV.getEnvCode(), DEV.getEnvName()),
            ImmutableMap.of(PRE.getEnvCode(), PRE.getEnvName()),
            ImmutableMap.of(PROD.getEnvCode(), PROD.getEnvName())
    );

    private SystemEnvConstant() {
    }
}
