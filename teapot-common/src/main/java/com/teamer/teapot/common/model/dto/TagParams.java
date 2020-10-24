package com.teamer.teapot.common.model.dto;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.common.model.enums.TagEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2020/11/9
 */
@Data
@Accessors(chain = true)
public class TagParams {

    private String tagId;
    private String businessId;
    private String tagKey;
    private String tagValue;
    private TagEnum.TagChannelEnum channel;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
