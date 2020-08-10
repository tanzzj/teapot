package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : tanzj
 * @date : 2020/8/10.
 */
@Data
@Accessors(chain = true)
public class MetaData {

    private String name;
    private Integer mysqlType;
    private Boolean primaryKey;
    private Boolean blob;

}
