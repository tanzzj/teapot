package com.teamer.teapot.project.order.controller;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.PortalUser;
import com.teamer.teapot.common.model.ProjectOrder;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.ValidationUtil;
import com.teamer.teapot.project.order.service.ProjectOrderService;
import com.teamer.teapot.rbac.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/project/order/")
public class ProjectOrderController {

    @Autowired
    ProjectOrderService projectOrderService;

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
        PortalUser portalUser = ContextUtil.getUserFromContext(httpServletRequest);
        ValidationUtil.validateParamsBlankAndNull(
                projectOrder::getProjectOrderName,
                projectOrder::getProjectId,
                projectOrder::getContent,
                projectOrder::getOrderType
        );
        projectOrder.setCreateUser(portalUser.getUsername()).setCreateUserId(portalUser.getUserId());
        return projectOrderService.createProjectOrder(projectOrder);
    }
}
