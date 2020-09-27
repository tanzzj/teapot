package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Data
@Accessors(chain = true)
public class ProjectDatabase {
    private Integer id;
    private String projectId;
    private String databaseId;
    private String databaseName;
}
