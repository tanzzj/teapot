package com.teamer.teapot.project.order.service;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.ProjectOrder;
import com.teamer.teapot.common.model.Result;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
public interface ProjectOrderService {


    /**
     * 查询项目工单列表
     *
     * @param projectOrderPageParam (
     *                              projectId-项目id
     *                              projectOrderName-工单名称[可选] *
     *                              )
     * @return Result
     */
    Result queryProjectOrderList(PageParam<ProjectOrder> projectOrderPageParam);

    /**
     * 查询订单详情
     *
     * @param projectOrder (projectOrderId-项目工单id)
     * @return Result
     */
    Result queryProjectOrderDetails(ProjectOrder projectOrder);

    /**
     * 创建项目工单
     *
     * @param projectOrder - 项目工单
     * @return Result
     */
    Result createProjectOrder(ProjectOrder projectOrder);

    /**
     * 审批工单
     *
     * @param projectOrder (
     *                     projectId,
     *                     orderState - 审核结果 ,
     *                     examineOpinion - 审核意见 TBD
     *                     )
     * @return Result
     */
    Result examineProjectOrder(ProjectOrder projectOrder);
}
