package com.teamer.teapot.project.order.dao;

import com.teamer.teapot.common.model.ProjectOrder;
import com.teamer.teapot.common.model.dto.MergeOrderParams;
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
     *
     * @param projectOrder (
     *                     projectId,
     *                     projectOrderId,
     *                     orderTag
     *                     )
     * @return int
     */
    int insertProjectTags(ProjectOrder projectOrder);

    /**
     * 更新工单详情
     *
     * @param projectOrder (
     *                     projectOrderId,
     *                     projectOrderName,
     *                     projectOrderDetail,
     *                     content,
     *                     orderState
     *                     )
     * @return int
     */
    int updateProjectOrder(ProjectOrder projectOrder);

    /**
     * 批量查询工单信息
     *
     * @param mergeOrderParams (
     *                         projectId,
     *                         projectOrderIdList
     *                         )
     * @return List<ProjectOrder>
     */
    List<ProjectOrder> queryOrderListByOrderIdList(MergeOrderParams mergeOrderParams);

}
