package com.teamer.teapot.common.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2020/11/22
 */
@Data
@Accessors(chain = true)
public class ProjectEnvironment {

    private String environmentId;
    private String projectId;
    private String environmentName;
    private String environmentHost;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
