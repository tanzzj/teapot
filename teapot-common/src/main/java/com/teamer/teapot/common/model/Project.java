package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

/**
 * @author : tanzj
 * @date : 2020/7/30.
 */
@Data
@Accessors(chain = true)
public class Project {

    private Integer id;
    private String projectId;
    private String projectName;
    private String createUser;
    private Date createTime;
}
