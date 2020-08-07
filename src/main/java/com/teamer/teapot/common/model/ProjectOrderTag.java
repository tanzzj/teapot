package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Data
@Accessors(chain = true)
public class ProjectOrderTag {
    private String tagId;
    private String projectId;
    private String orderTag;
}
