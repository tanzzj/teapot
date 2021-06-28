package com.teamer.teapot.core.env;

/**
 * @author tanzj
 * @date 2021/1/13
 */
public enum SystemEnvEnum {
    /**
     * 开发环境
     */
    DEV("dev", "开发环境"),
    /**
     * 预发环境
     */
    PRE("pre", "预发环境"),
    /**
     * 生产环境
     */
    PROD("prod", "生产环境");
    private String envCode;
    private String envName;

    SystemEnvEnum(String envCode, String envName) {
        this.envCode = envCode;
        this.envName = envName;
    }

    public String getEnvCode() {
        return envCode;
    }

    public String getEnvName() {
        return envName;
    }
}
