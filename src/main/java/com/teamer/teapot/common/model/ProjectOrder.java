package com.teamer.teapot.common.model;

import com.teamer.teapot.common.annoation.FieldName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Data
@Accessors(chain = true)
public class ProjectOrder {

    private Integer id;
    private String projectOrderId;
    private String projectId;
    private String projectOrderName;
    /**
     * 工单描述
     */
    private String projectOrderDetail;
    /**
     * 工单请求修改的内容
     */
    private String content;
    @FieldName(value = "工单类型", comment = "0-其他工单 1-db工单 2-config工单")
    private Integer orderType;
    @FieldName(value = "工单状态", comment = "0-未审核 1-已通过")
    private Integer orderState;
    private String createUserId;
    private String createUser;
    private Date createTime;
    @FieldName(value = "指定审核人", comment = "传入userId")
    private List<PortalUser> assignedUserList;
    private List<ProjectOrderTag> tag;

}
