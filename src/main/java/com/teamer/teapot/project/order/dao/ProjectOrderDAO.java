package com.teamer.teapot.project.order.dao;

import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectOrder;
import com.teamer.teapot.common.model.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Mapper
public interface ProjectOrderDAO {

    /**
     * 查询项目工单列表
     *
     * @param projectOrder (projectId-项目id)
     * @return Result
     */
    List<ProjectOrder> queryProjectOrderList(ProjectOrder projectOrder);

    /**
     * 查询订单详情
     *
     * @param projectOrder (projectOrderId-项目工单id)
     * @return Result
     */
    ProjectOrder queryProjectOrderDetails(ProjectOrder projectOrder);

    /**
     * 创建项目工单
     *
     * @param projectOrder - 项目工单
     * @return Result
     */
    int createProjectOrder(ProjectOrder projectOrder);

    /**
     * 创建工单tag
     * @param projectOrder (
     *                     projectId,
     *                     projectOrderId,
     *                     orderTag
     *                     )
     * @return
     */
    int insertProjectTags(ProjectOrder projectOrder);

}
