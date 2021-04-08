package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author tanzj
 * @date 2020/11/10
 */
@Data
@Accessors(chain = true)
public class BusinessTag {

    private String businessId;
    private String channel;
    private List<Tag> tagList;

}
