package com.teamer.teapot.common.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/8/10.
 */
@Data
@Accessors(chain = true)
public class DatabaseQueryVO extends AbstractDatabaseExecuteVO {

    Object metaData;
    List<List> dataList;

}
