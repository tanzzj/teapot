package com.teamer.teapot.project.order.controller;

import com.teamer.teapot.common.model.OrderLogs;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.ProjectOrder;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.dto.MergeOrderParams;
import com.teamer.teapot.common.util.ValidationUtil;
import com.teamer.teapot.project.order.service.ProjectOrderLogsService;
import com.teamer.teapot.project.order.service.ProjectOrderService;
import com.teamer.teapot.rbac.model.TeapotUser;
import com.teamer.teapot.rbac.util.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目工单
 *
 * @author : tanzj
 * @date : 2020/8/7.
 */
@RestController
@RequestMapping("/api/project/order")
public class ProjectOrderController {

    @Autowired
    ProjectOrderService projectOrderService;
    @Autowired
    ProjectOrderLogsService projectOrderLogsService;

    @PostMapping("/queryProjectOrderList")
    public Result queryProjectOrderList(@RequestBody PageParam<ProjectOrder> projectPageParam) {
        ValidationUtil.validateParamsBlankAndNull(projectPageParam::getParams);
        ValidationUtil.validateParamsBlankAndNull(projectPageParam.getParams()::getProjectId);
        return projectOrderService.queryProjectOrderList(projectPageParam);
    }

    @PostMapping("/queryProjectOrderDetails")
    public Result queryProjectOrderDetails(@RequestBody ProjectOrder projectOrder) {
        ValidationUtil.validateParamsBlankAndNull(projectOrder::getProjectOrderId);
        return projectOrderService.queryProjectOrderDetails(projectOrder);
    }

    @PostMapping("/createProjectOrder")
    public Result createProjectOrder(@RequestBody ProjectOrder projectOrder, HttpServletRequest httpServletRequest) {
        TeapotUser teapotUser = ContextUtil.getUserFromContext(httpServletRequest);
        ValidationUtil.validateParamsBlankAndNull(
                projectOrder::getProjectOrderName,
                projectOrder::getProjectId,
                projectOrder::getOrderType
        );
        projectOrder.setCreateUser(teapotUser.getUsername()).setCreateUserId(teapotUser.getUserId());
        return projectOrderService.createProjectOrder(projectOrder);
    }

    @PostMapping("/queryProjectOrderAssignedToMe")
    public Result queryProjectOrderAssignedToMe(@RequestBody ProjectOrder projectOrder, HttpServletRequest httpServletRequest) {
        TeapotUser teapotUser = ContextUtil.getUserFromContext(httpServletRequest);
        ValidationUtil.validateParamsBlankAndNull(projectOrder::getProjectId);
        return null;
    }

    @PostMapping("/examineOrder")
    public Result examineOrder(@RequestBody ProjectOrder projectOrder, HttpServletRequest httpServletRequest) {
        ValidationUtil.validateParamsBlankAndNull(projectOrder::getProjectOrderId);
        TeapotUser teapotUser = ContextUtil.getUserFromContext(httpServletRequest);
        projectOrder.setUpdateUser(teapotUser.getUsername());
        return projectOrderService.examineProjectOrder(projectOrder);
    }

    @PostMapping("/mergeOrder")
    public Result mergeOrder(@RequestBody MergeOrderParams mergeOrderParams) {
        ValidationUtil.validateParamsBlankAndNull(mergeOrderParams::getProjectId);
        if (CollectionUtils.isEmpty(mergeOrderParams.getProjectOrderList())) {
            return Result.fail("orderList cannot be null");
        }
        return projectOrderService.mergeOrder(mergeOrderParams);
    }

    @PostMapping("/queryProjectOrderLogs")
    public Result queryProjectOrderLogs(@RequestBody PageParam<OrderLogs> pageParam) {
        ValidationUtil.validateParamsBlankAndNull(pageParam::getParams);
        ValidationUtil.validateParamsBlankAndNull(pageParam.getParams()::getProjectOrderId);
        return projectOrderLogsService.queryProjectOrderLogs(pageParam);
    }

    @PostMapping("/insertProjectOrderLogs")
    public Result insertProjectOrderLogs(@RequestBody OrderLogs orderLogs, HttpServletRequest request) {
        ValidationUtil.validateParamsBlankAndNull(orderLogs::getProjectOrderId, orderLogs::getDetails);
        orderLogs.setCreator(ContextUtil.getUserFromContext(request).getUsername());
        orderLogs.setUpdater(ContextUtil.getUserFromContext(request).getUsername());
        return projectOrderLogsService.insertProjectOrderLogs(orderLogs);
    }
}
