package com.teamer.teapot.common.model.dto;

import com.teamer.teapot.common.model.ProjectOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/8/11.
 */
@Data
@Accessors(chain = true)
public class MergeOrderParams {

    private String projectId;
    private List<ProjectOrder> projectOrderList;

}
