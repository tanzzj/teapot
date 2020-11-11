package com.teamer.teapot.common.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2020/11/9
 */
@Data
@Accessors(chain = true)
public class Tag {

    private Integer id;
    private String tagId;
    private String tagKey;
    private String tagValue;
    private String channel;
    private String businessId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
